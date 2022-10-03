package org.yandex.praktikum.taskmanager.task;

public class Subtask extends Task{
    /**
     * Значение Id эпика, которому принадлежит подзадача
     */
    private Epic epicOwned;
    public Subtask(String name, String description, int id, TaskStatus status, TaskType type, Epic epicOwned) {
        super(name, description, id, status, type);
        this.epicOwned = epicOwned;
    }

    public Epic getEpicOwned() {
        return epicOwned;
    }

    @Override
    public String toString() {
        return super.toString() + this.epicOwned.id;
    }
}
