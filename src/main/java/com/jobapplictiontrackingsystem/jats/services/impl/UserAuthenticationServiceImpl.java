package com.jobapplictiontrackingsystem.jats.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobapplictiontrackingsystem.jats.entity.User;
import com.jobapplictiontrackingsystem.jats.repo.UserRepository;
import com.jobapplictiontrackingsystem.jats.services.UserAuthenticationService;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService{

    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepo;

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override 
    public boolean checkAuthDetails(User user){
       
        User dbUser = userRepo.findByUserName(user.getUserName());
        if(dbUser == null){
            return false;
        }

        if(passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) return true;
        
        return false;
        
        }
           
        
        

    @Override
    public void saveUser(User user){
        logger.info("New User Created");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }



    
}
