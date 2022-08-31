package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;

import java.util.List;

public interface TaskManager {
    int getNewId();
    void addTask(Task task);
    void updateTask(Task task);
    Task getTaskById(int id);
    void deleteTaskById(int id);

    void addEpic(Epic epic);
    void updateEpic(Epic epic);
    Epic getEpicById(int id);

    void addSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);
    Subtask getSubtaskById(int id);

    void getAllTasks();
    void deleteAllTasks();
    List<Task> getHistory();
}
