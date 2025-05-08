package io.github.maiconmian.authwithmongo.infraestructure.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AuthException extends RuntimeException {
    private final HttpStatus status;

    public AuthException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
