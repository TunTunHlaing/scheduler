package org.example.dynamicschedulerredis.service;

import org.example.dynamicschedulerredis.model.Township;
import org.example.dynamicschedulerredis.repo.RedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownShipService {

    @Autowired
    private RedisRepo repo;

    private String KEY = "Township";

    public Township addToTownShip (String townshipName) {
        Integer size = getAllTownShip().size();
        var township = new Township(size, townshipName);
        repo.save(KEY, township, size.toString() );
        return township;
    }

    public Township getById (Integer townshipId) {
        return (Township) repo.findById(KEY, townshipId.toString());
    }

    public List<Township> getAllTownShip () {
        return repo.findAll(KEY).values().stream().map(a -> (Township) a).toList();
    }

    public String deleteById(Integer townshipId) {
         repo.delete(KEY, townshipId.toString());
         return "Successfully Deleted";
    }

}
