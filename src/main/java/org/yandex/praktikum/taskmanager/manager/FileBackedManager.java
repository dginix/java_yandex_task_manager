package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.task.*;

public class FileBackedManager extends InMemoryTaskManager {
    /**
     * Метод для сохранения текущего состояния менеджера задач в файл
     */
    public void save() {

    }

    /**
     *
     * @param value значение строки для конвертации в объект задачи
     * @return задача с определенынм типом
     */
    public static Task fromString(String value) {
        String[] splitInput = value.split(",");

        int id = Integer.parseInt(splitInput[0]);
        TaskType type = TaskType.valueOf(splitInput[1]);
        String name = splitInput[2];
        TaskStatus status = TaskStatus.valueOf(splitInput[3]);
        String description = splitInput[4];

        switch (type) {
            case TASK -> {
                return new Task(name, description, id, status, type);
            }
            case EPIC -> {
                return new Epic(name, description, id, status, type);
            }
            case SUBTASK -> {
                int epicId = Integer.parseInt(splitInput[5]);
                return new Subtask(name, description, id, status, type, epicId);
            }
        }
        return null;
    }
}
