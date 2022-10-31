package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.historymanager.HistoryManager;
import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    int getNewId();
    void addTask(Task task);
    void updateTask(Task task);
    Task getTaskById(int id);
    List<Task> getAllTasks();
    void deleteTaskById(int id);
    void deleteAllTask();

    void addEpic(Epic epic);
    void updateEpic(Epic epic);
    Epic getEpicById(int id);
    List<Epic> getAllEpics();
    void deleteEpicById(int id);
    void deleteAllEpic();

    void addSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);
    Subtask getSubtaskById(int id);
    List<Subtask> getAllSubtasks();
    void deleteSubtaskById(int id);
    void deleteAllSubtask();

    void getAll();
    void deleteAll();
    List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();

    HistoryManager getHistoryManager();
}
