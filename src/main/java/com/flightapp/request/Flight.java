package com.flightapp.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String airline;

    private String airlineLogoUrl;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @Min(1)
    private int totalSeats;

    @Min(0)
    private int availableSeats;

    private double price;

    @ElementCollection
    @CollectionTable(name = "flight_booked_seats", joinColumns = @JoinColumn(name = "flight_id"))
    @Column(name = "seat_number")
    private Set<Integer> bookedSeats = new HashSet<>();

    public Flight() {
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

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Set<Integer> getBookedSeats() { return bookedSeats; }
    public void setBookedSeats(Set<Integer> bookedSeats) { this.bookedSeats = bookedSeats; }

    public boolean isSeatAvailable(Integer seatNumber) {
        return seatNumber != null && seatNumber >= 1 && seatNumber <= totalSeats && !bookedSeats.contains(seatNumber);
    }

    public void bookSeats(Set<Integer> seats) {
        if (seats != null) {
            bookedSeats.addAll(seats);
            this.availableSeats = totalSeats - bookedSeats.size();
        }
    }

    public void releaseSeats(Set<Integer> seats) {
        if (seats == null || seats.isEmpty()) return;
        bookedSeats.removeAll(seats);
        availableSeats += seats.size();
        if (totalSeats > 0 && availableSeats > totalSeats) {
            availableSeats = totalSeats;
        }
    }

    @Override
    public String toString() {
        return "Flight{" + "id=" + id + ", airline='" + airline + '\'' + '}';
    }
}