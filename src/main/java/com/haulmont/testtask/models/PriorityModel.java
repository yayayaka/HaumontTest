package com.haulmont.testtask.models;

import com.haulmont.testtask.dbconnection.ConnectorDB;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Priority;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PriorityModel implements Model<Priority> {
    @Override
    public long getId(Priority priority) {
        long result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT id FROM Priority " +
                    "WHERE prior_name = ?");
            statement.setString(1, priority.name());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getLong(1);
                return result;
            } else
                return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public Priority getOne(long id) {
        String priorityName;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT prior_name FROM Priority " +
                    "WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                priorityName = resultSet.getString(1);
                return Priority.valueOf(priorityName);
            } else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Priority> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public boolean addOne(Priority entity) {
        throw new NotImplementedException();
    }

    @Override
    public boolean updateOne(Priority entity) {
        throw new NotImplementedException();
    }

    @Override
    public boolean deleteOne(long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Priority> getFiltered(String descriptionFilter, Priority priorityFilter, Patient patientFilter) {
        throw new NotImplementedException();
    }
}
