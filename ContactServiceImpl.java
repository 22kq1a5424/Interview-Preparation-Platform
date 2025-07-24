package com.example.demo.service;


import com.example.demo.model.ContactMessage;
import com.example.demo.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMessageRepository repository;

    @Override
    public ContactMessage saveMessage(ContactMessage message) {
        return repository.save(message);
    }
}

