package io.github.maiconmian.authwithmongo.api.controllers;

import io.github.maiconmian.authwithmongo.api.DTO.UserDTO;
import io.github.maiconmian.authwithmongo.api.mappers.UserMapper;
import io.github.maiconmian.authwithmongo.api.reponse.ApiResponseFormat;
import io.github.maiconmian.authwithmongo.business.service.UserService;
import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Busca todos os usuários", description = "Recupera a lista de todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuários encontrados com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse(responseCode = "404", description = "Não existem usuários")
    })
    @GetMapping
    public ResponseEntity<ApiResponseFormat<List<UserEntity>>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseFormat<>("Users found successfully", users));
    }

    @Operation(summary = "Busca usuário por ID", description = "Recupera um usuário específico pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado com o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseFormat<UserEntity>> getUserById(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable String id) {

        UserEntity user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseFormat<>("User found successfully", user));
    }

    @Operation(summary = "Criação de um novo usuário", description = "Cria um novo usuário no sistema a partir de um DTO com as informações necessárias.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de entrada")
    })
    @PostMapping
    public ResponseEntity<ApiResponseFormat<UserEntity>> addUser(
            @Parameter(description = "Objeto contendo os dados do novo usuário", required = true)
            @Valid @RequestBody UserDTO userDTO) {

        UserEntity user = userService.saveUser(UserMapper.toEntity(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseFormat<>("User created successfully", user));
    }

    @Operation(summary = "Atualizar informações do usuário", description = "Atualiza os dados de um usuário existente com base no ID e nos novos dados informados.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro na atualização do usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseFormat<UserEntity>> editUser(
            @Parameter(description = "ID do usuário a ser atualizado", required = true)
            @PathVariable String id,
            @Parameter(description = "Objeto contendo os novos dados do usuário", required = true)
            @Valid @RequestBody UserDTO userDTO) {

        UserEntity user = userService.updateUser(UserMapper.toEntity(userDTO), id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseFormat<>("User updated successfully", user));
    }

    @Operation(summary = "Excluir usuário", description = "Exclui um usuário do sistema pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseFormat<UserEntity>> deleteUser(
            @Parameter(description = "ID do usuário a ser excluído", required = true)
            @PathVariable String id) {

        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseFormat<>("User deleted successfully", null));
    }
}
