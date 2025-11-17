package com.flightapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flightapp.ResourceNotFoundException;
import com.flightapp.repository.FlightRepository;
import com.flightapp.request.Flight;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight createFlight(Flight flight) {
        flight.setAvailableSeats(flight.getTotalSeats());
        return flightRepository.save(flight);
    }

    @Override
    public Flight getById(Long id) {
        Optional<Flight> o = flightRepository.findById(id);
        return o.orElseThrow(() -> new ResourceNotFoundException("Flight not found: " + id));
    }

    @Override
    public List<Flight> search(String origin, String destination, LocalDateTime from, LocalDateTime to) {
        return flightRepository.findByOriginAndDestinationAndDepartureTimeBetween(origin, destination, from, to);
    }
}
