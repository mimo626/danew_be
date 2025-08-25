package com.example.danew_spring.alarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {
    @Autowired
    private AlarmRepository alarmRepository;
}
