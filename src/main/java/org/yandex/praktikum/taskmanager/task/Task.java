package org.yandex.praktikum.taskmanager.task;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;
    protected TaskType type;

    public Task(String name, String description, int id, TaskStatus status, TaskType type) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status && type == task.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, type);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,", this.id, this.type.name(), this.name, this.status.name(),
                this.description);
    }
}

