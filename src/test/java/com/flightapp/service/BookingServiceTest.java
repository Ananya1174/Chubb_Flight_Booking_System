package com.flightapp.service;

import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.request.Booking;
import com.flightapp.request.CreateBookingRequest;
import com.flightapp.request.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock BookingRepository bookingRepository;
    @Mock FlightRepository flightRepository;
    @InjectMocks BookingServiceImpl bookingService;

    @BeforeEach void setup(){ MockitoAnnotations.openMocks(this); }

    @Test
    void createBooking_whenSeatNotAvailable_throws() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setTotalSeats(2);
        flight.setAvailableSeats(2);
        flight.setBookedSeats(new HashSet<>(Arrays.asList(1))); 

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        CreateBookingRequest req = new CreateBookingRequest();
        req.setUserEmail("u@x.com");
        req.setContactName("c");
        req.setSeats(1);
        req.setSeatNumbers(Set.of(1)); 

        assertThatThrownBy(() -> bookingService.createBooking(1L, req))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("not available");
    }

    @Test
    void createBooking_success_generatesPnr_and_reducesSeats() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setTotalSeats(100);
        flight.setAvailableSeats(100);
        flight.setBookedSeats(new HashSet<>());

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(bookingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        CreateBookingRequest req = new CreateBookingRequest();
        req.setUserEmail("u@x.com");
        req.setContactName("c");
        req.setSeats(2);
        req.setSeatNumbers(Set.of(1,2));
        req.setPassengers(new ArrayList<>()); 

        var resp = bookingService.createBooking(1L, req);
        assertThat(resp.getPnr()).isNotBlank();
        verify(flightRepository).save(flight);
        assertThat(flight.getAvailableSeats()).isEqualTo(98);
    }

    @Test
    void cancelBooking_wrongUser_throws() {
        Booking b = new Booking();
        b.setPnr("PNR1");
        b.setUserEmail("owner@x.com");
        when(bookingRepository.findByPnr("PNR1")).thenReturn(Optional.of(b));
        assertThatThrownBy(() -> bookingService.cancelBooking("PNR1","notowner@x.com"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Only booking owner");
    }

    @Test
    void cancelBooking_within24H_throws() {
        Flight f = new Flight();
        f.setDepartureTime(LocalDateTime.now().plusHours(10));
        Booking b = new Booking();
        b.setPnr("P");
        b.setFlight(f);
        b.setUserEmail("u@x.com");
        when(bookingRepository.findByPnr("P")).thenReturn(Optional.of(b));
        assertThatThrownBy(() -> bookingService.cancelBooking("P","u@x.com"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("24 hours");
    }

    @Test
    void cancelBooking_success_releasesSeats_and_deletes() {
        Flight f = new Flight();
        f.setId(1L);
        f.setTotalSeats(10);
        f.setBookedSeats(new HashSet<>(Set.of(1,2)));
        f.setAvailableSeats(8);
        f.setDepartureTime(LocalDateTime.now().plusDays(2));    
        
        Booking b = new Booking();
        b.setPnr("P");
        b.setUserEmail("u@x.com");
        b.setFlight(f);
        b.setSeatNumbers(Set.of(1,2));

        when(bookingRepository.findByPnr("P")).thenReturn(Optional.of(b));

        bookingService.cancelBooking("P","u@x.com");

        verify(flightRepository).save(f);
        verify(bookingRepository).delete(b);

        assertThat(f.getBookedSeats()).doesNotContain(1,2);
        assertThat(f.getAvailableSeats()).isEqualTo(10);  
    }
}