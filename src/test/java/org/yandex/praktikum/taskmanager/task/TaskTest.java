package org.yandex.praktikum.taskmanager.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Managers managers = new Managers();
    TaskManager taskManager;
    Task task1;

    @BeforeEach
    public void beforeEach() {
        taskManager = managers.getDefault();
        task1 = new Task("Task1", "descr1", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);
    }

    @Test
    public void fromStringTaskNoDurationTest() {
        Task testStringTask = Task.fromString("1,TASK,Task1,NEW,descr1,");
        assertEquals(task1, testStringTask, "Задачи не совпадают");
    }
    @Test
    public void toStringTaskNoDurationTest() {
        String stringTask = task1.toString();
        assertEquals("1,TASK,Task1,NEW,descr1,", stringTask, "Строки не совпадают");
    }
}