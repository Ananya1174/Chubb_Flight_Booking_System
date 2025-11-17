package com.flightapp.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AddInventoryRequest {
    @NotBlank private String airline;
    private String airlineLogoUrl;
    @NotBlank private String origin;
    @NotBlank private String destination;
    @NotNull private LocalDateTime departureTime;
    @NotNull private LocalDateTime arrivalTime;
    @Min(1) private int totalSeats;
    @Min(0) private double price;

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
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
