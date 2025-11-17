package com.flightapp.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    private String pnr;

    @Email
    @NotBlank
    private String userEmail;

    @NotBlank
    private String contactName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_flight_id")
    private Flight returnFlight;

    @Min(1)
    private int seatsBooked;

    @ElementCollection
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_number")
    private Set<Integer> seatNumbers = new HashSet<>();


    @ElementCollection
    @CollectionTable(name = "booking_passengers", joinColumns = @JoinColumn(name = "booking_id"))
    private List<PassengerInfo> passengers = new ArrayList<>();

    private LocalDateTime bookingTime;

    private double totalPrice;
    /**
     * Default constructor required by JPA/Hibernate.
     * Do not remove.
     */
    public Booking() {// no-args constructor required for JPA
    	
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public Flight getReturnFlight() { return returnFlight; }
    public void setReturnFlight(Flight returnFlight) { this.returnFlight = returnFlight; }

    public int getSeatsBooked() { return seatsBooked; }
    public void setSeatsBooked(int seatsBooked) { this.seatsBooked = seatsBooked; }

    public Set<Integer> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(Set<Integer> seatNumbers) { this.seatNumbers = seatNumbers; }

    public List<PassengerInfo> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerInfo> passengers) { this.passengers = passengers; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "Booking{" + "pnr='" + pnr + '\'' + ", userEmail='" + userEmail + '\'' + '}';
    }
}
