package com.scheuled.barber.infra.web.exception;

import com.scheuled.barber.domain.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    // Trata erros de regras de negócio do nosso sistema (ex: regra dos 7 dias, barbeiro ocupado)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleBusinessValidation(ValidationException ex) {
        return ResponseEntity.badRequest().body(new ValidationErrorResponse(ex.getMessage()));
    }

    // Trata erros de validação dos DTOs (@NotBlank, @NotNull, etc)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorData>> handleValidationError(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors().stream()
                .map(FieldErrorData::new)
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    public record ValidationErrorResponse(String message) {}

    public record FieldErrorData(String field, String message) {
        public FieldErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
