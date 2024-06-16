package co.com.poli.showtimesservice.service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MoviesDTO {
    @NotEmpty(message = "El título es obligatorio")
    private String title;

    @NotEmpty(message = "El director es obligatorio")
    private String director;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer rating;

    @Override
    public String toString() {
        return "{\"title\":\"" + title + "\", \"director\":\"" + director + "\", \"rating\":" + rating + "}";
    }
}

