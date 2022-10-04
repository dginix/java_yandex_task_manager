package org.yandex.praktikum.taskmanager;

import org.yandex.praktikum.taskmanager.manager.FileBackedManager;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;
import org.yandex.praktikum.taskmanager.task.*;

public class Main {
    public static void main(String[] args) {
        //testInMemoryManager();
        testFileBackedManager();
    }
    public static void testInMemoryManager() {
        Managers managers = new Managers();

        TaskManager testManager = managers.getDefault();

        testManager.addTask(new Task("задача 1", "что-то там", testManager.getNewId(), TaskStatus.NEW,
                TaskType.TASK));
        testManager.addTask(new Task("задача 2", "что-то там", testManager.getNewId(), TaskStatus.NEW,
                TaskType.TASK));

        Epic epic1 = new Epic("эпик 1", "бла бла бла", testManager.getNewId(), TaskStatus.NEW,
                TaskType.EPIC);

        Subtask subtask1 = new Subtask("подзадача 1", "бла бла бла", testManager.getNewId(),
                TaskStatus.NEW, TaskType.SUBTASK, epic1.getId());
        Subtask subtask2 = new Subtask("подзадача 2", "бла бла бла", testManager.getNewId(),
                TaskStatus.NEW, TaskType.SUBTASK, epic1.getId());

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
    public static void testFileBackedManager() {
        FileBackedManager manager = new FileBackedManager();

        Task task1 = new Task("Task 1", "descr1", manager.getNewId(), TaskStatus.NEW,
                TaskType.TASK);
        Epic epic1 = new Epic("Epic 1", "descr 2", manager.getNewId(), TaskStatus.NEW,
                TaskType.EPIC);

        Subtask subtask1 = new Subtask("Subtask 1", "descr 3", manager.getNewId(), TaskStatus.NEW, TaskType.SUBTASK,
                epic1.getId());

        manager.addTask(task1);
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);

        manager.save();
    }
}
