package com.groupeisi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityAlreadyExistsException extends RuntimeException {
    String message;
//    HttpStatus status;
}
