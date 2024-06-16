package co.com.poli.showtimesservice.service;

import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.service.dto.ShowtimesRequestDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesResponseDTO;

import java.util.List;

public interface IShowtimesServices {

    ShowtimesResponseDTO save(ShowtimesRequestDTO showtimesRequestDTO);

    List<ShowtimesResponseDTO> findAll();

    ShowtimesResponseDTO findById(Long id);

    ShowtimesResponseDTO update(Long id, ShowtimesRequestDTO showtimesRequestDTO);

    boolean validateIfExistShowtimesByMovieId(Long movieId);
}
