package com.example.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParkingServiceException {

    @ExceptionHandler(ParkingFullException.class)
    protected ResponseEntity<ErrorDetails> parkingSlotsFull(ParkingFullException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidSlotNumberException.class)
    protected ResponseEntity<ErrorDetails> invalidTicketNumber(InvalidSlotNumberException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorDetails.setMessage(error.getDefaultMessage());
        });
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
