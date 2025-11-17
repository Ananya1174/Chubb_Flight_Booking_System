package com.flightapp.controller;

import com.flightapp.request.CreateBookingRequest;
import com.flightapp.request.BookingResponse;
import com.flightapp.request.PassengerInfo;      // ensure this exists
import com.flightapp.service.BookingService;
import com.flightapp.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @SuppressWarnings("removal")
	@MockBean private BookingService bookingService;
    @SuppressWarnings("removal")
	@MockBean private FlightService flightService;    

    private PassengerInfo samplePassenger() {
        PassengerInfo p = new PassengerInfo();
        p.setName("Test User");
        p.setGender("F");
        p.setAge(25);
        p.setMealPreference("VEG");
        return p;
    }

    @Test
    void bookTicket_headerMismatch_returns403() throws Exception {
        CreateBookingRequest req = new CreateBookingRequest();
        req.setUserEmail("owner@x.com");
        req.setContactName("c");
        req.setSeats(1);
        req.setSeatNumbers(Set.of(1));
        req.setPassengers(List.of(samplePassenger()));  

        mvc.perform(post("/api/v1.0/flight/booking/1")
                .contentType("application/json")
                .header("X-User-Email", "other@x.com")   
                .content(mapper.writeValueAsString(req)))
            .andExpect(status().isForbidden());
    }

    @Test
    void bookTicket_success_returnsCreated() throws Exception {
        CreateBookingRequest req = new CreateBookingRequest();
        req.setUserEmail("owner@x.com");
        req.setContactName("c");
        req.setSeats(1);
        req.setSeatNumbers(Set.of(1));
        req.setPassengers(List.of(samplePassenger()));   

        BookingResponse response = new BookingResponse();
        response.setPnr("PNR1");

        given(bookingService.createBooking(eq(1L), any())).willReturn(response);

        mvc.perform(post("/api/v1.0/flight/booking/1")
                .contentType("application/json")
                .header("X-User-Email", "owner@x.com")
                .content(mapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.pnr").value("PNR1"));
    }
}