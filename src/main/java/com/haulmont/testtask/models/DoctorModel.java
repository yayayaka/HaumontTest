package com.haulmont.testtask.models;

import com.haulmont.testtask.dbconnection.ConnectorDB;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.DoctorPrescrInfo;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Priority;
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
    public List<Doctor> getFiltered(String descriptionFilter, Priority entityFilter1, Patient entityFilter2) {
        throw new NotImplementedException();
    }

    @Override
    public long getId(Doctor entity) {
        throw new NotImplementedException();
    }

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
