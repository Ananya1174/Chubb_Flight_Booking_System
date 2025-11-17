package com.flightapp.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.ResourceNotFoundException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.request.Booking;
import com.flightapp.request.BookingResponse;
import com.flightapp.request.CreateBookingRequest;
import com.flightapp.request.Flight;
import com.flightapp.PnrGenerator;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public BookingResponse createBooking(Long flightId, CreateBookingRequest req) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found: " + flightId));

        if (req.getSeatNumbers().size() != req.getSeats()) {
            throw new IllegalArgumentException("seatNumbers size must equal seats");
        }

        for (Integer s : req.getSeatNumbers()) {
            if (!flight.isSeatAvailable(s)) {
                throw new IllegalArgumentException("Seat " + s + " not available");
            }
        }

        flight.bookSeats(req.getSeatNumbers());
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setUserEmail(req.getUserEmail());
        booking.setContactName(req.getContactName());
        booking.setSeatsBooked(req.getSeats());
        booking.setSeatNumbers(req.getSeatNumbers());
        booking.setPassengers(req.getPassengers());
        booking.setFlight(flight);
        booking.setBookingTime(LocalDateTime.now());
        booking.setPnr(generateUniquePnr());

        double price = flight.getPrice() * req.getSeats();
        if (req.getReturnFlightId() != null) {
            Flight ret = flightRepository.findById(req.getReturnFlightId())
                    .orElseThrow(() -> new ResourceNotFoundException("Return flight not found: " + req.getReturnFlightId()));
            booking.setReturnFlight(ret);
            price += ret.getPrice() * req.getSeats();
        }
        booking.setTotalPrice(price);

        Booking saved = bookingRepository.save(booking);
        return BookingResponse.fromEntity(saved);
    }

    private String generateUniquePnr() {
        String p;
        int tries = 0;
        do {
            p = PnrGenerator.generatePnr();
            tries++;
            if (tries > 20) break;
        } while (bookingRepository.findByPnr(p).isPresent());
        return p;
    }

    @Override
    public BookingResponse getByPnr(String pnr) {
        Booking b = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for PNR: " + pnr));
        return BookingResponse.fromEntity(b);
    }

    @Override
    public List<BookingResponse> getByUserEmail(String email) {
        return bookingRepository.findByUserEmail(email).stream().map(BookingResponse::fromEntity).toList();
    }

    @Override
    public void cancelBooking(String pnr, String requesterEmail) {
        Booking b = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for PNR: " + pnr));

        if (!b.getUserEmail().equalsIgnoreCase(requesterEmail)) {
            throw new IllegalArgumentException("Only booking owner can cancel");
        }

        LocalDateTime now = LocalDateTime.now();
        if (b.getFlight() != null && Duration.between(now, b.getFlight().getDepartureTime()).toHours() < 24) {
            throw new IllegalArgumentException("Cannot cancel within 24 hours of departure");
        }

        if (b.getFlight() != null) {
            Flight f = b.getFlight();
            f.releaseSeats(b.getSeatNumbers());
            flightRepository.save(f);
        }
        bookingRepository.delete(b);
    }
}
