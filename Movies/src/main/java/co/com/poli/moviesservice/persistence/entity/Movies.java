package co.com.poli.moviesservice.persistence.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "movies")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotEmpty(message = "El título no puede estar vacío")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "El director no puede estar vacío")
    @Column(name = "director")
    private String director;

    @Min(value = 1, message = "El rating mínimo es 1")
    @Max(value = 5, message = "El rating máximo es 5")
    @Column(name = "rating")
    private int rating;

}
