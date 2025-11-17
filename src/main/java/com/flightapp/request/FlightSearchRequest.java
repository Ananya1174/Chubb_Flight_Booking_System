package com.flightapp.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class FlightSearchRequest {
    @NotBlank private String origin;
    @NotBlank private String destination;
    private LocalDateTime from;
    private LocalDateTime to;

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public LocalDateTime getFrom() { return from; }
    public void setFrom(LocalDateTime from) { this.from = from; }
    public LocalDateTime getTo() { return to; }
    public void setTo(LocalDateTime to) { this.to = to; }
}
