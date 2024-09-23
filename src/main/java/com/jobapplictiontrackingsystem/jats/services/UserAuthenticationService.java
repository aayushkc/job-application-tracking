package com.jobapplictiontrackingsystem.jats.services;


import com.jobapplictiontrackingsystem.jats.entity.User;

public interface UserAuthenticationService {
    public boolean checkAuthDetails(User user);
    void saveUser(User user);
}
