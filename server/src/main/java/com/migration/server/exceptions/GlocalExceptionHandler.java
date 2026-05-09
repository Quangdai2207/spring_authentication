package com.migration.server.exceptions;

import com.migration.server.dtos.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlocalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlocalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleNotFound(NotFoundException e) {
        return ApiResponse.notFound(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleBadRequest(BadRequestException e) {
        return ApiResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleValidation(MethodArgumentNotValidException e) {
        String DtoName = e.getBindingResult().getObjectName();
        System.out.println("Loi Vavlidation DTO class: " + DtoName);
        List<String> message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toList();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            System.out.println("FIELD: " + err.getField());
            System.out.println("ERROR: " + err.getDefaultMessage());
        });

        return ApiResponse.badRequest(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleInvalidBody(HttpMessageNotReadableException e) {
        System.out.println(e.getMessage());
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        return ApiResponse.badRequest("Data invalid");
    }

    @ExceptionHandler(DataAccessException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleDatabaseError(DataAccessException e) {
        log.error("Database error: ", e);
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        return ApiResponse.internalServerError("Database error occurred");
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ApiResponse<T>> handleAll(Exception e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        log.error("Unexpected error: ", e);
        return ApiResponse.notFound("An error occurred!");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleApiNotFound(NoResourceFoundException e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        log.error("Resource not found: ", e.getMessage());
        return ApiResponse.notFound("API Not Found!");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        Map<String, String> errorCaseMap = Map.of(
                "uk_users_email", "Email already exists",
                "uk_users_phone", "Phone already exists"
        );
        String error = e.getMostSpecificCause().getMessage();
        System.out.println("Loi o day...");

        for (String key : errorCaseMap.keySet()) {
            if (error.contains(key))
                return ApiResponse.badRequest(errorCaseMap.get(key));
        }

        return ApiResponse.badRequest("Data is duplicated or existed");
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleOptimisticLockFailure(OptimisticLockingFailureException e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        return ApiResponse.badRequest("Data was modified by another request");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        System.out.println(e.getMessage());
        return ApiResponse.badRequest("Api not found");
    }

    /// ExceptionHandler custom xu ly query string URL khong dung tham so format quy dinh cua API.
    @ExceptionHandler(IllegalParamException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleIllegalParam(IllegalParamException e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        return ApiResponse.badRequest("Param invalid.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleUsernameNotFound(BadCredentialsException e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        log.error("BadCredentialsException.class ", e.getMessage());
        return ApiResponse.badRequest("Your account is invalid.");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleInvalidDataAccessApiUsage(InvalidDataAccessApiUsageException e) {
        String DtoName = e.getClass().getSimpleName();
        System.out.println("Loi tai Class: " + DtoName);
        return ApiResponse.badRequest("Data access invalid");
    }
}
