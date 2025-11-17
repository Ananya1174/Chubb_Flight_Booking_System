package com.flightapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.request.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {
    List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(String origin, String destination,
            LocalDateTime from, LocalDateTime to);
}
