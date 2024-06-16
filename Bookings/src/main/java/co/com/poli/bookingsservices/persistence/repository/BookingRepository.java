package co.com.poli.bookingsservices.persistence.repository;


import co.com.poli.bookingsservices.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findByUserId(Long userId);
}
