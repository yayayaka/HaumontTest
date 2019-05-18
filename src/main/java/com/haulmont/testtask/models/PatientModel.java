package com.haulmont.testtask.models;

import com.haulmont.testtask.dbconnection.ConnectorDB;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientModel implements Model<Patient> {
    @Override
    public ArrayList<Patient> getAll() {
        ArrayList<Patient> patients = new ArrayList<>();
        try (ConnectorDB connector = new ConnectorDB()){
            Connection conn = connector.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Patient");
            while (resultSet.next()) {
                Patient currentPatient = new Patient(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5));
                patients.add(currentPatient);
            }
        } catch (SQLException e) {
            return null;
        }
        return patients;
    }

    @Override
    public Patient getOne(long id) {
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Patient " +
                    "WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Patient patient = new Patient(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5));
                return patient;
            } else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean addOne(Patient patient) {
        int result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Patient" +
                    "(pat_name, pat_secname, pat_otch, tel_no)" +
                    "VALUES" +
                    "(?, ?, ?, ?)");
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getSecname());
            statement.setString(3, patient.getOtch());
            statement.setInt(4, patient.getPhoneNo());
            result = statement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateOne(Patient patient) {
        int result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE Patient " +
                    "SET pat_name = ?, pat_secname = ?, pat_otch = ?, tel_no = ?" +
                    "WHERE id = ?");
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getSecname());
            statement.setString(3, patient.getOtch());
            statement.setInt(4, patient.getPhoneNo());
            statement.setLong(5, patient.getId());
            result = statement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteOne(long id) {
        int result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Patient " +
                    "WHERE id = ?");
            statement.setLong(1, id);
            result = statement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Patient> getFiltered(String descriptionFilter, Priority entityFilter1, Patient entityFilter2) {
        throw new NotImplementedException();
    }

    @Override
    public long getId(Patient entity) {
        throw new NotImplementedException();
    }
}
