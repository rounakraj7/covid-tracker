package com.wander.covidtracker.service;

import com.wander.covidtracker.exception.ResourceNotFoundException;
import com.wander.covidtracker.model.User;
import com.wander.covidtracker.payload.CurrentUserResponse;
import com.wander.covidtracker.repository.UserRepository;
import com.wander.covidtracker.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Fetch details of the current signed in user.
     * @param userPrincipal
     * @return CurrentUserResponse
     */
    public CurrentUserResponse getCurrentUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User Profile ", "id", userPrincipal.getId()));
        return new CurrentUserResponse(user);
    }
}
