package com.flightapp.controller;

import java.net.URI;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flightapp.request.AddInventoryRequest;
import com.flightapp.request.Flight;
import com.flightapp.request.FlightSearchRequest;
import com.flightapp.request.FlightSearchResponse;
import com.flightapp.service.FlightService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightController {

    
    private final FlightService flightService;
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    /**
     * Add Inventory/Schedule for an airline (Admin)
     * POST /api/v1.0/flight/airline/inventory/add
     */
    @PostMapping("/airline/inventory/add")
    public ResponseEntity<Flight> addInventory(@Valid @RequestBody AddInventoryRequest req) {
        Flight f = new Flight();
        f.setAirline(req.getAirline());
        f.setAirlineLogoUrl(req.getAirlineLogoUrl());
        f.setOrigin(req.getOrigin());
        f.setDestination(req.getDestination());
        f.setDepartureTime(req.getDepartureTime());
        f.setArrivalTime(req.getArrivalTime());
        f.setTotalSeats(req.getTotalSeats());
        f.setAvailableSeats(req.getTotalSeats());
        f.setPrice(req.getPrice());

        Flight saved = flightService.createFlight(f);
        log.debug("Added flight inventory: {}", saved);
        return ResponseEntity.created(URI.create("/api/v1.0/flight/" + saved.getId())).body(saved);
    }

    /**
     * Search Flights
     * POST /api/v1.0/flight/search
     */
    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponse>> search(@Valid @RequestBody FlightSearchRequest req) {
        var from = req.getFrom() != null ? req.getFrom() : java.time.LocalDateTime.now().minusDays(1);
        var to = req.getTo() != null ? req.getTo() : java.time.LocalDateTime.now().plusDays(365);
        
        List<Flight> flights = flightService.search(req.getOrigin(), req.getDestination(), from, to);

        List<FlightSearchResponse> resp = flights.stream().map(f -> {
            FlightSearchResponse r = new FlightSearchResponse();
            r.setId(f.getId());
            r.setAirline(f.getAirline());
            r.setAirlineLogoUrl(f.getAirlineLogoUrl());
            r.setOrigin(f.getOrigin());
            r.setDestination(f.getDestination());
            r.setDepartureTime(f.getDepartureTime());
            r.setArrivalTime(f.getArrivalTime());
            r.setAvailableSeats(f.getAvailableSeats());
            r.setPrice(f.getPrice());
            return r;
        }).toList();

        return ResponseEntity.ok(resp);
    }
}