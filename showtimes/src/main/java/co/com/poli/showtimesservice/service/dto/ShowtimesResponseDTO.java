package co.com.poli.showtimesservice.service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimesResponseDTO {
    private Long id;
    private LocalDate date;
    private List<MoviesDTO> movies;

}
