package com.gmail.petrikov05.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.gmail.petrikov05.repository.model.Car;
import com.gmail.petrikov05.repository.model.CarModelEnum;
import com.gmail.petrikov05.service.CarService;
import com.gmail.petrikov05.service.HomeWorkService;
import com.gmail.petrikov05.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CAR_CAR_MODEL;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CAR_ENGINE_CAPACITY;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CAR_ID;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CAR_NAME;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.NAME_TABLE_CAR;

public class HomeWorkServiceImpl implements HomeWorkService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void runTaskFirst() {
        String fileName = "sql.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                logger.trace(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("File not found!");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Reading file failed!");
        }
        logger.trace("Task one finished");
    }

    @Override
    public void runTaskSecond() {

        String nameTable = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_CAR + "( "
                + CAR_ID + " INT PRIMARY KEY AUTO_INCREMENT, "
                + CAR_NAME + " varchar(50) not null, "
                + CAR_CAR_MODEL + " varchar(50) not null, "
                + CAR_ENGINE_CAPACITY + " int not null);";
        CarService carService = new CarServiceImpl();
        carService.createTable(nameTable);

        int numberOfCar = 10;
        int minValueRangeCapacity = 1;
        int maxValueRangeCapacity = 10;
        List<Car> carList = getCarList(numberOfCar, minValueRangeCapacity, maxValueRangeCapacity);
        carService.addList(carList);

        int randomEngineCapacity = RandomUtil.getRandomIntRange(minValueRangeCapacity, maxValueRangeCapacity);
        carList = carService.findWithEngineCapacity(randomEngineCapacity);
        if (carList.size() != 0) {
            logger.trace("Collection with engineCapacity = " + randomEngineCapacity + ". Start:");
            carList.forEach(x -> logger.trace(x.getName() + " " + x.getCarModel() + " " + x.getEngineCapacity()));
            logger.trace("Collection finish.");
        } else {
            logger.trace("List is empty. Lines with engineCapacity = " + randomEngineCapacity + ": not found.");
        }

        int resultDelete = carService.deleteMinEngineCapacity();
        logger.trace("Delete lines with MIN EngineCapacity: " + resultDelete);

        randomEngineCapacity = RandomUtil.getRandomIntRange(minValueRangeCapacity, maxValueRangeCapacity);
        int countWithEngineCapacity = carService.findCountWithEngineCapacity(randomEngineCapacity);
        logger.trace("Number of lines with engineСapacity (" + randomEngineCapacity + ") : " + countWithEngineCapacity);

        randomEngineCapacity = RandomUtil.getRandomIntRange(minValueRangeCapacity, maxValueRangeCapacity);
        carList = carService.replaceAndShowWithEngineCapacity(randomEngineCapacity);
        if (carList.size() != 0) {
            logger.trace("Collection with engineCapacity = " + randomEngineCapacity + ". Start:");
            carList.forEach(x -> logger.trace(x.getName() + " " + x.getCarModel() + " " + x.getEngineCapacity()));
            logger.trace("Collection finish.");
        } else {
            logger.trace("Line with engineCapacity = " + randomEngineCapacity + " not found. Title change in line did not happen");
        }
    }

    private List<Car> getCarList(int numberOfCar, int minValueRangeCapacity, int maxValueRangeCapacity) {
        List<Car> carList = new ArrayList<>(numberOfCar);
        for (int i = 0; i < numberOfCar; i++) {
            carList.add(getCar(minValueRangeCapacity, maxValueRangeCapacity));
        }
        return carList;
    }

    private Car getCar(int minValueRangeCapacity, int maxValueRangeCapacity) {
        String name = "Name_" + RandomUtil.getRandomIntRange(minValueRangeCapacity, maxValueRangeCapacity);
        CarModelEnum carModel = getCarModel();
        int engineCapacity = RandomUtil.getRandomIntRange(minValueRangeCapacity, maxValueRangeCapacity);
        return new Car(name, carModel, engineCapacity);
    }

    private CarModelEnum getCarModel() {
        switch (RandomUtil.getRandomIntRange(1, 3)) {
            case 1: {
                return CarModelEnum.BMW;
            }
            case 2: {
                return CarModelEnum.MERCEDES;
            }
            default: {
                return CarModelEnum.AUDI;
            }
        }
    }

}
