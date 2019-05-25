package com.haulmont.testtask.models;

import com.haulmont.testtask.dbconnection.ConnectorDB;
import com.haulmont.testtask.entities.DoctorPrescrInfo;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.entities.PrioritySelect;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PriorityModel implements Model<Priority> {
    @Override
    public long getId(Priority priority) {
        long result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT id FROM Prescr_priority " +
                    "WHERE prior_name = ?");
            statement.setString(1, priority.getPriorityName().name());
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
            PreparedStatement statement = conn.prepareStatement("SELECT prior_name FROM Prescr_priority " +
                    "WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                priorityName = resultSet.getString(1);
                return new Priority(id, PrioritySelect.valueOf(priorityName));
            } else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Priority searchByFields(Priority entity) {
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT id FROM Prescr_priority " +
                    "WHERE (prior_name LIKE ?)");
            if (entity.getPriorityName() == null) {
                statement.setString(1, "%");
            } else {
                statement.setString(1, entity.getPriorityName().name());
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Priority(resultSet.getLong(1),
                        entity.getPriorityName());
            } else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ArrayList<DoctorPrescrInfo> getDocPrescInfo() {
        throw new NotImplementedException();
    }

    @Override
    public List<Priority> getAll() {
        ArrayList<Priority> priorities = new ArrayList<>();
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Prescr_priority ");
            while (resultSet.next()) {
                priorities.add(new Priority(resultSet.getLong(1),
                        PrioritySelect.valueOf(resultSet.getString(2))));
            }
            return priorities;
        } catch (SQLException e) {
            return null;
        }
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
    public List<Priority> getFiltered(String descriptionFilter, String priorityFilter, String patientFilter) {
        throw new NotImplementedException();
    }
}
