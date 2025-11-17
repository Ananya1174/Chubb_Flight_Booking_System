package com.flightapp.service;

import com.flightapp.request.Flight;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    Flight createFlight(Flight flight);
    Flight getById(Long id);
    List<Flight> search(String origin, String destination, LocalDateTime from, LocalDateTime to);
}
