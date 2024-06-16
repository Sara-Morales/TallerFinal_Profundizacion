package co.com.poli.showtimesservice.service;

import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.persistence.repository.ShowtimesRepository;
import co.com.poli.showtimesservice.service.dto.MoviesDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesRequestDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ShowtimesServiceImplTest {

    @Mock
    private ShowtimesRepository showtimesRepository;

    private ShowtimesServiceImpl showtimesService;

    List<MoviesDTO> moviesDTOList;
    List<Showtimes> showtimesList;
    List<ShowtimesResponseDTO> showtimesResponseDTOList;
    ShowtimesRequestDTO showtimesRequestDTO;
    ShowtimesRequestDTO showtimesRequestDTOToUpdate;
    Showtimes showtimes;
    Showtimes showtimes2;
    ShowtimesResponseDTO showtimesResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        showtimesService = new ShowtimesServiceImpl(showtimesRepository);

        Long id = 1L;
        moviesDTOList = List.of(
                new MoviesDTO("Enrredados","James Cameron", 5),
                new MoviesDTO("Rapidos y furiosos","James Cameron 2", 2),
                new MoviesDTO("Scary movie","James Cameron 3", 4)
        );
        showtimesList = List.of(
                new Showtimes(
                        1L,
                        LocalDate.now(),
                        List.of(
                            moviesDTOList.get(0), moviesDTOList.get(1)
                        ).toString()
                ),
                new Showtimes(
                        2L,
                        LocalDate.now(),
                        List.of(
                                moviesDTOList.get(1), moviesDTOList.get(2)
                        ).toString()
                ),
                new Showtimes(
                        3L,
                        LocalDate.now(),
                        List.of(
                                moviesDTOList.get(0), moviesDTOList.get(2)
                        ).toString()
                )
        );
        showtimesResponseDTOList = List.of(
                new ShowtimesResponseDTO(
                        showtimesList.get(0).getId(),
                        showtimesList.get(0).getDate(),
                        List.of(
                                moviesDTOList.get(0), moviesDTOList.get(1)
                        )
                ),
                new ShowtimesResponseDTO(
                        showtimesList.get(1).getId(),
                        showtimesList.get(1).getDate(),
                        List.of(
                                moviesDTOList.get(1), moviesDTOList.get(2)
                        )
                ),
                new ShowtimesResponseDTO(
                        showtimesList.get(2).getId(),
                        showtimesList.get(2).getDate(),
                        List.of(
                                moviesDTOList.get(0), moviesDTOList.get(2)
                        )
                )
        );
        showtimesRequestDTO = new ShowtimesRequestDTO();
        showtimesRequestDTO.setDate(LocalDate.now());
        showtimesRequestDTO.setMovies(List.of(moviesDTOList.get(0)));
        showtimesRequestDTOToUpdate = new ShowtimesRequestDTO();
        showtimesRequestDTOToUpdate.setDate(LocalDate.now());
        showtimesRequestDTOToUpdate.setMovies(List.of(moviesDTOList.get(0), moviesDTOList.get(2)));
        showtimes = new Showtimes();
        showtimes.setDate(showtimesRequestDTO.getDate());
        showtimes.setMovies(showtimesRequestDTO.getMovies().toString());

        showtimes2 = new Showtimes();
        showtimes2.setId(id);
        showtimes2.setDate(showtimesRequestDTO.getDate());
        showtimes2.setMovies(showtimesRequestDTO.getMovies().toString());
        showtimesResponseDTO = new ShowtimesResponseDTO();
        showtimesResponseDTO.setId(id);
        showtimesResponseDTO.setDate(showtimesRequestDTO.getDate());
        showtimesResponseDTO.setMovies(showtimesRequestDTO.getMovies());
    }

    @Test
    void saveOk() {
        //Arrange
        when(showtimesRepository.save(showtimes)).thenReturn(showtimes2);
        //Act
        ShowtimesResponseDTO result = this.showtimesService.save(showtimesRequestDTO);
        //Assert
        assertEquals(showtimesResponseDTO, result);
    }

    @Test
    void findAllOk() {
        //Arrange
        when(showtimesRepository.findAll()).thenReturn(showtimesList);
        //Act
        List<ShowtimesResponseDTO> result = this.showtimesService.findAll();
        //Assert
        assertEquals(showtimesResponseDTOList, result);
    }

    @Test
    void findByIdWthNotFound() {
        //Arrange
        Long id = 1L;
        when(showtimesRepository.findById(id)).thenReturn(Optional.empty());
        //Act
        ShowtimesResponseDTO result = this.showtimesService.findById(id);
        // Assert
        assertNull(result);
    }

    @Test
    void findByIdOk() {
        //Arrange
        Long id = 1L;
        when(showtimesRepository.findById(id)).thenReturn(Optional.of(showtimesList.get(0)));
        //Act
        ShowtimesResponseDTO result = this.showtimesService.findById(id);
        // Assert
        assertEquals(showtimesResponseDTOList.get(0), result);
    }

    @Test
    void updateWithNotFound() {
        //Arrange
        Long id = 1L;
        when(showtimesRepository.findById(id)).thenReturn(Optional.empty());
        //Act
        ShowtimesResponseDTO result = this.showtimesService.update(id, showtimesRequestDTOToUpdate);
        // Assert
        assertNull(result);
    }

    @Test
    void updateOk() {
        //Arrange
        Long id = 1L;
        when(showtimesRepository.findById(id)).thenReturn(Optional.of(showtimesList.get(0)));
        Showtimes showtimesToUpdate = showtimesList.get(2);
        showtimesToUpdate.setId(id);
        showtimesResponseDTOList.get(2).setId(id);
        when(showtimesRepository.save(showtimesToUpdate)).thenReturn(showtimesToUpdate);
        //Act
        ShowtimesResponseDTO result = this.showtimesService.update(id, showtimesRequestDTOToUpdate);
        // Assert
        assertEquals(showtimesResponseDTOList.get(2), result);
    }
}