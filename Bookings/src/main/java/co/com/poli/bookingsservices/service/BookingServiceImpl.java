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
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final UserClient userClient;
    private final CircuitBreakerFactory factory;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserClient userClient, CircuitBreakerFactory factory) {
        this.bookingRepository = bookingRepository;
        this.userClient = userClient;
        this.factory = factory;
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
        booking.get().setUser(findByIdUser(modelMapper, booking.get().getUserId()));
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
            booking.setUser(findById(modelMapper,booking.getUserId()));
            return convertBookingToResponseDTO(booking);
        } ).toList();
    }

    public User findByIdUser(ModelMapper modelMapper, Long userId){
    return factory.create("Service-User")
            .run(()->modelMapper.map(userClient.findById(userId).getData(), User.class),
                    e -> new User());
    }

    public User findById(ModelMapper modelMapper,  Long getUserId){
        return factory.create("Service-UserID")
                .run(()->modelMapper.map(userClient.findById(getUserId), User.class),
                        e -> new User());
    }
}
