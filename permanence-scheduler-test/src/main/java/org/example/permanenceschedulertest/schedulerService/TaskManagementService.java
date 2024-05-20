package org.example.permanenceschedulertest.schedulerService;

import jakarta.transaction.Transactional;
import org.example.permanenceschedulertest.SchedulerConfig;
import org.example.permanenceschedulertest.model.entity.SchedulerTask;
import org.example.permanenceschedulertest.repo.ScheduledTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskManagementService {

    @Autowired
    private  SchedulerConfig schedulerConfig;
    @Autowired
    private  ScheduledTaskRepository scheduledTaskRepository;
    @Transactional
    public void addTask(SchedulerTask task) {
        scheduledTaskRepository.save(task);
        schedulerConfig.scheduleTask(task, task.getTime());
    }

    @Transactional
    public void removeTask(Long taskId) {
        SchedulerTask task = scheduledTaskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        scheduledTaskRepository.delete(task);
        schedulerConfig.rescheduleTasks();
    }
}
