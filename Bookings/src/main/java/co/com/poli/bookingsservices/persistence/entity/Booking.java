package co.com.poli.bookingsservices.persistence.entity;

import co.com.poli.bookingsservices.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "showtime_id", nullable = false)
    private Long showtimeId;

    @Column(columnDefinition = "JSON", nullable = false)
    private String movies;

    @Transient
    private User user;
}
