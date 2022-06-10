package com.co.ias.observatory.infrastructure.adapters.output;

import com.co.ias.observatory.birds.application.domain.Bird;
import com.co.ias.observatory.birds.application.domain.valueObjs.BirdId;
import com.co.ias.observatory.birds.application.ports.output.BirdRepository;
import com.co.ias.observatory.infrastructure.logSystem.LogStatus;
import com.co.ias.observatory.infrastructure.logSystem.Logger;
import com.co.ias.observatory.infrastructure.models.BirdDAO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class PostgresSqlBirdRepository implements BirdRepository {

    private final DataSource dataSource;

    public PostgresSqlBirdRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void store(Bird bird) {
        String sql = "INSERT INTO birds (common_name, scientific_name, zone_name, confirmed_quantity) values (?, ?, ?, ?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, bird.getCommonName().getValue());
            preparedStatement.setString(2, bird.getScientificName().getValue());
            preparedStatement.setString(3, bird.getZoneName().getValue());
            preparedStatement.setInt(4, bird.getConfirmedQuantity().getValue());

            preparedStatement.execute();

        } catch (SQLException exception) {
            System.out.println("Error INSERT INTO birds DB: "  + exception.getMessage());
            throw new RuntimeException("Error insert into birds table", exception);
        }
    }

    @Override
    public Optional<Bird> get(BirdId birdId) {
        String sql = "SELECT * FROM birds WHERE id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, birdId.getValue());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                BirdDAO birdDAO = BirdDAO.fromResultSet(resultSet);
                Bird bird = birdDAO.toDomain();
                return Optional.of(bird);
            } else {
                return Optional.empty();
            }

        } catch (SQLException exception) {
            System.out.println("Error SELECT FROM birds DB by id: "  + exception.getMessage());
            throw new RuntimeException("Error select bird by id", exception);
        }
    }

    @Override
    public void update(Bird bird) {
        String sql = "UPDATE birds SET common_name = ?, scientific_name = ?, zone_name = ?, confirmed_quantity = ? WHERE id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bird.getCommonName().getValue());
            preparedStatement.setString(2, bird.getScientificName().getValue());
            preparedStatement.setString(3, bird.getZoneName().getValue());
            preparedStatement.setInt(4, bird.getConfirmedQuantity().getValue());

            preparedStatement.setLong(5, bird.getId().getValue());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            Logger.log(LogStatus.ERROR, "Error update bird " + exception.getMessage());
            throw new RuntimeException("Error update bird", exception);
        }
    }

    @Override
    public boolean delete(BirdId birdId) {
        String sql = "DELETE FROM birds WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, birdId.getValue());

            return preparedStatement.execute();
        } catch (SQLException exception) {
            Logger.log(LogStatus.ERROR, "Error delete bird " + exception.getMessage());
            throw  new RuntimeException("Error delete bird", exception);
        }
    }
}
