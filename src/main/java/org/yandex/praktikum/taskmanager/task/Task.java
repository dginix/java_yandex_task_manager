package org.yandex.praktikum.taskmanager.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public Task(String name, String description, int id, TaskStatus status, TaskType type, LocalDateTime startTime,
                Duration duration) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
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
        if (startTime == null || duration == null) {
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
                && status == task.status && type == task.type && Objects.equals(duration, task.duration)
                && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, type, duration, startTime);
    }

    @Override
    public String toString() {
        String startTimeString = "";
        String durationString = "";

        if (startTime != null && duration != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            startTimeString = startTime.format(formatter);
            durationString = duration.toString();
        }

        return String.format("%d,%s,%s,%s,%s,%s,%s,", this.id, this.type.name(), this.name, this.status.name(),
                this.description, startTimeString, durationString);
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
        LocalDateTime startTime = null;
        Duration duration = null;

        if (splitInput.length > 5) {
            if(!splitInput[5].isEmpty() && !splitInput[6].isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                startTime = LocalDateTime.parse(splitInput[5], formatter);
                duration = Duration.parse(splitInput[6]);
            }
        }

        switch (type) {
            case TASK -> {
                if (startTime != null && duration != null) {
                    return new Task(name, description, id, status, type, startTime, duration);
                }
                return new Task(name, description, id, status, type);
            }
            case EPIC -> {
                if (startTime != null && duration != null) {
                    return new Epic(name, description, id, status, type, startTime, duration);
                }
                return new Epic(name, description, id, status, type);

            }
            case SUBTASK -> {
                if (startTime != null && duration != null) {
                    int epicId = Integer.parseInt(splitInput[7]);
                    return new Subtask(name, description, id, status, type, startTime, duration, epicId);
                }

                int epicId = Integer.parseInt(splitInput[7]);
                return new Subtask(name, description, id, status, type, epicId);
            }
        }

        return null;
    }
}

