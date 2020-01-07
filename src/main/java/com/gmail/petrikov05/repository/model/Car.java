package com.gmail.petrikov05.repository.model;

public class Car {

    private String name;
    private CarModelEnum carModel;
    private int engineCapacity;

    public Car(String name, CarModelEnum carModel, int engineCapacity) {
        this.name = name;
        this.carModel = carModel;
        this.engineCapacity = engineCapacity;
    }

    public String getName() {
        return name;
    }

    public CarModelEnum getCarModel() {
        return carModel;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

}
