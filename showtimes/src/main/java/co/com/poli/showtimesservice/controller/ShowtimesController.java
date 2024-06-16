package co.com.poli.showtimesservice.controller;

import co.com.poli.showtimesservice.helpers.Response;
import co.com.poli.showtimesservice.helpers.ResponseBuild;
import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.service.ShowtimesServiceImpl;
import co.com.poli.showtimesservice.service.dto.ShowtimesRequestDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/showtimes")
@Tag(name = "Showtimes", description = "Programación de las peliculas")
public class ShowtimesController {

    private final ShowtimesServiceImpl showtimesService;
    private final ResponseBuild responseBuild;

    @Autowired
    public ShowtimesController(ShowtimesServiceImpl showtimesService, ResponseBuild responseBuild) {
        this.showtimesService = showtimesService;
        this.responseBuild = responseBuild;
    }

    @PostMapping
    @Operation(summary = "Guardar una programación", description = "Guardar una nueva programación en la base de datos", tags = { "Showtimes" })
    public Response saveShowtime(@Valid @RequestBody ShowtimesRequestDTO showtimesRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<Map<String, String>> errors = formatValidationErrors(result);
            return this.responseBuild.failedBadRequest(errors);
        }
        return this.responseBuild.success(this.showtimesService.save(showtimesRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las programaciones", description = "Recuperar una lista de todas las programaciones", tags = { "Showtimes" })
    public Response findAllShowtimes() {
        List<ShowtimesResponseDTO> showtimes = this.showtimesService.findAll();
        if (!showtimes.isEmpty()) {
            return this.responseBuild.success(showtimes);
        } else {
            return this.responseBuild.failedNotFound("No existen programaciones registradas");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una programación por ID", description = "Recuperar una programación por su ID", tags = { "Showtimes" })
    public Response findShowtimeById(@PathVariable("id") Long id) {
        ShowtimesResponseDTO showtimesResponseDTO = this.showtimesService.findById(id);
        if (showtimesResponseDTO != null) {
            return this.responseBuild.success(showtimesResponseDTO);
        } else {
            return this.responseBuild.failedNotFound("No existe una programación con el ID:" + id);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una programación", description = "Actualizar una nueva programación en la base de datos", tags = { "Showtimes" })
    public Response updateShowtime(@PathVariable("id") Long id, @Valid @RequestBody ShowtimesRequestDTO showtimesRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<Map<String, String>> errors = formatValidationErrors(result);
            return this.responseBuild.failedBadRequest(errors);
        }
        ShowtimesResponseDTO showtimes = this.showtimesService.update(id, showtimesRequestDTO);

        if (showtimes != null) {
            return this.responseBuild.success(showtimes);
        } else {
            return this.responseBuild.failedNotFound("No existen un programación con el id enviado");
        }
    }

    @GetMapping("validation-existence/{movieId}")
    @Operation(summary = "Verificar asociación de pelicula", description = "Verificar si la pelicula está asociada a alguna programación", tags = { "Showtimes" })
    public Response validExistence(@PathVariable("movieId") Long movieId) {
        Boolean resultValidation = this.showtimesService.validateIfExistShowtimesByMovieId(movieId);
        return this.responseBuild.successValidation(resultValidation);
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
