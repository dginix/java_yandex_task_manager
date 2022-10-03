package org.yandex.praktikum.taskmanager.task;

public class Subtask extends Task{
    /**
     * Значение Id эпика, которому принадлежит подзадача
     */
    protected int epicId;

    public Subtask(String name, String description, int id, TaskStatus status, TaskType type, int epicId) {
        super(name, description, id, status, type);
        this.epicId = epicId;
}

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.toString() + this.epicId;
    }
}
