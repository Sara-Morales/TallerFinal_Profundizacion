package co.com.poli.bookingsservices.service;


import co.com.poli.bookingsservices.persistence.entity.Booking;
import co.com.poli.bookingsservices.service.dto.BookingRequestDTO;
import co.com.poli.bookingsservices.service.dto.BookingResponseDTO;

import java.util.List;

public interface IBookingService {

    BookingResponseDTO save(BookingRequestDTO bookingRequestDTO);

    BookingResponseDTO findById(Long id);

    String delete(Long id);

    List<BookingResponseDTO> findAll();

    BookingResponseDTO findByUserId(Long userId);
}
