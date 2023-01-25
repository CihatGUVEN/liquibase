package com.cihatguven.liquibase.controller;

import com.cihatguven.liquibase.model.City;
import com.cihatguven.liquibase.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @PostConstruct
    private void createIfNotExist(){
        Long count = cityRepository.count();
        if(count == 0){
            City city = new City();
            city.setName("firstCity");
            cityRepository.save(city);
        }
    }

    @GetMapping
    List<City> getAll(){
        return cityRepository.findAll();
    }
}
