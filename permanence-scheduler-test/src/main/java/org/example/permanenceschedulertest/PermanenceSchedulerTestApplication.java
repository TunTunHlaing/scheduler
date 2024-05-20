package org.example.permanenceschedulertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@SpringBootApplication
public class PermanenceSchedulerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermanenceSchedulerTestApplication.class, args);
    }


    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public ScheduledTaskRegistrar scheduledTaskRegistrar(TaskScheduler taskScheduler) {
        ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
        return scheduledTaskRegistrar;
    }
}
