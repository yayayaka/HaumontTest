package com.haulmont.testtask.models;

import com.haulmont.testtask.entities.Entity;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Priority;

import java.util.List;

public interface Model<T extends Entity> {
    List<T> getAll();

    boolean addOne(T entity);

    boolean updateOne(T entity);

    boolean deleteOne(long id);

    List<T> getFiltered(String descriptionFilter,
                                        Priority priorityFilter,
                                        Patient patientFilter);

    long getId(T entity);

    T getOne(long id);
}