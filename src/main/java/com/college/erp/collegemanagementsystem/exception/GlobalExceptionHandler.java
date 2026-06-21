package com.college.erp.collegemanagementsystem.exception;

import java.util.List;
import java.util.stream.Collectors;
import com.college.erp.collegemanagementsystem.dto.RestResponseDTO;
import com.college.erp.collegemanagementsystem.enums.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author grish
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestResponseDTO> handleNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                ResponseStatus.FAILURE,
                exception.getMessage(),
                request.getRequestURI(),
                List.of());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<RestResponseDTO> handleDuplicate(DuplicateResourceException exception, HttpServletRequest request) {
        return buildResponse(
                HttpStatus.CONFLICT,
                ResponseStatus.FAILURE,
                exception.getMessage(),
                request.getRequestURI(),
                List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponseDTO> handleValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<String> details = exception.getBindingResult().getFieldErrors().stream().map(this::formatFieldError).collect(Collectors.toList());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ResponseStatus.VALIDATION_FAILED,
                "Validation failed",
                request.getRequestURI(),
                details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestResponseDTO> handleConstraintViolation(ConstraintViolationException exception, HttpServletRequest request) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ResponseStatus.VALIDATION_FAILED,
                "Validation failed",
                request.getRequestURI(),
                exception.getConstraintViolations().stream().map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).collect(Collectors.toList()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RestResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException exception, HttpServletRequest request) {
        String detailMessage = exception.getMostSpecificCause() != null && exception.getMostSpecificCause().getMessage() != null ? exception.getMostSpecificCause().getMessage() : exception.getMessage();
        return buildResponse(
                HttpStatus.CONFLICT,
                ResponseStatus.FAILURE,
                "Database constraint violation",
                request.getRequestURI(),
                detailMessage != null ? List.of(detailMessage) : List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponseDTO> handleGeneric(Exception exception, HttpServletRequest request) {
        String detailMessage = exception.getMessage() != null ? exception.getMessage() : exception.getClass().getSimpleName();
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ResponseStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error",
                request.getRequestURI(),
                List.of(detailMessage));
    }

    private ResponseEntity<RestResponseDTO> buildResponse(HttpStatus status, ResponseStatus responseStatus, String message, String path, List<String> details) {
        RestResponseDTO.RestResponseDTOBuilder builder = RestResponseDTO.builder()
                .code(responseStatus.getKey())
                .status(responseStatus.getValue())
                .message(message)
                .detail(path);
        if (details != null) {
            builder.details(details);
        }
        return ResponseEntity.status(status).body(builder.build());
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
