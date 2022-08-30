package org.yandex.praktikum.taskmanager.manager;

/**
 * Утилитарный класс, позволяющий подобрать нужную реализацию интерфейса TaskManager
 * для возвращаемого менеджера задач
 */
public class Managers {
    public TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
}
