package co.com.poli.bookingsservices.controller;

import co.com.poli.bookingsservices.clientFeign.UserClient;
import co.com.poli.bookingsservices.helpers.Response;
import co.com.poli.bookingsservices.helpers.ResponseBuild;
import co.com.poli.bookingsservices.model.User;
import co.com.poli.bookingsservices.persistence.entity.Booking;
import co.com.poli.bookingsservices.service.BookingServiceImpl;
import co.com.poli.bookingsservices.service.dto.BookingRequestDTO;
import co.com.poli.bookingsservices.service.dto.BookingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final ResponseBuild responseBuild;

    @Autowired
    public BookingController(BookingServiceImpl bookingService, ResponseBuild responseBuild, UserClient user) {
        this.bookingService = bookingService;
        this.responseBuild = responseBuild;
    }

    @PostMapping
    @Operation(summary = "Crear un agendamiento", tags = { "Bookings" })
    public Response createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            return responseBuild.validationFailed(formatValidationErrors(result));
        }
        return responseBuild.successCreated(this.bookingService.save(bookingRequestDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener agendamiento por id", tags = { "Bookings" })
    public Response findBookingById(@PathVariable Long id) {
        BookingResponseDTO bookingResponseDTO = bookingService.findById(id);
        if (bookingResponseDTO != null) {
            return responseBuild.success(bookingResponseDTO);
        } else {
            return responseBuild.failedNotFound("Booking with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un agendamiento por id", tags = { "Bookings" })
    public Response deleteBookingById(@PathVariable Long id) {
        String message = bookingService.delete(id);
        if (message.equals("deleted")) {
            return responseBuild.success("Booking with ID " + id + " deleted");
        } else {
            return responseBuild.failedNotFound("Booking with ID " + id + " not found");
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener todas los agendamientos por userId", tags = { "Bookings" })
    public Response findBookingByUserId(@PathVariable Long userId) {
        BookingResponseDTO bookingResponseDTO = bookingService.findByUserId(userId);
        if (bookingResponseDTO != null) {
            return responseBuild.success(bookingResponseDTO);
        } else {
            return responseBuild.failedNotFound("Booking for user ID " + userId + " not found");
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los agendamientos", tags = { "Bookings" })
    public Response findAllBookings() {
        List<BookingResponseDTO> bookingResponseDTO = bookingService.findAll();
        if (!bookingResponseDTO.isEmpty()) {
            return responseBuild.success(bookingResponseDTO);
        } else {
            return responseBuild.failedNotFound("No bookings found");
        }
    }

    @GetMapping("validation-existence/{movieId}")
    @Operation(summary = "Verificar agendamiento de pelicula", description = "Verificar si la pelicula está asociada a alguna agenda", tags = { "Bookings" })
    public Response validExistence(@PathVariable("movieId") Long movieId) {
        Boolean resultValidation = this.bookingService.validateIfExistBookingsByMovieId(movieId);
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
