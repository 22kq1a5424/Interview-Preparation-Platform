package com.example.demo.service;

import com.example.demo.model.SlotBooking;
import com.example.demo.repository.SlotBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotBookingServiceImpl implements SlotBookingService {

    @Autowired
    private SlotBookingRepository slotBookingRepository;

    @Override
    public SlotBooking bookSlot(SlotBooking booking) {
        
        return slotBookingRepository.save(booking);
    }

    @Override
    public List<SlotBooking> getSlotsByUserId(Long userId) {
        return slotBookingRepository.findByUser_Id(userId);
    }
}
