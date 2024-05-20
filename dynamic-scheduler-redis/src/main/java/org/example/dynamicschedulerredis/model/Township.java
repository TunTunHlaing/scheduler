package org.example.dynamicschedulerredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Township implements Serializable {

    private Integer id;
    private String name;

}
