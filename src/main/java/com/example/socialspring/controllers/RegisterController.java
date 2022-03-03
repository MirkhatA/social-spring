package com.example.socialspring.controllers;

import com.example.socialspring.entities.User;
import com.example.socialspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    @GetMapping
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String registerPost(User user, HttpServletRequest request) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            logger.error("This user is already taken");
            return "redirect:/register";
        } else {
            // encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            logger.info(user.getUsername() + " successfully registered");
            return "redirect:/login";
        }
    }
}
