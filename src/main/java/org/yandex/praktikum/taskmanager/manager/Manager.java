package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;
import org.yandex.praktikum.taskmanager.task.TaskStatus;

import java.util.HashMap;

public class Manager {
    private int idCount = 0;

    private HashMap <Integer, Task> taskMap = new HashMap<>();
    private HashMap <Integer, Epic> epicMap = new HashMap<>();
    private HashMap <Integer, Task> subtaskMap = new HashMap<>();

    public int getNewId(){
        return ++idCount;
    }

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
    }

    public void updateEpic(Epic epic){
        epicMap.put(epic.getId(), epic);
    }

    /**
     * @param subtask новая подзадача
     */
    public void addSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
    }

    /**
     * Обновляет подзадачу и проверяет на завершенность эпика, которому эта подзадача принадлежит
     * @param subtask новая подзадача, взамен старой
     */
    public void updateSubtask(Subtask subtask) {
        boolean changeStatus = true;
        for (Subtask subtaskInEpic : subtask.getEpicOwned().getSubtaskList()) {
            if (subtaskInEpic.getStatus() != TaskStatus.DONE) {
                changeStatus = false;
                break;
            }
        }

        if (changeStatus) {
            subtask.getEpicOwned().setStatus(TaskStatus.DONE);
        }
        else {
            subtask.getEpicOwned().setStatus(TaskStatus.IN_PROGRESS);
        }

        subtaskMap.put(subtask.getId(), subtask);
    }
}
