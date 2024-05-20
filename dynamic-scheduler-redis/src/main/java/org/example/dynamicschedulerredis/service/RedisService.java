package org.example.dynamicschedulerredis.service;

import jakarta.annotation.PostConstruct;
import org.example.dynamicschedulerredis.model.OutputScheduleTask;
import org.example.dynamicschedulerredis.repo.RedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Service
public class RedisService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private RedisRepo repo;

    private  String KEY = "Task";


    private Map<LocalDateTime, ScheduledFuture<?>> scheduleMap = new HashMap<>();

    public void scheduleTask(OutputScheduleTask task) {
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(
                () -> executeTask(task),
                task.getTime().atZone(ZoneId.systemDefault()).toInstant());
        scheduleMap.put(task.getTime().withNano(0), scheduledFuture);
    }

    @PostConstruct
    public void initializeScheduledTasks() {
        List<OutputScheduleTask> tasks = repo.findAll(KEY).values().stream()
                .map(a -> (OutputScheduleTask) a)
                .toList();

        tasks.forEach(this::scheduleTask);
        System.out.println("Start With Schedule Size :: " + tasks.size());
    }

    public void deleteFromScheduleList(LocalDateTime id) {
        System.out.println("Already Delete Before:: " + scheduleMap.size());
        repo.delete(KEY, formatDateTime(id.withNano(0)));

        Optional.ofNullable(scheduleMap.get(id)).ifPresent(schedule -> {
            if (!schedule.isCancelled()) {
                schedule.cancel(true);
                scheduleMap.remove(id);
            }
        });
        System.out.println("Already Delete :: " + scheduleMap.size());
    }

    public void executeTask(OutputScheduleTask task) {
        try {
            System.out.println("Hello Task :: " + task);
            deleteFromScheduleList(task.getTime().withNano(0));
            System.out.println("Map Size Execute :: " + scheduleMap.size());
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public String addSchedule(Long min) {
        var value = new OutputScheduleTask(min, LocalDateTime.now().plusMinutes(min));
        repo.save(value);
        scheduleTask(value);
        return "Success";
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}