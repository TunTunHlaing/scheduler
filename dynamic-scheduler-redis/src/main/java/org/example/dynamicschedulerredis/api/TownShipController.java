package org.example.dynamicschedulerredis.api;

import jakarta.annotation.PostConstruct;
import org.example.dynamicschedulerredis.model.Township;
import org.example.dynamicschedulerredis.service.TownShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("township")
public class TownShipController {

    @Autowired
    private TownShipService service;

    @PostMapping
    public Township saveTownShip (@RequestBody String townshipName) {
        return service.addToTownShip(townshipName);
    }

    @GetMapping("{id}")
    public Township findById (@PathVariable(name = "id") Integer id) {
        return service.getById(id);
    }

    @DeleteMapping("{id}")
    public String deleteById (@PathVariable(name = "id") Integer id) {
        return service.deleteById(id);
    }

    @GetMapping("all")
    public List<Township> findAllTownship () {
        return service.getAllTownShip();
    }
}
