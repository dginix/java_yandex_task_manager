package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.task.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileBackedManager extends InMemoryTaskManager {

    final static String savePath = "/home/kruchinin/IdeaProjects/java_yandex_task_manager/src/main/resources" +
            "/state_saver/data.csv";

    public FileBackedManager() {
        super();
    }

    /**
     * Метод для сохранения текущего состояния менеджера задач в файл
     */
    public void save() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(savePath))) {
            if(!taskMap.isEmpty()) {
                for(Map.Entry<Integer, Task> entry: taskMap.entrySet()) {
                    writer.write(entry.getValue().toString());
                }
                writer.write("\n");
            }

            if(!taskMap.isEmpty()) {
                for (Map.Entry<Integer, Epic> entry : epicMap.entrySet()) {
                    writer.write(entry.getValue().toString());
                }
                writer.write("\n");
            }

            if(!taskMap.isEmpty()) {
                for (Map.Entry<Integer, Subtask> entry : subtaskMap.entrySet()) {
                    writer.write(entry.getValue().toString());
                }
                writer.write("\n");
            }
        }
        catch (IOException e) {
            e.getStackTrace();
        }
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
