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

    public List<K> getAll() {
        return model.getAll();
    }

    public boolean addOne(K entity) {
        return model.addOne(entity);
    }

    public boolean updateOne(K entity) {
        return model.updateOne(entity);
    }

    public boolean deleteOne(long id) {
        return model.deleteOne(id);
    }

    public List<K> getFiltered(String descriptionFilter,
                        Priority priorityFilter,
                        Patient patientFilter) {
        return model.getFiltered(descriptionFilter,
                priorityFilter,
                patientFilter);
    }

    public long getId(K entity) {
        return model.getId(entity);
    }

    public K getPriorityName(long id) {
        return (K)model.getOne(id);
    }
}
