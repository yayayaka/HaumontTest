package com.haulmont.testtask.controllers;

import com.haulmont.testtask.entities.Entity;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.models.Model;

import java.util.List;

public class Controller<T extends Model, K extends Entity> {
    T model;

    public Controller(T model) {
        this.model = model;
    }

    List<K> getAll() {
        return model.getAll();
    }

    boolean addOne(K entity) {
        return model.addOne(entity);
    }

    boolean updateOne(K entity) {
        return model.updateOne(entity);
    }

    boolean deleteOne(long id) {
        return model.deleteOne(id);
    }

    List<K> getFiltered(String descriptionFilter,
                        Priority priorityFilter,
                        Patient patientFilter) {
        return model.getFiltered(descriptionFilter,
                priorityFilter,
                patientFilter);
    }

    long getId(K entity) {
        return model.getId(entity);
    }

    K getPriorityName(long id) {
        return (K)model.getOne(id);
    }
}
