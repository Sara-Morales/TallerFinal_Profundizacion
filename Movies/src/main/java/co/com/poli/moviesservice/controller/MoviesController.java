package co.com.poli.moviesservice.controller;

import co.com.poli.moviesservice.helpers.Response;
import co.com.poli.moviesservice.helpers.ResponseBuild;
import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.service.MoviesServiceImpl;
import co.com.poli.moviesservice.service.dto.MoviesInDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Operaciones relacionadas con las películas")
public class MoviesController {

    private final MoviesServiceImpl moviesService;
    private final ResponseBuild responseBuild;

    @Autowired
    public MoviesController(MoviesServiceImpl moviesService, ResponseBuild responseBuild) {
        this.moviesService = moviesService;
        this.responseBuild = responseBuild;
    }

    @PostMapping
    @Operation(summary = "Guardar una película", description = "Guardar una nueva película en la base de datos", tags = { "Movies" })
    public Response saveMovie(@Valid @RequestBody MoviesInDTO moviesInDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<Map<String, String>> errors = formatValidationErrors(result);
            return this.responseBuild.failedBadRequest(errors);
        }
        return this.responseBuild.success(this.moviesService.save(moviesInDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una película por ID", description = "Eliminar una película de la base de datos por su ID", tags = { "Movies" })
    public Response deleteMovie(@PathVariable("id") Long id) {
        String message = this.moviesService.delete(id);
        if (message.equals("eliminado")) {
            return this.responseBuild.success("La película con ID:" + id + " fue eliminada.");
        } else {
            return this.responseBuild.failedNotFound("La película con ID:" + id + " no existe.");
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todas las películas", description = "Recuperar una lista de todas las películas", tags = { "Movies" })
    public Response findAllMovies() {
        List<Movies> movies = this.moviesService.findAll();
        if (!movies.isEmpty()) {
            return this.responseBuild.success(movies);
        } else {
            return this.responseBuild.failedNotFound("No existen películas registradas");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una película por ID", description = "Recuperar una película por su ID", tags = { "Movies" })
    public Response findMovieById(@PathVariable("id") Long id) {
        Movies movie = this.moviesService.findById(id);
        if (movie != null) {
            return this.responseBuild.success(movie);
        } else {
            return this.responseBuild.failedNotFound("No existe una película para el ID:" + id);
        }
    }

    private List<Map<String, String>> formatValidationErrors(BindingResult result) {
        return result.getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put(error.getField(), error.getDefaultMessage());
                    return err;
                })
                .collect(Collectors.toList());
    }
}
