package co.com.poli.moviesservice.service;

import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.service.dto.MoviesInDTO;

import java.util.List;

public interface IMoviesServices {

    Movies save(MoviesInDTO moviesInDTO);

    String delete(Long id);

    List<Movies> findAll();

    Movies findById(Long id);
}
