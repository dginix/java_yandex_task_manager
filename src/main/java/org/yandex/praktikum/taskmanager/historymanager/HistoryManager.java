package org.yandex.praktikum.taskmanager.historymanager;

import org.yandex.praktikum.taskmanager.task.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);
    void remove(int id);
    ArrayList<Task> getHistory();
}
