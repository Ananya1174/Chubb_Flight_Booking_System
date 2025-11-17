package com.flightapp.controller;

import com.flightapp.request.AddInventoryRequest;
import com.flightapp.request.Flight;
import com.flightapp.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @SuppressWarnings("removal")
	@MockBean
    private FlightService flightService;

    @Test
    void addInventory_returnsCreated() throws Exception {
        AddInventoryRequest req = new AddInventoryRequest();
        req.setAirline("A");
        req.setOrigin("DEL");
        req.setDestination("BLR");
        req.setDepartureTime(LocalDateTime.now().plusDays(1));
        req.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        req.setTotalSeats(100);
        req.setPrice(1000.0);

        Flight savedFlight = new Flight();
        savedFlight.setId(10L);
        savedFlight.setAirline("A");

        given(flightService.createFlight(any())).willReturn(savedFlight);

        mvc.perform(post("/api/v1.0/flight/airline/inventory/add")
                .contentType("application/json")
                .content(mapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(10));
    }
}