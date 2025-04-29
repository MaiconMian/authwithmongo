package io.github.maiconmian.authwithmongo.infraestructure.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final HttpStatus status;

    public BusinessException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}