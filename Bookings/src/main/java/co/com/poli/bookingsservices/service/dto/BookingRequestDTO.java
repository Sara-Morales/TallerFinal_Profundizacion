package co.com.poli.bookingsservices.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDTO {
    @Min(value = 1, message = "La userId mínimo es 1")
    @NotNull(message = "El userId no puede ser nulo")
    private Long userId;

    @Min(value = 1, message = "La showtimeId mínimo es 1")
    @NotNull(message = "El showtimeId no puede ser nulo")
    private Long showtimeId;

    @NotNull(message = "Las peliculas son obligatorias")
    @Size(min = 1)
    @Valid
    private List<MoviesDTO> movies;
}
