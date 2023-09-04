package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class HotelManagementRepository {

    HashMap<String,Hotel> hotelDB = new HashMap<>();
    HashMap<Integer,User> userDB = new HashMap<>();

    HashMap<String,Booking> bookingDB = new HashMap<>();

    HashMap<String,List<Facility>> hotelFacilities = new HashMap<>();

    HashMap<Integer,List<String>> userBookings = new HashMap<>();

    public String addHotel(Hotel hotel) {

        if(hotelDB.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }

        hotelDB.put(hotel.getHotelName(),hotel);
        if(hotel.getFacilities()!=null){
            hotelFacilities.put(hotel.getHotelName(),hotel.getFacilities());
        }
        return "SUCCESS";
    }

    public Integer addUser(User user) {

        userDB.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {

        int max=0;
        String ans="";

        for(String h: hotelFacilities.keySet()){
            if(hotelFacilities.get(h).size()>max){
                max=hotelFacilities.get(h).size();
                ans=h;
            }else if(hotelFacilities.get(h).size()==max&& h.compareTo(ans)<0){
                ans=h;
            }
        }
        return ans;
    }

    public int bookARoom(Booking booking) {

        int rr=booking.getNoOfRooms();
        String hn=booking.getHotelName();

        if(!hotelDB.containsKey(hn)){
            return -1;
        }

        int ar = hotelDB.get(hn).getAvailableRooms();

        if(ar<rr){
            return -1;
        }

        int ppn = hotelDB.get(hn).getPricePerNight();
        int total = ppn*rr;
        hotelDB.get(hn).setAvailableRooms(ar-rr);
        booking.setAmountToBePaid(total);

        bookingDB.put(booking.getBookingId(),booking);
        if(userBookings.containsKey(booking.getBookingAadharCard())){
            userBookings.get(booking.getBookingAadharCard()).add(booking.getBookingId());
        }else{
            List<String> temp = new ArrayList<>();
            temp.add(booking.getBookingId());
            userBookings.put(booking.getBookingAadharCard(),temp);
        }

        return total;
    }

    public int getBookings(Integer aadharCard) {
        if(userBookings.containsKey(aadharCard)){
            return userBookings.get(aadharCard).size();
        }
        return 0;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        if(!hotelDB.containsKey(hotelName)){
            return null;
        }

        Hotel h = hotelDB.get(hotelName);
        List<Facility> f = h.getFacilities();
        if(f==null){
            f = new ArrayList<>();
        }
        for(Facility x: newFacilities){
            if(!f.contains(x)){
                f.add(x);
            }
        }
        return h;
    }
}
