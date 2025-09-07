package io.zipcoder.tc_spring_poll_application.exception;

import io.zipcoder.tc_spring_poll_application.dto.error.ErrorDetail;
import io.zipcoder.tc_spring_poll_application.dto.error.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        ErrorDetail ed = new ErrorDetail();
        ed.setTitle("Resource Not Found");
        ed.setStatus(HttpStatus.NOT_FOUND.value());
        ed.setDetail(ex.getMessage());
        ed.setDeveloperMessage(ex.getClass().getName());
        ed.setTimeStamp(new Date().getTime());
        return new ResponseEntity<>(ed, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ErrorDetail ed = new ErrorDetail();
        ed.setTitle("Validation Failure");
        ed.setStatus(HttpStatus.BAD_REQUEST.value());
        ed.setDetail("Input validation failed");
        ed.setDeveloperMessage(ex.getClass().getName());
        ed.setTimeStamp(new Date().getTime());

        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            ed.getErrors().computeIfAbsent(fe.getField(), k -> new ArrayList<>());
            ValidationError ve = new ValidationError();
            ve.setCode(fe.getCode());
            ve.setMessage(messageSource.getMessage(fe, null));
            ed.getErrors().get(fe.getField()).add(ve);
        }
        return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
    }
}
