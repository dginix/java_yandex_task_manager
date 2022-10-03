package org.yandex.praktikum.taskmanager;

import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;
import org.yandex.praktikum.taskmanager.task.*;

public class Main {
    public static void main(String[] args) {
        Managers managers = new Managers();

        TaskManager testManager = managers.getDefault();

        testManager.addTask(new Task("задача 1", "что-то там", testManager.getNewId(), TaskStatus.NEW,
                TaskType.TASK));
        testManager.addTask(new Task("задача 2", "что-то там", testManager.getNewId(), TaskStatus.NEW,
                TaskType.TASK));

        Epic epic1 = new Epic("эпик 1", "бла бла бла", testManager.getNewId(), TaskStatus.NEW,
                TaskType.EPIC);

        Subtask subtask1 = new Subtask("подзадача 1", "бла бла бла", testManager.getNewId(),
                TaskStatus.NEW, TaskType.SUBTASK, epic1);
        Subtask subtask2 = new Subtask("подзадача 2", "бла бла бла", testManager.getNewId(),
                TaskStatus.NEW, TaskType.SUBTASK, epic1);

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);

        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        testManager.addEpic(epic1);

        subtask1.setStatus(TaskStatus.DONE);
        testManager.updateSubtask(subtask1);

        subtask2.setStatus(TaskStatus.DONE);
        testManager.updateSubtask(subtask1);

        System.out.println("\n============== Все задачи ============");
        testManager.getAll();

        System.out.println("\n======== Поштучно получаем задачи ========");
        testManager.getTaskById(1);
        testManager.getEpicById(3);
        testManager.getSubtaskById(4);
        testManager.getSubtaskById(5);
        testManager.getTaskById(2);


        System.out.println("\n======== Получение истории вызова задач ========");
        System.out.println(testManager.getHistory());

        testManager.deleteSubtaskById(4);
        System.out.println("\n======== Получение истории после удаления ========");
        System.out.println(testManager.getHistory());

        System.out.println("\nDone!");
    }
}
