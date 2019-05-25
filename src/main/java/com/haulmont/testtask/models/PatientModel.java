package com.haulmont.testtask.models;

import com.haulmont.testtask.dbconnection.ConnectorDB;
import com.haulmont.testtask.entities.DoctorPrescrInfo;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.entities.PrioritySelect;
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
                        resultSet.getLong(5));
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
                        resultSet.getLong(5));
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
            statement.setLong(4, patient.getPhoneNo());
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
            statement.setLong(4, patient.getPhoneNo());
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
    public List<Patient> getFiltered(String descriptionFilter, String entityFilter1, String entityFilter2) {
        throw new NotImplementedException();
    }

    @Override
    public long getId(Patient entity) {
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT id FROM Patient " +
                    "WHERE (pat_name = ?) AND " +
                    "(pat_secname = ?) AND " +
                    "(pat_otch = ?) ");
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSecname());
            statement.setString(3, entity.getOtch());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else
                return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public Patient searchByFields(Patient entity) {
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Patient " +
                    "WHERE (id LIKE ?) AND " +
                    "(pat_name LIKE ?) AND " +
                    "(pat_secname LIKE ?) AND " +
                    "(pat_otch LIKE ?) AND " +
                    "(tel_no LIKE ?) ");
            if (entity.getId() == -1) {
                statement.setString(1, "%");
            } else {
                statement.setLong(1, entity.getId());
            }
            if (entity.getName() == "") {
                statement.setString(2, "%");
            } else {
                statement.setString(2, entity.getName());
            }
            if (entity.getSecname() == "") {
                statement.setString(3, "%");
            } else {
                statement.setString(3, entity.getSecname());
            }
            if (entity.getOtch() == "") {
                statement.setString(4, "%");
            } else {
                statement.setString(4, entity.getOtch());
            }
            if (entity.getPhoneNo() == -1) {
                statement.setString(5, "%");
            } else {
                statement.setLong(5, entity.getPhoneNo());
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Patient(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5));
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
}
