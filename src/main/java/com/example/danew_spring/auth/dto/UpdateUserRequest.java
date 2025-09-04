package com.example.danew_spring.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserRequest {
    private String name;   // null이면 업데이트 안 함
    private Integer age;   // null이면 업데이트 안 함
    private String gender; // null이면 업데이트 안 함 ("남성" / "여성")
}
