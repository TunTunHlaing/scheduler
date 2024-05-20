package org.example.dynamicschedulerredis.repo;

import org.example.dynamicschedulerredis.model.OutputScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Repository
public class RedisRepo {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "Task";

    private String convertToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void save(OutputScheduleTask task) {
        try {
            String key = convertToString(task.getTime().withNano(0));
            redisTemplate.opsForHash().put(KEY, key, task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void save(String mainKey, T task, String key) {
        try {
            redisTemplate.opsForHash().put(mainKey, key, task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object findById(String key, String id) {
        try {
            return  redisTemplate.opsForHash().get(key, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<Object, Object> findAll(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(String key, String id) {
            redisTemplate.opsForHash().delete(key, id);
    }
}
