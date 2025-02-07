package com.example.chat.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable Long id) {
        // make sure there is correct permissions
        // call user service to delete user account
            // set delete flag inside userservice
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Account deleted");
    }
}
