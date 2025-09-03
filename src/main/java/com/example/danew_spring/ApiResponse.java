package com.example.danew_spring;

public class ApiResponse<T> {
    private String status; // "success" or "error"
    private String message; // 필요시 오류 메시지
    private T data; // 실제 반환할 데이터

    // 생성자
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    // getters/setters 생략
}
