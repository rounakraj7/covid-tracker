package com.wander.covidtracker.contoller;

import com.wander.covidtracker.exception.AppException;
import com.wander.covidtracker.model.User;
import com.wander.covidtracker.payload.ApiResponse;
import com.wander.covidtracker.payload.LoginRequest;
import com.wander.covidtracker.payload.SignUpRequest;
import com.wander.covidtracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.getLoginAccessToken(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        try {
            User result = authService.registerNewUser(signUpRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{username}")
                    .buildAndExpand(result.getEmail()).toUri();
            return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
        } catch (AppException ex) {
            return new ResponseEntity(new ApiResponse(false, ex.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(new ApiResponse(false, ex.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
