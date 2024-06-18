package co.com.poli.bookingsservices.service.dto;

import co.com.poli.bookingsservices.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private Long showtimeId;
    private List<MoviesDTO> movies;
    private User user;
}
