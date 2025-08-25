package com.example.danew_spring.news;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NewsController {

    @Autowired
    private NewsService newsService;
}
