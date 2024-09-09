package com.jobapplictiontrackingsystem.jats.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jobapplictiontrackingsystem.jats.entity.User;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/login")
public class UserAuthenticationController {
    @GetMapping
    public String logInForm(Model model){
        model.addAttribute("userLoginForm", new User());
        return "login";
    }

    @PostMapping
    public String checkLogInDetails(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
      
        List<User> users = BaseUserController.userList;
        boolean isAuthOk = false;
        for(User userDetails: users){
            if(userDetails.getUserName().equals(user.getUserName()) && userDetails.getPassword().equals(user.getPassword())){
                isAuthOk = true;
                break;
            }
            isAuthOk = false;
        }

        if(isAuthOk){ 
            redirectAttributes.addFlashAttribute("userName", user.getUserName());
            return "redirect:/user/index";
        }
        model.addAttribute("worngCredential", "The provided credentials were worng.");
        model.addAttribute("userLoginForm", new User());
        return "login";  
    }
    
}
