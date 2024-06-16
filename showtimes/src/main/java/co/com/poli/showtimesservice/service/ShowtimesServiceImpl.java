package co.com.poli.showtimesservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.persistence.repository.ShowtimesRepository;
import co.com.poli.showtimesservice.service.dto.MoviesDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesRequestDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowtimesServiceImpl implements IShowtimesServices {

    private final ShowtimesRepository showtimesRepository;

    private List<MoviesDTO> convertStringToMovies(String movies) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (movies.startsWith("\"") && movies.endsWith("\"")) {
                movies = movies.substring(1, movies.length() - 1).replace("\\\"", "\"");
            }
            // Deserializar la cadena JSON a una lista de MoviesDTO
            return objectMapper.readValue(movies, new TypeReference<List<MoviesDTO>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private ShowtimesResponseDTO convertShowtimeToResponseDTO(Showtimes showtime) {
        ShowtimesResponseDTO showtimesResponseDTO = new ShowtimesResponseDTO();
        showtimesResponseDTO.setId(showtime.getId());
        showtimesResponseDTO.setDate(showtime.getDate());
        showtimesResponseDTO.setMovies(convertStringToMovies(showtime.getMovies()));
        return showtimesResponseDTO;
    }

    private Showtimes convertRequestDTOToShowtimes(ShowtimesRequestDTO showtimesRequestDTO) {
        Showtimes showtime = new Showtimes();
        showtime.setDate(showtimesRequestDTO.getDate());
        showtime.setMovies(showtimesRequestDTO.getMovies().toString());
        return showtime;
    }

    public ShowtimesResponseDTO save(ShowtimesRequestDTO showtimesRequestDTO) {
        Showtimes newShowtime = showtimesRepository.save(convertRequestDTOToShowtimes(showtimesRequestDTO));
        return convertShowtimeToResponseDTO(newShowtime);
    }

    public List<ShowtimesResponseDTO> findAll() {
        List<Showtimes> showtimes = showtimesRepository.findAll();
        return showtimes.stream().map(this::convertShowtimeToResponseDTO).toList();
    }

    public ShowtimesResponseDTO findById(Long id) {
        Optional<Showtimes> showtime = showtimesRepository.findById(id);
        if(showtime.isPresent()){
            return convertShowtimeToResponseDTO(showtime.get());
        }else{
            return null;
        }
    }

    public ShowtimesResponseDTO update(Long id, ShowtimesRequestDTO showtimesRequestDTO) {
        Optional<Showtimes> optionalShowtime = showtimesRepository.findById(id);
        if (optionalShowtime.isPresent()) {
            Showtimes beforeShowtimes = convertRequestDTOToShowtimes(showtimesRequestDTO);
            beforeShowtimes.setId(id);
            Showtimes afterShowtimes = showtimesRepository.save(beforeShowtimes);
            return convertShowtimeToResponseDTO(afterShowtimes);
        } else {
            return null;
        }
    }


    public boolean validateIfExistShowtimesByMovieId(Long movieId) {
        Optional<List<Showtimes>> showtime = showtimesRepository.getShowtimesByMovieId(movieId);
        return showtime.isPresent() && !showtime.get().isEmpty();
    }
}
