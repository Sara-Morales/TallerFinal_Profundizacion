package com.example.user.service.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
    @NotEmpty(message = "El nombre es obligatorio")
    private String name;

    @NotEmpty(message = "El apellido es obligatorio")
    private String last_name;
}
