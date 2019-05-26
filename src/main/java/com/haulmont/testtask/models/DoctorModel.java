package com.haulmont.testtask.models;

import com.haulmont.testtask.dbconnection.ConnectorDB;
import com.haulmont.testtask.entities.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorModel implements Model<Doctor>{
    @Override
    public ArrayList<Doctor> getAll() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        try (ConnectorDB connector = new ConnectorDB()){
            Connection conn = connector.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Doctor");
            while (resultSet.next()) {
                Doctor currentDoctor = new Doctor(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
                doctors.add(currentDoctor);
            }
        } catch (SQLException e) {
            return null;
        }
        return doctors;
    }

    @Override
    public Doctor getOne(long id) {
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Doctor " +
                    "WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
                return doctor;
            } else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean addOne(Doctor doctor) {
        int result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Doctor" +
                    "(doc_name, doc_secname, doc_otch, doc_spec)" +
                    "VALUES" +
                    "(?, ?, ?, ?)");
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSecname());
            statement.setString(3, doctor.getOtch());
            statement.setString(4, doctor.getSpec());
            result = statement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateOne(Doctor doctor) {
        int result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE Doctor " +
                    "SET doc_name = ?, doc_secname = ?, doc_otch = ?, doc_spec = ?" +
                    "WHERE id = ?");
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSecname());
            statement.setString(3, doctor.getOtch());
            statement.setString(4, doctor.getSpec());
            statement.setLong(5, doctor.getId());
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
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Doctor " +
                    "WHERE id = ?");
            statement.setLong(1, id);
            result = statement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Doctor> getFiltered(String descriptionFilter, String entityFilter1, String entityFilter2) {
        throw new NotImplementedException();
    }

    @Override
    public long getId(Doctor entity) {
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT id FROM Doctor " +
                    "WHERE (doc_name = ?) AND " +
                    "(doc_secname = ?) AND " +
                    "(doc_otch = ?) ");
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
    public Doctor searchByFields(Doctor entity) {
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Doctor " +
                    "WHERE (id LIKE ?) AND " +
                    "(doc_name LIKE ?) AND " +
                    "(doc_secname LIKE ?) AND " +
                    "(doc_otch LIKE ?) AND " +
                    "(doc_spec LIKE ?) ");
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
            if (entity.getSpec() == "") {
                statement.setString(5, "%");
            } else {
                statement.setString(5, entity.getSpec());
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Doctor(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
            } else
                return null;
        } catch (SQLException e) {
            return null;
        }
    }

    // Получение статистики по докторам
    public ArrayList<DoctorPrescrInfo> getDocPrescInfo() {
        ArrayList<DoctorPrescrInfo> info = new ArrayList<>();
        DoctorPrescrInfo infoRow;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT doc_name, doc_secname, doc_otch, count(doc_id) \n" +
                    "FROM Doctor, Prescription\n" +
                    "WHERE doc_id = Doctor.id\n" +
                    "GROUP BY Doctor.id ");
            while (resultSet.next()) {
                infoRow = new DoctorPrescrInfo(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
                info.add(infoRow);
            }
            return info;
        } catch (SQLException e) {
            return null;
        }
    }
}
