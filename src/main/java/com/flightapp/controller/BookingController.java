package com.flightapp.controller;

import com.flightapp.request.BookingResponse;
import com.flightapp.request.CreateBookingRequest;
import com.flightapp.service.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/flight")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * POST /api/v1.0/flight/booking/{flightId}
     * Book ticket for a flightId.
     * Header X-User-Email must match request.userEmail to simulate logged-in user.
     */
    @PostMapping("/booking/{flightId}")
    public ResponseEntity<BookingResponse> bookTicket(
            @PathVariable("flightId") Long flightId,
            @RequestHeader("X-User-Email") String userEmailHeader,
            @Valid @RequestBody CreateBookingRequest req) {

        if (req.getUserEmail() == null || !req.getUserEmail().equalsIgnoreCase(userEmailHeader)) {
            return ResponseEntity.status(403).build();
        }

        BookingResponse saved = bookingService.createBooking(flightId, req);
        log.debug("Booking created: {}", saved.getPnr());
        return ResponseEntity.created(URI.create("/api/v1.0/flight/ticket/" + saved.getPnr())).body(saved);
    }

    /**
     * GET /api/v1.0/flight/ticket/{pnr}
     * Return booking details for given PNR.
     */
    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<BookingResponse> getByPnr(@PathVariable("pnr") String pnr) {
        BookingResponse resp = bookingService.getByPnr(pnr);
        return ResponseEntity.ok(resp);
    }

    /**
     * GET /api/v1.0/flight/booking/history/{emailId}
     * Return booking history for an email.
     */
    @GetMapping("/booking/history/{emailId}")
    public ResponseEntity<List<BookingResponse>> history(@PathVariable("emailId") String emailId) {
        List<BookingResponse> list = bookingService.getByUserEmail(emailId);
        return ResponseEntity.ok(list);
    }

    /**
     * DELETE /api/v1.0/flight/booking/cancel/{pnr}
     * Cancel a booking. Header X-User-Email must match owner.
     */
    @DeleteMapping("/booking/cancel/{pnr}")
    public ResponseEntity<Void> cancel(
            @PathVariable("pnr") String pnr,
            @RequestHeader("X-User-Email") String userEmailHeader) {

        bookingService.cancelBooking(pnr, userEmailHeader);
        log.debug("Booking cancelled: {}", pnr);
        return ResponseEntity.noContent().build();
    }
}