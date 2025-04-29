package io.github.maiconmian.authwithmongo.api.controllers;

import io.github.maiconmian.authwithmongo.api.DTO.UserDTO;
import io.github.maiconmian.authwithmongo.api.mappers.UserMapper;
import io.github.maiconmian.authwithmongo.api.reponse.ApiResponse;
import io.github.maiconmian.authwithmongo.business.service.UserService;
import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import io.github.maiconmian.authwithmongo.infraestructure.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v1/users")
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserEntity>>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<List<UserEntity>>("OK", "Users found successfully", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserEntity>> getUserById(@PathVariable String id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<UserEntity>("OK", "Users found successfully", user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserEntity>> addUser(@Valid @RequestBody UserDTO userDTO) {
        UserEntity user = userService.saveUser(UserMapper.toEntity(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<UserEntity>("OK", "Users created successfully", user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserEntity>> editUser(@Valid @RequestBody UserDTO userDTO, @PathVariable String id) {
        UserEntity user = userService.updateUser(UserMapper.toEntity(userDTO), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<UserEntity>("OK", "User update successfully", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserEntity>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<UserEntity>("Ok", "User deleted successfully", null));
    }

}
