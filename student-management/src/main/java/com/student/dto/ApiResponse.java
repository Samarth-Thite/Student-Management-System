package com.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private int statusCode;
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(), 200);
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Operation completed successfully");
    }
    
    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(), statusCode);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return error(message, 400);
    }
}
