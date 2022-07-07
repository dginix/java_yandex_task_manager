package org.yandex.praktikum.taskmanager.task;

public class Subtask extends Task{
    /**
     * Значение Id эпика, которому принадлежит подзадача
     */
    Epic epicOwned;
    public Subtask(String name, String description, int id, TaskStatus status, Epic epicOwned) {
        super(name, description, id, status);
        this.epicOwned = epicOwned;
    }
}
