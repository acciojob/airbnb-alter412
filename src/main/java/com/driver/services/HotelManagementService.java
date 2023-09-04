package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {

    @Autowired
    HotelManagementRepository hmr;
    public String addHotel(Hotel hotel) {
        if(hotel==null || hotel.getHotelName()==null){
            return "FAILURE";
        }
        return hmr.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hmr.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hmr.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        String id=UUID.randomUUID().toString();
        booking.setBookingId(id);

        return hmr.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hmr.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hmr.updateFacilities(newFacilities,hotelName);
    }
}
