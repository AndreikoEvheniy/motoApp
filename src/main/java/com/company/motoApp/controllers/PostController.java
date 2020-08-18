package com.company.motoApp.controllers;

import com.company.motoApp.models.Post;
import com.company.motoApp.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.UUID;

@Controller
public class PostController {

    //private static final String UPLOADED_FOLDER = "D://files//";

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/addPost")
    public String addPost(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
                          @RequestParam String title, @RequestParam String anons,
                          @RequestParam String fullText, Model model) throws IOException {
        Post post = new Post(title, anons, fullText, file.getOriginalFilename());

        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            post.setFilename(resultFileName);
        }

        postRepository.save(post);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + title);

/*        if (file.isEmpty() || title.equals("") || anons.equals("") || fullText.equals("")) {
            redirectAttributes.addFlashAttribute("message", "Please fill in all fields");
            return "redirect:uploadStatus";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
