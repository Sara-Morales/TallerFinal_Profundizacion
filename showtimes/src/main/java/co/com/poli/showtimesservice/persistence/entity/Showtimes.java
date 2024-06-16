package co.com.poli.showtimesservice.persistence.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "showtimes")
@NoArgsConstructor
@AllArgsConstructor
public class Showtimes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "JSON", nullable = false)
    private String movies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showtimes showtimes = (Showtimes) o;
        return Objects.equals(id, showtimes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
