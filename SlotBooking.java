package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "slot_bookings")
public class SlotBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    private LocalDateTime slotDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference  // Prevents infinite JSON nesting
    private User user;

    private String status; // PENDING / CONFIRMED / CANCELLED

    public SlotBooking() {}

    public SlotBooking(String topic, LocalDateTime slotDateTime, User user, String status) {
        this.topic = topic;
        this.slotDateTime = slotDateTime;
        this.user = user;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDateTime getSlotDateTime() {
        return slotDateTime;
    }

    public void setSlotDateTime(LocalDateTime slotDateTime) {
        this.slotDateTime = slotDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
