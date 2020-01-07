package com.gmail.petrikov05.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.repository.model.Car;

public interface CarRepository {

    void createTable(Connection connection, String commandCreateTable) throws SQLException;

    void add(Connection connection, Car car) throws SQLException;

    List<Car> findWithEngineCapacity(Connection connection, int RandomEngineCapacity) throws SQLException;

    int deleteMinEngineCapacity(Connection connection) throws SQLException;

    int findCountWithEngineCapacity(Connection connection, int randomEngineCapacity) throws SQLException;

    void replaceWithEngineCapacity(Connection connection, int randomEngineCapacity) throws SQLException;

}
