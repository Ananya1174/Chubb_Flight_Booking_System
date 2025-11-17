package com.flightapp.service;

import com.flightapp.ResourceNotFoundException;
import com.flightapp.request.Flight;
import com.flightapp.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    @Mock FlightRepository flightRepository;
    @InjectMocks FlightServiceImpl flightService; 

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void createFlight_setsAvailableSeats_and_saves() {
        Flight f = new Flight();
        f.setAirline("A");
        f.setTotalSeats(100);
        f.setPrice(1000.0);

        when(flightRepository.save(any())).thenAnswer(i -> {
            Flight saved = i.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Flight saved = flightService.createFlight(f);
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getAvailableSeats()).isEqualTo(100);
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void getById_whenNotFound_throws() {
        when(flightRepository.findById(5L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> flightService.getById(5L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Flight not found");
    }

    @Test
    void search_callsRepositoryWithDates() {
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now().plusDays(1);
        flightService.search("DEL","BLR",from,to);
        verify(flightRepository).findByOriginAndDestinationAndDepartureTimeBetween("DEL","BLR",from,to);
    }
}