package com.example.demo.repository;

import com.example.demo.model.SlotBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotBookingRepository extends JpaRepository<SlotBooking, Long> {
    
    // This is the correct and preferred method
    List<SlotBooking> findByUser_Id(Long userId);
}
