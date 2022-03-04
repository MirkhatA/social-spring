package com.example.socialspring.controllers;

import com.example.socialspring.entities.Post;
import com.example.socialspring.entities.User;
import com.example.socialspring.repositories.PostRepository;
import com.example.socialspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private static final Logger logger = LogManager.getLogger(PostController.class);

    @PostMapping
    private String postAdd(Post post, Authentication authentication) {
        User user = userRepo.findByUsername(authentication.getName()).orElse(null);
        if (user != null) {
            post.setUser(user);
            post.setIsComment(true);
            post.setStatus("internet");
            logger.info("Post created successfully!");

            postRepo.save(post);
        }
        else {
            logger.error("Some error!");
        }

        return "redirect:/";
    }

    @PostMapping("/update")
    private String postUpdate(@RequestParam String status, @RequestParam String comment, @RequestParam String id) {
        Post post = postRepo.findById(Long.parseLong(id)).orElse(null);

        if (post != null) {
            post.setStatus(status);
            post.setIsComment(comment.equals("enable"));
            postRepo.save(post);
            logger.info("Post updated successfully!");
        } else {
            logger.error("Some error!");
        }
        return "redirect:/user/profile";

    }
}
