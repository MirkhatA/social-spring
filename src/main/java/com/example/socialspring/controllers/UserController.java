package com.example.socialspring.controllers;

import com.example.socialspring.entities.Post;
import com.example.socialspring.entities.User;
import com.example.socialspring.repositories.CommentRepository;
import com.example.socialspring.repositories.PostRepository;
import com.example.socialspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;

    private List<User> users = new ArrayList<>();
    private static final Logger logger = Logger.getGlobal();

    @GetMapping("/profile")
    private String profile(Authentication authentication, Model model) {
        User user = userRepo.findByUsername(authentication.getName()).orElse(null);
        model.addAttribute("commentRepo", commentRepo);
        model.addAttribute("posts", postRepo.findByUser(user));
        model.addAttribute("post", new Post());
        model.addAttribute("users", users);
        model.addAttribute("user", user);

        return "userProfile";
    }
}
