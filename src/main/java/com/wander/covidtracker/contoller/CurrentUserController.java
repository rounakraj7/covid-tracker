package com.wander.covidtracker.contoller;

import com.wander.covidtracker.payload.CurrentUserResponse;
import com.wander.covidtracker.security.CurrentUser;
import com.wander.covidtracker.security.UserPrincipal;
import com.wander.covidtracker.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/current/")
public class CurrentUserController {

    @Autowired
    private CurrentUserService currentUserService;

    @GetMapping("me")
    public CurrentUserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
              return currentUserService.getCurrentUser(userPrincipal);
    }
}
