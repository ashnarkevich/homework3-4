package com.gmail.petrikov05.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.gmail.petrikov05.repository.CarRepository;
import com.gmail.petrikov05.repository.ConnectionRepository;
import com.gmail.petrikov05.repository.impl.CarRepositoryImpl;
import com.gmail.petrikov05.repository.impl.ConnectionRepositoryImpl;
import com.gmail.petrikov05.repository.model.Car;
import com.gmail.petrikov05.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarServiceImpl implements CarService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();
    private CarRepository carRepository = new CarRepositoryImpl();

    @Override
    public void createTable(String commandCreateTable) {
        try (Connection connection = connectionRepository.getConnection()) {
            carRepository.createTable(connection, commandCreateTable);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Create table failed!");
        }
    }

    @Override
    public void addList(List<Car> carList) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                addListInCar(connection, carList);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Write car failed!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Write car failed!");
        }
    }

    @Override
    public List<Car> findWithEngineCapacity(int randomEngineCapacity) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Car> carList = carRepository.findWithEngineCapacity(connection, randomEngineCapacity);
                connection.commit();
                return carList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Find car failed!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public int deleteMinEngineCapacity() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int resultDelete = carRepository.deleteMinEngineCapacity(connection);
                connection.commit();
                return resultDelete;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Deleted car failed!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int findCountWithEngineCapacity(int randomEngineCapacity) {
        try (Connection connection = connectionRepository.getConnection()) {
            return carRepository.findCountWithEngineCapacity(connection, randomEngineCapacity);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public List<Car> replaceAndShowWithEngineCapacity(int randomEngineCapacity) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                carRepository.replaceWithEngineCapacity(connection, randomEngineCapacity);
                List<Car> carList = carRepository.findWithEngineCapacity(connection, randomEngineCapacity);
                connection.commit();
                return carList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException("Replace title car failed!");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private void addListInCar(Connection connection, List<Car> carList) throws SQLException {
        try {
            for (Car car : carList) {
                carRepository.add(connection, car);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Write car failed!");
        }
    }

}
