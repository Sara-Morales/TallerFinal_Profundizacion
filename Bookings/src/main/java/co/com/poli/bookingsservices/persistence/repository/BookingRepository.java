package co.com.poli.bookingsservices.persistence.repository;


import co.com.poli.bookingsservices.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByUserId(Long userId);

    @Query(value = "SELECT * FROM bookings WHERE movies LIKE %:movie_id%",
            nativeQuery = true)

    Optional<List<Booking>> getBookingsByMovieId(@Param("movie_id") Long movieId);
}
