package co.com.poli.showtimesservice.persistence.repository;

import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimesRepository extends JpaRepository<Showtimes, Long> {
    @Query(value = "SELECT * FROM showtimes WHERE movies LIKE %:movie_id%",
            nativeQuery = true)
    Optional<List<Showtimes>> getShowtimesByMovieId(@Param("movie_id") Long movieId);
}
