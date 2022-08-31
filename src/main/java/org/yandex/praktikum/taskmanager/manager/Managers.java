package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.historymanager.HistoryManager;
import org.yandex.praktikum.taskmanager.historymanager.InMemoryHistoryManager;

/**
 * Утилитарный класс, позволяющий подобрать нужную реализацию интерфейса TaskManager
 * для возвращаемого менеджера задач
 */
public class Managers {
    public TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
