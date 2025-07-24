package com.example.demo.controller;

import com.example.demo.model.SlotBooking;
import com.example.demo.model.User;
import com.example.demo.service.SlotBookingService;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = "*")
public class SlotBookingController {

    @Autowired
    private SlotBookingService service;

    @Autowired
    private UserRepository userRepository;

    // ✅ Book a slot ONLY if user is logged in (via session)
    @PostMapping("/book")
    public ResponseEntity<?> bookSlot(@RequestBody SlotBooking slot, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(401).body("You must be logged in to book a slot.");
        }

        slot.setUser(loggedInUser); // override user with session user
        SlotBooking saved = service.bookSlot(slot);
        return ResponseEntity.ok(saved);
    }

    // ✅ Book using legacy method, but check session user
    @PostMapping("/book-via-email")
    public ResponseEntity<?> bookSlotViaEmail(@RequestBody SlotBookingRequest request, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(401).body("You must be logged in to book a slot.");
        }

        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime().split(" ")[0] + ":00");
        LocalDateTime slotDateTime = LocalDateTime.of(date, time);

        SlotBooking slot = new SlotBooking();
        slot.setUser(loggedInUser);
        slot.setSlotDateTime(slotDateTime);
        slot.setStatus("PENDING");
        slot.setTopic("Interview Slot");

        SlotBooking saved = service.bookSlot(slot);
        return ResponseEntity.ok(saved);
    }
    
    
    

    // ✅ Get bookings for current logged-in user only
    @GetMapping("/my-slots")
    public ResponseEntity<?> getUserSlots(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        List<SlotBooking> bookings = service.getSlotsByUserId(loggedInUser.getId());
        return ResponseEntity.ok(bookings);
    }

    // ❌ No longer needed if you use session:
    @GetMapping("/user/{userId}")
    public List<SlotBooking> getUserSlotsById(@PathVariable Long userId) {
        return service.getSlotsByUserId(userId);
    }

 
    static class SlotBookingRequest {
        private String name;
        private String email;
        private String date;
        private String time;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
    }
}
