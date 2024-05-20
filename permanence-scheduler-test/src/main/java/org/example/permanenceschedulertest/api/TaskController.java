package org.example.permanenceschedulertest.api;

import org.example.permanenceschedulertest.model.entity.SchedulerTask;
import org.example.permanenceschedulertest.schedulerService.TaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskManagementService taskManagementService;

    @Autowired
    public TaskController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    @GetMapping("{min}")
    public String addTask(@PathVariable("min") Long min) {
        var shcedulerTask = new SchedulerTask();
        shcedulerTask.setNotiId(min);
        shcedulerTask.setTime(LocalDateTime.now().plusMinutes(min));
        taskManagementService.addTask(shcedulerTask);
        return "Success!!!";
    }

    @DeleteMapping("/{id}")
    public void removeTask(@PathVariable Long id) {
        taskManagementService.removeTask(id);
    }
}
