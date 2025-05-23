package io.github.maiconmian.authwithmongo.infraestructure.exception;

import io.github.maiconmian.authwithmongo.api.reponse.ApiResponseFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHadler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseFormat<String>> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ApiResponseFormat<String>(ex.getMessage(), null));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponseFormat<String>> handleAuthenticationFailedException(AuthException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ApiResponseFormat<String>(ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseFormat<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(400)
                .body(new ApiResponseFormat<>(errorMessage, null));
    }
}
