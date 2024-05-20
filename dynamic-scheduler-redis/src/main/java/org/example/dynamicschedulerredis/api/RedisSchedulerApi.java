package org.example.dynamicschedulerredis.api;

import org.example.dynamicschedulerredis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisSchedulerApi {

    @Autowired
    private RedisService service;

    @GetMapping()
    public String addSchedule (Long min) {
       return service.addSchedule(min);
    }
}
