package org.example.permanenceschedulertest;

import jakarta.annotation.PostConstruct;
import org.example.permanenceschedulertest.model.entity.SchedulerTask;
import org.example.permanenceschedulertest.repo.ScheduledTaskRepository;
import org.example.permanenceschedulertest.schedulerService.MyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private  TaskScheduler taskScheduler;
    @Autowired
    private  MyTaskService myTaskService;
    @Autowired
    private  ScheduledTaskRepository scheduledTaskRepository;
    private List<ScheduledFuture<?>> scheduledTasks = new ArrayList<>();

    @PostConstruct
    public void initializeScheduledTasks() {
        List<SchedulerTask> tasks = scheduledTaskRepository.findAll();
        if (tasks.size() > 0){
            for (SchedulerTask taskForSchedule : tasks) {
                scheduleTask(taskForSchedule, taskForSchedule.getTime());
            }
        }
    }

    public void scheduleTask(SchedulerTask task, LocalDateTime scheduleTime) {
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(
                () -> myTaskService.executeTask(task),
                scheduleTime.atZone(ZoneId.systemDefault()).toInstant());
        scheduledTasks.add(scheduledFuture);
    }

    public void rescheduleTasks() {
        scheduledTasks.forEach(scheduledFuture -> scheduledFuture.cancel(false));
        scheduledTasks.clear();
        initializeScheduledTasks();
    }
}
