package com.jobapplictiontrackingsystem.jats.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jobapplictiontrackingsystem.jats.entity.User;
import com.jobapplictiontrackingsystem.jats.services.impl.UserAuthenticationServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/login")
public class UserAuthenticationController {

    @Autowired
    UserAuthenticationServiceImpl userAuthenticationService;

    @GetMapping
    public String logInForm(Model model){
        model.addAttribute("userLoginForm", new User());
        return "login";
    }

    @PostMapping
    public String checkLogInDetails(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
      
        boolean checkAuthDetails = userAuthenticationService.checkAuthDetails(user);
        
        if(checkAuthDetails){ 
            redirectAttributes.addFlashAttribute("userName", user.getUserName());
            return "redirect:/user/index";
        }
        model.addAttribute("worngCredential", "The provided credentials were worng.");
        model.addAttribute("userLoginForm", new User());
        return "login";  
    }
    
}
