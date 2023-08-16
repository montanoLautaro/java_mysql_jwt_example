package com.example.crud_mysql_jwt.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
@Setter
public class YoViAppException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String message;

    public YoViAppException(HttpStatus status, String message){
        super();
        this.status = status;
        this.message = message;
    }

    public YoViAppException(HttpStatus status, String message, String message1){
        super();
        this.status = status;
        this.message = message;
        this.message = message1;
    }


}
