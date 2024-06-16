package co.com.poli.showtimesservice.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ShowtimesRequestDTO {
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate date;

    @NotNull(message = "Las peliculas son obligatorias")
    @Size(min = 1)
    @Valid
    private List<MoviesDTO> movies;
}

