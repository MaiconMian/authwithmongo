package io.github.maiconmian.authwithmongo.api.controllers;

import io.github.maiconmian.authwithmongo.api.reponse.ApiResponseFormat;
import io.github.maiconmian.authwithmongo.business.service.RoleService;
import io.github.maiconmian.authwithmongo.infraestructure.entity.RoleEntity;
import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Busca todos os papeis", description = "Recupera a lista de todos os papeis cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Papéis encontrados com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoleEntity.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Nenhum papel encontrado")
    })
    @GetMapping
    public ResponseEntity<ApiResponseFormat<List<RoleEntity>>> getAllRoles() {
        List<RoleEntity> roles = roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseFormat<List<RoleEntity>>("Roles found successfully", roles));
    }
}
