package com.jobapplictiontrackingsystem.jats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.jobapplictiontrackingsystem.jats.entity.User;
import com.jobapplictiontrackingsystem.jats.services.impl.UserAuthenticationServiceImpl;

@Controller
@RequestMapping("/")
public class BaseUserController {


    @Autowired
    UserAuthenticationServiceImpl userAuthenticationService;

    @GetMapping("/")
    public String indexPage(){
        return "home";
    }

    @GetMapping("/register-as-seeker")
    public String registerSeekerPage(Model model){
        model.addAttribute("userRegisterForm", new User());
        return "register/register-as-jobSeeker";
    }

    @GetMapping("/register-as-recruiter")
    public String registerRecrutierPage(Model model){
        model.addAttribute("userRegisterForm", new User());
        return "register/register-as-recruiter";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        userAuthenticationService.saveUser(user);
        return "redirect:/login";
    }   
}
