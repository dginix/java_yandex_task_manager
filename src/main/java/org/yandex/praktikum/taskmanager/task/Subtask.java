package org.yandex.praktikum.taskmanager.task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task{
    /**
     * Значение Id эпика, которому принадлежит подзадача
     */
    private int epicId;

    public Subtask(String name, String description, int id, TaskStatus status, TaskType type, LocalDateTime startTime,
                   Duration duration, int epicId) {
        super(name, description, id, status, type, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int id, TaskStatus status, TaskType type, int epicId) {
        super(name, description, id, status, type);
        this.epicId = epicId;
}

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return super.toString() + this.epicId;
    }
}
