package org.yandex.praktikum.taskmanager.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskType type;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String name, String description, int id, TaskStatus status, TaskType type) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
    }

    public Task(String name, String description, int id, TaskStatus status, TaskType type, Duration duration,
                LocalDateTime startTime) {
        this(name, description, id, status, type);
        this.duration = duration;
        this.startTime = startTime;
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

    public Duration getDuration() {
        return duration;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        if (startTime == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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

    /**
     *
     * @param value значение строки для конвертации в объект задачи
     * @return задача с определенынм типом
     */
    public static Task fromString(String value) {
        String[] splitInput = value.split(",");

        int id = Integer.parseInt(splitInput[0]);
        TaskType type = TaskType.valueOf(splitInput[1]);
        String name = splitInput[2];
        TaskStatus status = TaskStatus.valueOf(splitInput[3]);
        String description = splitInput[4];

        switch (type) {
            case TASK -> {
                return new Task(name, description, id, status, type);
            }
            case EPIC -> {
                return new Epic(name, description, id, status, type);
            }
            case SUBTASK -> {
                int epicId = Integer.parseInt(splitInput[5]);
                return new Subtask(name, description, id, status, type, epicId);
            }
        }
        return null;
    }
}

