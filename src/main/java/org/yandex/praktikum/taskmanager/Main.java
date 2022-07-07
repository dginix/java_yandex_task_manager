package org.yandex.praktikum.taskmanager;

import org.yandex.praktikum.taskmanager.manager.Manager;
import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;
import org.yandex.praktikum.taskmanager.task.TaskStatus;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Manager taskManager = new Manager();
        taskManager.addTask(new Task("задача 1", "что-то там", taskManager.getNewId(), TaskStatus.NEW));
        taskManager.addTask(new Task("задача 2", "что-то там", taskManager.getNewId(), TaskStatus.NEW));
        Epic epic1 = new Epic("эпик 1", "бла бла бла", taskManager.getNewId(), TaskStatus.NEW);
        Epic epic2 = new Epic("эпик 2", "бла бла бла", taskManager.getNewId(), TaskStatus.NEW);
        Subtask subtask1 = new Subtask("подзадача 1", "бла бла бла", taskManager.getNewId(),
                TaskStatus.NEW, epic1);
        Subtask subtask2 = new Subtask("подзадача 2", "бла бла бла", taskManager.getNewId(),
                TaskStatus.NEW, epic1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
    }
}
