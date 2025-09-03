package com.example.danew_spring;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter/setter 자동 생성
@NoArgsConstructor //Jackson 기본 생성자
//위 두개가 없으면 JSON 직렬화 불가

public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
