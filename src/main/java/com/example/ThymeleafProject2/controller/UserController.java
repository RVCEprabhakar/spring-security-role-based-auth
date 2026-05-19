package com.example.ThymeleafProject2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ThymeleafProject2.entity.User;
import com.example.ThymeleafProject2.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute(
                "user",
                new User());

        return "register";
    }

    @PostMapping("/save")
    public String saveUser(
            @ModelAttribute User user) {

        userService.saveUser(user);

        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {

        return "dashboard";
    }

    @GetMapping("/profile")
    public String profilePage() {

        return "profile";
    }

    @GetMapping("/admin")
    public String adminPage() {

        return "admin";
    }
}