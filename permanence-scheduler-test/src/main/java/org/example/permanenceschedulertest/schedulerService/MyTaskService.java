package org.example.permanenceschedulertest.schedulerService;

import org.example.permanenceschedulertest.model.entity.SchedulerTask;
import org.example.permanenceschedulertest.repo.ScheduledTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyTaskService {

    @Autowired
    private ScheduledTaskRepository repo;
    public void executeTask(SchedulerTask task) {
        System.out.println("Executing task: " + task.getId());
        repo.deleteById(task.getId());
    }
}
