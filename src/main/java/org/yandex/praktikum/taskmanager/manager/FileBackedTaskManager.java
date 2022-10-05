package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.historymanager.HistoryManager;
import org.yandex.praktikum.taskmanager.models.exceptions.ManagerSaveException;
import org.yandex.praktikum.taskmanager.task.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

    final static String savePath = "/home/kruchinin/IdeaProjects/java_yandex_task_manager/src/main/resources" +
            "/state_saver/data.csv";

    public FileBackedTaskManager() {
        super();
    }

    /**
     * Метод для сохранения текущего состояния менеджера задач в файл
     */
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savePath))) {
            if (!taskMap.isEmpty()) {
                for(Map.Entry<Integer, Task> entry: taskMap.entrySet()) {
                    writer.write(entry.getValue().toString());
                }
                writer.write("\n");
            }

            if (!taskMap.isEmpty()) {
                for (Map.Entry<Integer, Epic> entry : epicMap.entrySet()) {
                    writer.write(entry.getValue().toString());
                }
                writer.write("\n");
            }

            if (!taskMap.isEmpty()) {
                for (Map.Entry<Integer, Subtask> entry : subtaskMap.entrySet()) {
                    writer.write(entry.getValue().toString());
                }
                writer.write("\n");
            }

            if (!historyManager.isEmpty()) {
                writer.write("\n");
                writer.write(historyToString(historyManager));
            }
        }
        catch (Exception e) {
            throw new ManagerSaveException(e.getMessage());
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

    public static FileBackedTaskManager loadFromFile(File file) {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                FileBackedTaskManager manager = new FileBackedTaskManager();
                while (reader.ready()) {
                    String line = reader.readLine();

                    if (line.isEmpty()) {
                        List<Integer> idList = historyFromString(reader.readLine());
                        for (Integer id : idList) {
                            if (manager.taskMap.containsKey(id)) {
                                manager.historyManager.add(manager.taskMap.get(id));
                            }
                            else if (manager.epicMap.containsKey(id)) {
                                manager.historyManager.add(manager.epicMap.get(id));
                            }
                            else if (manager.subtaskMap.containsKey(id)){
                                manager.historyManager.add(manager.subtaskMap.get(id));
                            }
                        }
                        break;
                    }
                    else {
                        Task loadTask = fromString(line);
                        if (loadTask != null) {
                            switch (loadTask.getType()) {
                                case TASK -> manager.addTask(loadTask);
                                case EPIC -> manager.addEpic((Epic) loadTask);
                                case SUBTASK -> manager.addSubtask((Subtask) loadTask);
                            }
                        }
                    }
                }
                return manager;
            }
            catch (Exception e) {
                throw new ManagerSaveException(e.getMessage());
            }
        }
        return null;
    }

    static List<Integer> historyFromString(String value) {
        String[] stringId = value.split(",");
        List<Integer> result = new ArrayList<>();

        for (String id : stringId) {
            result.add(Integer.parseInt(id));
        }
        return result;
    }

    static String historyToString(HistoryManager manager) {
        List<Task> taskList = manager.getHistory();
        StringBuilder result = new StringBuilder();
        for (Task task : taskList) {
            result.append(task.getId());
        }
        return result.toString();
    }
}