package org.yandex.praktikum.taskmanager;

import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;
import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;
import org.yandex.praktikum.taskmanager.task.TaskStatus;

public class Main {
    public static void main(String[] args) {
        Managers managers = new Managers();

        TaskManager testManager = managers.getDefault();

        testManager.addTask(new Task("задача 1", "что-то там", testManager.getNewId(), TaskStatus.NEW));
        testManager.addTask(new Task("задача 2", "что-то там", testManager.getNewId(), TaskStatus.NEW));

        Epic epic1 = new Epic("эпик 1", "бла бла бла", testManager.getNewId(), TaskStatus.NEW);
        Epic epic2 = new Epic("эпик 2", "бла бла бла", testManager.getNewId(), TaskStatus.NEW);

        Subtask subtask1 = new Subtask("подзадача 1", "бла бла бла", testManager.getNewId(),
                TaskStatus.NEW, epic1);
        Subtask subtask2 = new Subtask("подзадача 2", "бла бла бла", testManager.getNewId(),
                TaskStatus.NEW, epic1);

        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        testManager.addEpic(epic1);
        testManager.addEpic(epic2);

        subtask1.setStatus(TaskStatus.DONE);
        testManager.updateSubtask(subtask1);

        subtask2.setStatus(TaskStatus.DONE);
        testManager.updateSubtask(subtask1);

        testManager.getAll();

        testManager.getTaskById(1);
        testManager.getEpicById(4);
        testManager.getTaskById(2);


        System.out.println("Done!");
    }
}
