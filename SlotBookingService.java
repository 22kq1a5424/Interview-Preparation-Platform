package com.example.demo.service;


import com.example.demo.model.SlotBooking;

import java.util.List;

public interface SlotBookingService {
    SlotBooking bookSlot(SlotBooking booking);
    List<SlotBooking> getSlotsByUserId(Long userId);
}

