package com.example.demo.controller;

import com.example.demo.model.ContactMessage;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*") // Allows frontend access
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/submit")
    public ContactMessage submitMessage(@RequestBody ContactMessage message) {
        return contactService.saveMessage(message);
    }
}
