package com.flightapp.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

public class CreateBookingRequest {
    @Email @NotBlank private String userEmail;
    @NotBlank private String contactName;
    @Min(1) private int seats;
    @NotEmpty private List<PassengerInfo> passengers;
    @NotEmpty private Set<Integer> seatNumbers;
    private Long returnFlightId; 

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }
    public List<PassengerInfo> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerInfo> passengers) { this.passengers = passengers; }
    public Set<Integer> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(Set<Integer> seatNumbers) { this.seatNumbers = seatNumbers; }
    public Long getReturnFlightId() { return returnFlightId; }
    public void setReturnFlightId(Long returnFlightId) { this.returnFlightId = returnFlightId; }
}
