package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.petrikov05.repository.CarRepository;
import com.gmail.petrikov05.repository.model.Car;
import com.gmail.petrikov05.repository.model.CarModelEnum;

import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CAR_CAR_MODEL;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CAR_ENGINE_CAPACITY;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CAR_NAME;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.NAME_TABLE_CAR;

public class CarRepositoryImpl implements CarRepository {

    @Override
    public void createTable(Connection connection, String commandCreateTable) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(commandCreateTable);
        }
    }

    @Override
    public void add(Connection connection, Car car) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO car(name, car_model, engine_capacity) VALUES (?,?,?);"
                );
        ) {
            statement.setString(1, car.getName());
            statement.setString(2, String.valueOf(car.getCarModel()));
            statement.setInt(3, car.getEngineCapacity());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }
        }
    }

    @Override
    public List<Car> findWithEngineCapacity(Connection connection, int randomEngineCapacity) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM car WHERE " + CAR_ENGINE_CAPACITY + "=?;");
        ) {
            statement.setString(1, String.valueOf(randomEngineCapacity));
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Car> carList = new ArrayList<>();
                while (resultSet.next()) {
                    carList.add(getCar(resultSet));
                }
                return carList;
            }
        }
    }

    @Override
    public int deleteMinEngineCapacity(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate("DELETE FROM " + NAME_TABLE_CAR +
                    " WHERE " + CAR_ENGINE_CAPACITY + " = (SELECT min(" + CAR_ENGINE_CAPACITY + ") " +
                    "FROM (SELECT " + CAR_ENGINE_CAPACITY + " FROM " + NAME_TABLE_CAR + ") AS car_clone);"
            );
        }
    }

    @Override
    public int findCountWithEngineCapacity(Connection connection, int randomEngineCapacity) throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM " + NAME_TABLE_CAR +
                        "  WHERE " + CAR_ENGINE_CAPACITY + " = " + randomEngineCapacity + ";")
        ) {
            int countWithEngineCapacity = 0;
            if (resultSet.next()) {
                countWithEngineCapacity = resultSet.getInt("count(*)");
            }
            return countWithEngineCapacity;
        }
    }

    @Override
    public void replaceWithEngineCapacity(Connection connection, int randomEngineCapacity) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement("UPDATE " + NAME_TABLE_CAR + " SET "
                        + CAR_NAME + " = 'Good capacity' "
                        + "WHERE " + CAR_ENGINE_CAPACITY + " =?;");
        ) {
            statement.setInt(1, randomEngineCapacity);
            statement.executeUpdate();
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(CAR_NAME);
        String carModel = resultSet.getString(CAR_CAR_MODEL);
        CarModelEnum carModelEnum = CarModelEnum.valueOf(carModel);
        int EngineCapacity = resultSet.getInt(CAR_ENGINE_CAPACITY);
        return new Car(name, carModelEnum, EngineCapacity);
    }

}
