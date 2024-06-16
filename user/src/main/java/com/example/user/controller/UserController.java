package com.example.user.controller;

import com.example.user.helper.Response;
import com.example.user.helper.ResponseBuild;
import com.example.user.persistence.entity.User;
import com.example.user.service.DTO.UserDTO;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseBuild responseBuild;

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLast_name());
        return user;
    }

    @PostMapping
    public Response save(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<Map<String, String>> errors = formatValidationErrors(result);
            return this.responseBuild.failedBadRequest(errors);
        }
        User user = convertToEntity(userDTO);
        userService.save(user);
        return responseBuild.success(user);
    }

    @GetMapping
    public Response findAll() {
        return responseBuild.success(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return responseBuild.failedNotFound("El usuario con ID " + id + " no existe");
        }
        userService.delete(user);
        return responseBuild.success("Usuario eliminado correctamente");
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
