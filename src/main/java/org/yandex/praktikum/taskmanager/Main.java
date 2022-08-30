package org.yandex.praktikum.taskmanager;

import org.yandex.praktikum.taskmanager.manager.InMemoryTaskManager;
import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;
import org.yandex.praktikum.taskmanager.task.TaskStatus;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskInMemoryTaskManager = new InMemoryTaskManager();
        taskInMemoryTaskManager.addTask(new Task("задача 1", "что-то там", taskInMemoryTaskManager.getNewId(), TaskStatus.NEW));
        taskInMemoryTaskManager.addTask(new Task("задача 2", "что-то там", taskInMemoryTaskManager.getNewId(), TaskStatus.NEW));
        Epic epic1 = new Epic("эпик 1", "бла бла бла", taskInMemoryTaskManager.getNewId(), TaskStatus.NEW);
        Epic epic2 = new Epic("эпик 2", "бла бла бла", taskInMemoryTaskManager.getNewId(), TaskStatus.NEW);
        Subtask subtask1 = new Subtask("подзадача 1", "бла бла бла", taskInMemoryTaskManager.getNewId(),
                TaskStatus.NEW, epic1);
        Subtask subtask2 = new Subtask("подзадача 2", "бла бла бла", taskInMemoryTaskManager.getNewId(),
                TaskStatus.NEW, epic1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        taskInMemoryTaskManager.addEpic(epic1);
        taskInMemoryTaskManager.addEpic(epic2);

        subtask1.setStatus(TaskStatus.DONE);
        taskInMemoryTaskManager.updateSubtask(subtask1);

        subtask2.setStatus(TaskStatus.DONE);
        taskInMemoryTaskManager.updateSubtask(subtask1);

        System.out.println("Done!");

        taskInMemoryTaskManager.getAllTasks();
    }
}
