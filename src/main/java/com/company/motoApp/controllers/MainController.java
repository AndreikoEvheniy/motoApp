package com.company.motoApp.controllers;

import com.company.motoApp.models.Post;
import com.company.motoApp.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String homePage(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return "homePage";
    }

    @GetMapping("/addPost")
    public String pageAddPost(Model model) {
        return "addPost";
    }
}
