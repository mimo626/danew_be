package com.example.danew_spring.manage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnnounceRequest {
    private String title;
    private String content;
}