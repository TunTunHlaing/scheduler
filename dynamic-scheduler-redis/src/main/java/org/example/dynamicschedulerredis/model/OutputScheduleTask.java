package org.example.dynamicschedulerredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputScheduleTask implements Serializable{
        private  Long value;
        private LocalDateTime time;

}
