package org.yandex.praktikum.taskmanager.task;

import java.util.Objects;

public class Task {
    protected String name;
    protected String desctiption;
    protected int id;
    protected TaskStatus status;

    public Task(String name, String desctiption, int id, TaskStatus status) {
        this.name = name;
        this.desctiption = desctiption;
        this.id = id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && name.equals(task.name) && Objects.equals(desctiption, task.desctiption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desctiption, id);
    }
}

