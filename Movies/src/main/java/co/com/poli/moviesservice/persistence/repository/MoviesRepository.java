package co.com.poli.moviesservice.persistence.repository;

import co.com.poli.moviesservice.persistence.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {}
