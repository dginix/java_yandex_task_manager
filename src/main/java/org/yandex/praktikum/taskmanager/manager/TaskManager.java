package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;

public interface TaskManager {
    int getNewId();
    void addTask(Task task);
    void updateTask(Task task);

    void addEpic(Epic epic);
    void updateEpic(Epic epic);

    void addSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);

    void getAllTasks();
    void deleteAllTasks();
    Task getTaskById(int id);
    public void deleteTaskById(int id);
}
