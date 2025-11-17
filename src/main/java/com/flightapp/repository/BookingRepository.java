package com.flightapp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.flightapp.request.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    Optional<Booking> findByPnr(String pnr);
    List<Booking> findByUserEmail(String userEmail);
}
