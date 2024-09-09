package com.jobapplictiontrackingsystem.jats.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.jobapplictiontrackingsystem.jats.entity.User;

@Controller
@RequestMapping("/")
public class BaseUserController {

    protected static List<User> userList = new ArrayList<>();
    
    @GetMapping("/")
    public String indexPage(){
        return "home";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("userRegisterForm", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        userList.add(user);
        return "redirect:/login";
    }
}
