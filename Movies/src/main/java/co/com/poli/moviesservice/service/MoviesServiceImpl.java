package co.com.poli.moviesservice.service;

import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.persistence.repository.MoviesRepository;
import co.com.poli.moviesservice.service.dto.MoviesInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviesServiceImpl implements IMoviesServices {

    private final MoviesRepository moviesRepository;

    public Movies save(MoviesInDTO moviesInDTO) {
        Movies movie = new Movies();
        movie.setTitle(moviesInDTO.getTitle());
        movie.setDirector(moviesInDTO.getDirector());
        movie.setRating(moviesInDTO.getRating());
        return moviesRepository.save(movie);
    }

    public String delete(Long id) {
        Optional<Movies> movie = moviesRepository.findById(id);
        if (movie.isPresent()) {
            moviesRepository.delete(movie.get());
            return "eliminado";
        }
        return "inexistente";
    }

    public List<Movies> findAll() {
        return moviesRepository.findAll();
    }

    public Movies findById(Long id) {
        return moviesRepository.findById(id).orElse(null);
    }
}
