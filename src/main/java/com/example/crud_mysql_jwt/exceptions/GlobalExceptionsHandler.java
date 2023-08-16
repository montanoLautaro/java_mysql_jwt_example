package com.example.crud_mysql_jwt.exceptions;

import com.example.crud_mysql_jwt.dto.ErrorDetails;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Permite a la clase manejar excepciones de toda la aplicacion
@ControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {

    // Todas las Exception de la app se van a manejar con el siguiente metodo, mostrando la hora, mensaje y la url que
    // generó el problema
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handlerGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // este metodo se va a encargar de manejar las excepciones del tipo ResourceNotFoundException, la cual es uan excepcion propia
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                            // obtengo el nombre del campo de la tabla que esta generando el error
                            String fieldName = ((FieldError) error).getField();
                            String message = error.getDefaultMessage();
                            errors.put(fieldName, message);
                        }
                );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
