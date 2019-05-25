package com.haulmont.testtask.models;

import com.haulmont.testtask.entities.*;

import java.util.ArrayList;
import java.util.List;

public interface Model<T extends Entity> {
    List<T> getAll();

    boolean addOne(T entity);

    boolean updateOne(T entity);

    boolean deleteOne(long id);

    List<T> getFiltered(String descriptionFilter,
                                        String priorityFilter,
                                        String patientFilter);

    long getId(T entity);

    T getOne(long id);

    T searchByFields(T entity);

    ArrayList<DoctorPrescrInfo> getDocPrescInfo();
}