package com.flightapp.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class BookingResponse {
    private String pnr;
    private String userEmail;
    private String contactName;
    private Long flightId;
    private Long returnFlightId;
    private int seatsBooked;
    private Set<Integer> seatNumbers;
    private List<PassengerInfo> passengers;
    private LocalDateTime bookingTime;
    private double totalPrice;
    /**
     * Default constructor required by JPA/Hibernate.
     * Do not remove.
     */
    public BookingResponse() {// no-args constructor required for JPA
    	
    }

    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
    public Long getReturnFlightId() { return returnFlightId; }
    public void setReturnFlightId(Long returnFlightId) { this.returnFlightId = returnFlightId; }
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

    public static BookingResponse fromEntity(Booking b) {
        BookingResponse r = new BookingResponse();
        r.setPnr(b.getPnr());
        r.setUserEmail(b.getUserEmail());
        r.setContactName(b.getContactName());
        r.setFlightId(b.getFlight() != null ? b.getFlight().getId() : null);
        r.setReturnFlightId(b.getReturnFlight() != null ? b.getReturnFlight().getId() : null);
        r.setSeatsBooked(b.getSeatsBooked());
        r.setSeatNumbers(b.getSeatNumbers());
        r.setPassengers(b.getPassengers());
        r.setBookingTime(b.getBookingTime());
        r.setTotalPrice(b.getTotalPrice());
        return r;
    }
}
