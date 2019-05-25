package com.haulmont.testtask.models;

import com.haulmont.testtask.dbconnection.ConnectorDB;
import com.haulmont.testtask.entities.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PrescriptionModel implements Model<Prescription> {
    @Override
    public ArrayList<Prescription> getAll() {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        PatientModel patientModel = new PatientModel();
        DoctorModel doctorModel = new DoctorModel();
        PriorityModel priorityModel = new PriorityModel();
        Patient currentPatient;
        Doctor currentDoctor;
        Priority prioritySelect;
        try (ConnectorDB connector = new ConnectorDB()){
            Connection conn = connector.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Prescription");
            while (resultSet.next()) {
                currentDoctor = doctorModel.getOne(resultSet.getLong(4));
                currentPatient = patientModel.getOne(resultSet.getLong(3));
                prioritySelect = priorityModel.getOne(resultSet.getLong(7));
                Prescription currentPrescription = new Prescription(resultSet.getLong(1),
                        resultSet.getString(2),
                        currentPatient,
                        currentDoctor,
                        resultSet.getObject(5, LocalDate.class),
                        resultSet.getObject(6, LocalDate.class),
                        prioritySelect);
                prescriptions.add(currentPrescription);
            }
        } catch (SQLException e) {
            return null;
        }
        return prescriptions;
    }

    @Override
    public boolean addOne(Prescription prescription) {
        int result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PriorityModel priorityModel = new PriorityModel();
            long priorityId = priorityModel.getId(prescription.getPriority());
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Prescription" +
                    "(description, patient_id, doc_id, creation_date, expire_date, priority)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?, ?)");
            statement.setString(1, prescription.getDescription());
            statement.setLong(2, prescription.getPatient().getId());
            statement.setLong(3, prescription.getDoctor().getId());
            statement.setObject(4, prescription.getCreateDate());
            statement.setObject(5, prescription.getExpireDate());
            statement.setLong(6, priorityId);
            result = statement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateOne(Prescription prescription) {
        int result;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE Prescription " +
                    "SET description = ?, patient_id = ?, doc_id = ?, " +
                    "creation_date = ?, expire_date = ?, priority = ?" +
                    "WHERE id = ?");
            PriorityModel priorityModel = new PriorityModel();
            long priorityId = priorityModel.getId(prescription.getPriority());
            statement.setString(1, prescription.getDescription());
            statement.setLong(2, prescription.getPatient().getId());
            statement.setLong(3, prescription.getDoctor().getId());
            statement.setObject(4, prescription.getCreateDate());
            statement.setObject(5, prescription.getExpireDate());
            statement.setLong(6, priorityId);
            statement.setLong(7, prescription.getId());
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
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Prescription " +
                    "WHERE id = ?");
            statement.setLong(1, id);
            result = statement.executeUpdate();
            return result > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public ArrayList<Prescription> getFiltered(String descriptionFilter,
                                               String priorityFilter,
                                              String patientFilter) {
        ArrayList<Prescription> filteredPrescriptions = new ArrayList<>();
        Prescription currentPrescription;
        DoctorModel doctorModel = new DoctorModel();
        PatientModel patientModel = new PatientModel();
        PriorityModel priorityModel = new PriorityModel();
        long priorityId;
        Patient currentPatient;
        Doctor currentDoctor;
        Priority currentPriority;
        try(ConnectorDB connector = new ConnectorDB()) {
            Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT Prescription.id, " +
                    "description, patient_id, doc_id, creation_date, expire_date, priority " +
                    "FROM Prescription, Prescr_priority, Patient " +
                    "WHERE (Prescription.patient_id = Patient.id) AND " +
                    "(Prescription.priority = Prescr_priority.id) AND " +
                    "(description LIKE ?) AND " +
                    "(prior_name LIKE ?) AND " +
                    "((Patient.pat_secname LIKE ?) OR " +
                    "(Patient.pat_name LIKE ?) OR " +
                    "(Patient.pat_otch LIKE ?)) ");
            statement.setString(1, descriptionFilter == null ? "%" : "%" + descriptionFilter + "%");
            statement.setString(2, priorityFilter == null ? "%" : "%" + priorityFilter + "%");
            statement.setString(3, patientFilter == null ? "%" : "%" + patientFilter + "%");
            statement.setString(4, patientFilter == null ? "%" : "%" + patientFilter + "%");
            statement.setString(5, patientFilter == null ? "%" : "%" + patientFilter + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                currentPatient = patientModel.getOne(resultSet.getLong(3));
                currentDoctor = doctorModel.getOne(resultSet.getLong(4));
                currentPriority = priorityModel.getOne(resultSet.getLong(7));
                currentPrescription = new Prescription(resultSet.getLong(1),
                        resultSet.getString(2),
                        currentPatient,
                        currentDoctor,
                        resultSet.getObject(5, LocalDate.class),
                        resultSet.getObject(6, LocalDate.class),
                        currentPriority);
                filteredPrescriptions.add(currentPrescription);
            }
            return filteredPrescriptions;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public long getId(Prescription entity) {
        throw new NotImplementedException();
    }

    @Override
    public Prescription getOne(long id) {
        throw new NotImplementedException();
    }

    @Override
    public Prescription searchByFields(Prescription entity) {
        throw new NotImplementedException();
    }

    @Override
    public ArrayList<DoctorPrescrInfo> getDocPrescInfo() {
        throw new NotImplementedException();
    }
}
