package com.wander.covidtracker.payload;

import com.wander.covidtracker.model.Role;
import com.wander.covidtracker.model.User;
import lombok.Data;

import java.util.Set;

@Data
public class CurrentUserResponse {
    String name;
    String email;
    Set<Role> roles;

    public CurrentUserResponse(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.roles = user.getRoles();
    }
}
