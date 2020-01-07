package com.gmail.petrikov05.service;

import java.util.List;

import com.gmail.petrikov05.repository.model.Car;

public interface CarService {

    void createTable(String commandCreateTable);

    void addList(List<Car> carList);

    List<Car> findWithEngineCapacity(int randomEngineCapacity);

    int deleteMinEngineCapacity();

    int findCountWithEngineCapacity(int randomEngineCapacity);

    List<Car> replaceAndShowWithEngineCapacity(int randomEngineCapacity);

}
