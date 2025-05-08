package io.github.maiconmian.authwithmongo.api.controllers;

import io.github.maiconmian.authwithmongo.api.DTO.LoginDTO;
import io.github.maiconmian.authwithmongo.api.reponse.ApiResponseFormat;
import io.github.maiconmian.authwithmongo.config.security.SecurityFilter;
import io.github.maiconmian.authwithmongo.config.security.TokenService;
import io.github.maiconmian.authwithmongo.infraestructure.entity.UserEntity;
import io.github.maiconmian.authwithmongo.infraestructure.exception.AuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityFilter securityHelper;
    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Login do usuário", description = "Realiza o login do usuário utilizando email e senha e retorna um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseFormat.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseFormat.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseFormat<String>> login(@Valid @RequestBody LoginDTO loginDTO) {
        String a = "a";
        var userPassword = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        try {
            var auth = this.authenticationManager.authenticate(userPassword);
            var token = tokenService.generateToken((UserEntity) auth.getPrincipal());
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(60 * 60)
                    .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new ApiResponseFormat<String>("Login successfully!", null));
        } catch (Exception e) {
            throw new AuthException(a + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Logout do usuário", description = "Realiza o logout do usuário, removendo o token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseFormat.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseFormat<String>> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new ApiResponseFormat<String>("Logout successfully!", null));
    }

    @Operation(summary = "Validação do token JWT", description = "Valida o token JWT presente no cabeçalho ou cookies da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseFormat.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseFormat.class)))
    })
    @GetMapping("/validate")
    public ResponseEntity<ApiResponseFormat<String>> validateToken(HttpServletRequest request) {

        String token = securityHelper.recoveryToken(request);
        if (token != null) {
            try {
                tokenService.validateToken(token);
                return ResponseEntity.ok().body(new ApiResponseFormat<String>("Valid Token!", null));
            } catch (RuntimeException e) {
                throw new AuthException(e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseFormat<String>("Invalid Token!", null));
    }
}

