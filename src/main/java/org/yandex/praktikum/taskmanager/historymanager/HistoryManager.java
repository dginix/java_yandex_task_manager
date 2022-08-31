package org.yandex.praktikum.taskmanager.historymanager;

import org.yandex.praktikum.taskmanager.task.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
