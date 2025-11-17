package com.flightapp.service;

import java.util.List;

import com.flightapp.request.BookingResponse;
import com.flightapp.request.CreateBookingRequest;

public interface BookingService {
    BookingResponse createBooking(Long flightId, CreateBookingRequest req);
    BookingResponse getByPnr(String pnr);
    List<BookingResponse> getByUserEmail(String email);
    void cancelBooking(String pnr, String requesterEmail);
}
