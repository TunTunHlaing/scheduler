package org.example.dynamicschedulerredis;

import jakarta.annotation.PostConstruct;
import org.example.dynamicschedulerredis.model.Township;
import org.example.dynamicschedulerredis.repo.RedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.stream.Task;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DynamicSchedulerRedisApplication {

    @Autowired
    private RedisRepo repo;

    public static void main(String[] args) {
        SpringApplication.run(DynamicSchedulerRedisApplication.class, args);
    }

    @Bean
    public TaskScheduler scheduler() {
        return new ConcurrentTaskScheduler();
    }

    @PostConstruct
    public void createTownShip () {
        String key = "Township";
       if (repo.findAll(key).isEmpty()) {
           var t1 = new Township(1, "Hlaing");
           var t2 = new Township(2, "KaMarYut");
           var t3 = new Township(3 , "Hlaing Tharyar");
           var list = List.of(t1, t2, t3);
           list.forEach(a -> repo.save(key, a, a.getId().toString()));
           repo.findAll(key).values().forEach(System.out::println);
       }
    }
}
