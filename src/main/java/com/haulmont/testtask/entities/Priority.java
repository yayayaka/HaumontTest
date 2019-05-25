package com.haulmont.testtask.entities;

import com.google.common.base.Objects;

public class Priority implements Entity {
    long id;
    PrioritySelect priorityName;

    public Priority(long id, PrioritySelect priorityName) {
        this.id = id;
        this.priorityName = priorityName;
    }

    public Priority() {
        id = -1;
        priorityName = PrioritySelect.Normal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PrioritySelect getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(PrioritySelect priorityName) {
        this.priorityName = priorityName;
    }

    @Override
    public String toString() {
        return getPriorityName().toString();
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(priorityName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Priority other = (Priority) obj;
        return Objects.equal(priorityName, other.getPriorityName());
    }
}
