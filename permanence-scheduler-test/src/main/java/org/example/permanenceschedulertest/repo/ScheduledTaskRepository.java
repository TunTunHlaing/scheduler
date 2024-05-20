package org.example.permanenceschedulertest.repo;

import org.example.permanenceschedulertest.model.entity.SchedulerTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledTaskRepository extends JpaRepository<SchedulerTask, Long> {
}
