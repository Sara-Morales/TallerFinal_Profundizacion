package co.com.poli.bookingsservices.service;

import co.com.poli.bookingsservices.clientFeign.UserClient;
import co.com.poli.bookingsservices.model.User;
import co.com.poli.bookingsservices.persistence.entity.Booking;
import co.com.poli.bookingsservices.persistence.repository.BookingRepository;
import co.com.poli.bookingsservices.service.dto.BookingRequestDTO;
import co.com.poli.bookingsservices.service.dto.BookingResponseDTO;
import co.com.poli.bookingsservices.service.dto.MoviesDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final UserClient userClient;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserClient userClient) {
        this.bookingRepository = bookingRepository;
        this.userClient = userClient;
    }

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

    private BookingResponseDTO convertBookingToResponseDTO(Booking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setId(booking.getId());
        bookingResponseDTO.setUserId(booking.getUserId());
        bookingResponseDTO.setShowtimeId(booking.getShowtimeId());
        bookingResponseDTO.setMovies(convertStringToMovies(booking.getMovies()));
        bookingResponseDTO.setUser(booking.getUser());
        return bookingResponseDTO;
    }

    private Booking convertRequestDTOToBooking(BookingRequestDTO bookingRequestDTO) {
        Booking booking = new Booking();
        booking.setUserId(bookingRequestDTO.getUserId());
        booking.setShowtimeId(bookingRequestDTO.getShowtimeId());
        booking.setMovies(bookingRequestDTO.getMovies().toString());
        return booking;
    }

    @Override
    public BookingResponseDTO save(BookingRequestDTO bookingRequestDTO) {
        Booking newBooking = bookingRepository.save(convertRequestDTOToBooking(bookingRequestDTO));
        return convertBookingToResponseDTO(newBooking);
    }

    @Override
    public BookingResponseDTO findById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Booking> booking = bookingRepository.findById(id);
        User user = modelMapper.map(userClient.findById(booking.get().getUserId()).getData(), User.class);
        booking.get().setUser(user);
        return booking.map(this::convertBookingToResponseDTO).orElse(null);
    }

    @Override
    public String delete(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return "deleted";
        }
        return "not found";
    }

    @Override
    public BookingResponseDTO findByUserId(Long userId) {
        return convertBookingToResponseDTO(bookingRepository.findByUserId(userId));
    }

    public boolean validateIfExistBookingsByMovieId(Long movieId) {
        Optional<List<Booking>> booking = bookingRepository.getBookingsByMovieId(movieId);
        return booking.isPresent() && !booking.get().isEmpty();
    }

    @Override
    public List<BookingResponseDTO> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map((booking)-> {
            User user = modelMapper.map(userClient.findById(booking.getUserId()).getData(), User.class);
            booking.setUser(user);
            return convertBookingToResponseDTO(booking);
        } ).toList();
    }
}
