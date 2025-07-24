package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Register new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> existingUser = userService.getUserByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered!");
        }

        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ Login and store user in session
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
        String email = user.getEmail();
        String password = user.getPassword();

        User validUser = userService.loginUser(email, password);

        if (validUser != null) {
            session.setAttribute("loggedInUser", validUser);
            return ResponseEntity.ok(validUser);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // ✅ Get logged-in user from session
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            return ResponseEntity.ok(user);
        } 
        else {
            return ResponseEntity.status(401).body("Not logged in");
        }
    }

    // ✅ Logout user and invalidate session
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

    // ✅ Get user by email (optional, for profile etc.)
    @GetMapping("/get-by-email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } 
        else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
