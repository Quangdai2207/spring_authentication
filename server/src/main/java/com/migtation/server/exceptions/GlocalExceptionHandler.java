package com.migtation.server.exceptions;

import com.migtation.server.dtos.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlocalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
        System.out.println(DtoName + " Loi o day");
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

    /// Body null khong co {} hoac sai Json - dung cho cac request body tu client
    /// Exception Handler nay ap dung cho Validation data (request body)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleInvalidBody(HttpMessageNotReadableException e) {
        System.out.println(e.getMessage());
        return ApiResponse.badRequest("Data invalid");
    }

    /// 500 - DB error
    @ExceptionHandler(DataAccessException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleDatabaseError(DataAccessException e) {
        log.error("Database error: ", e);
        return ApiResponse.internalServerError("Database error occurred");
    }

    /// Undefined other Exception - fallback (du phong)
    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ApiResponse<T>> handleAll(Exception e) {
        log.error("Unexpected error: ", e);
        return ApiResponse.notFound("An error occurred!");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleApiNotFound(NoResourceFoundException e) {
        log.error("Resource not found: ", e.getMessage());
        return ApiResponse.notFound("API Not Found!");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        Map<String, String> errorCaseMap = Map.of(
                "uk_users_email", "Email already exists", /// Spring lay noi dung FK tu FK_name trong DB
                "uk_users_phone", "Phone already exists" /// Spring lay noi dung FK tu FK_name trong DB
        );
        String error = e.getMostSpecificCause().getMessage();
        System.out.println("Loi o day...");

        for (String key : errorCaseMap.keySet()) {
            if (error.contains(key))
                return ApiResponse.badRequest(errorCaseMap.get(key));
        }

        return ApiResponse.badRequest("Data is duplicated or existed");
    }

    /// ExceptionHandler OptimisticLockingFailureException ap dung cho muc dich khi tai cung 1 thoi diem co nhieu request tuong
    /// tac tren cung 1 du lieu. Ngoai le nay giup cho viec du lieu duoc bao toan chinh xac tranh bi sai du lieu khi du lieu duoc
    /// cap nhat troong cung mot thoi diem.
    /// GlobalException tu dong goi ExceptionHandler OptimisticLockingFailureException de xu ly khi xay ra truong hop tren ma khong
    /// can phai goi thu cong.
    /// <br /><br />
    /// Dieu kien ap dung ExceptionHandle OptimisticLockingFailureException can phai co: <br />
    ///     1. Tai cac class POJO phai them field version cho entity do, dong thoi them annotation @Version mac dinh cua Spring <br />
    ///     2. Tai tang Services, cac method nao mang tinh nhay cam nhu payment hoac update du lieu thi can phai them annotation @Transactional cho method do.
    /// <br /><br />
    /// Xem Demo tai class POJO Pokemon
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleOptimisticLockFailure(OptimisticLockingFailureException e) {
        return ApiResponse.badRequest("Data was modified by another request");
    }

    ///  XU ly loi kh nguoi dung co truy cap route va truyen tham so khong dung voi format ma API duoc dinh nghia trong
    /// controller. Global se tu dong xu ly cho truong hop nay ma khong can phai goi thu cong.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        System.out.println(e.getMessage());
        return ApiResponse.badRequest("Api not found");
    }

    /// ExceptionHandler custom xu ly query string URL khong dung tham so format quy dinh cua API.
    @ExceptionHandler(IllegalParamException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleIllegalParam(IllegalParamException e) {
        return ApiResponse.badRequest("Param invalid.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleUsernameNotFound(BadCredentialsException e) {
        log.error("BadCredentialsException.class ", e.getMessage());
        return ApiResponse.badRequest("Your account is invalid.");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleInvalidDataAccessApiUsage(InvalidDataAccessApiUsageException e) {
        return ApiResponse.badRequest("Data access invalid");
    }
}
