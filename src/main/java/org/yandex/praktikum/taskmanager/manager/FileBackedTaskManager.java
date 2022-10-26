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
                        Task loadTask = Task.fromString(line);
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

    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }
    public Task getTaskById(int id) {
        Task result = super.getTaskById(id);
        save();
        return result;
    }
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }
    public Epic getEpicById(int id) {
        Epic result = super.getEpicById(id);
        save();
        return result;
    }
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }
    public Subtask getSubtaskById(int id) {
        Subtask result = super.getSubtaskById(id);
        save();
        return result;
    }
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }
}
