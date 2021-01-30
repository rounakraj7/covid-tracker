package com.wander.covidtracker.service;

import com.wander.covidtracker.exception.AppException;
import com.wander.covidtracker.model.Role;
import com.wander.covidtracker.model.RoleName;
import com.wander.covidtracker.model.User;
import com.wander.covidtracker.payload.JwtAuthenticationResponse;
import com.wander.covidtracker.payload.LoginRequest;
import com.wander.covidtracker.payload.SignUpRequest;
import com.wander.covidtracker.repository.RoleRepository;
import com.wander.covidtracker.repository.UserRepository;
import com.wander.covidtracker.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * When a user signs in this method will generate a JWT access token which will be used for subsequent REST api calls
     * @param loginRequest
     * @return JWT token.
     */
    public JwtAuthenticationResponse getLoginAccessToken(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * This methods takes Sing up request and creates a new User
      * @param signUpRequest
     * @return newly created User.
     */
    public User registerNewUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AppException("User already exists");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        //set the role.
        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }
}
