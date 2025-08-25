package com.example.danew_spring.alarm;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class AlarmController {

    @Autowired
    private AlarmService alarmService;
}
