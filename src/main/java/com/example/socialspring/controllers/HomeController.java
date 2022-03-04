package com.example.socialspring.controllers;

import com.example.socialspring.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final PostRepository postRepo;

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        model.addAttribute("posts", postRepo.findByStatus("internet"));
        model.addAttribute("authorizedPosts", postRepo.findByStatus("authorized"));

        return "main";
    }


}
