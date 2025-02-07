package com.example.chat.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody String username, @RequestBody String name, @RequestBody String password) {
        // Verify if username exists
        // Verify password criteria
        // Encrypt password
        // Store data to users table
        //
        return ResponseEntity.status(HttpStatus.CREATED).body("Sign up successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String username, @RequestBody String password) {
        // Spring Security Login
        // Generate a jwt token on successful login
        return ResponseEntity.ok("Login Successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Logout logic
        // Invalidate jwt token
        return ResponseEntity.ok("Logout Successful");
    }

}
