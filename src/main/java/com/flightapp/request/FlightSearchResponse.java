package com.flightapp.request;

import java.time.LocalDateTime;

public class FlightSearchResponse {
    private Long id;
    private String airline;
    private String airlineLogoUrl;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int availableSeats;
    private double price;

    public FlightSearchResponse() {
    	// no-args constructor required for JPA
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }
    public String getAirlineLogoUrl() { return airlineLogoUrl; }
    public void setAirlineLogoUrl(String airlineLogoUrl) { this.airlineLogoUrl = airlineLogoUrl; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public static FlightSearchResponse fromEntity(Flight f) {
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
    }
}
