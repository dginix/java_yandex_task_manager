package org.yandex.praktikum.taskmanager.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

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
        Task testStringTask = Task.fromString("1,TASK,Task1,NEW,descr1,,,");
        assertEquals(task1, testStringTask, "Задачи не совпадают");
    }
    @Test
    public void toStringTaskNoDurationTest() {
        assertEquals("1,TASK,Task1,NEW,descr1,,,", task1.toString(),
                "Создание строки из задачи без времени не работает корректно");
    }

    @Test
    public void fromStringTaskWithDurationTest() {
        LocalDateTime startTime = LocalDateTime.of(2022, 11, 26, 16, 44);
        Duration duration = Duration.ofHours(12).plusMinutes(15);
        task1.setStartTime(startTime);
        task1.setDuration(duration);

        Task testStringTask = Task.fromString("1,TASK,Task1,NEW,descr1,2022-11-26T16:44:00,PT12H15M,");
        assertEquals(task1, testStringTask, "Задачи не совпадают");
    }
    @Test
    public void toStringTaskWithDurationTest() {
        LocalDateTime startTime = LocalDateTime.of(2022, 11, 26, 16, 44);
        Duration duration = Duration.ofHours(12).plusMinutes(15);
        task1.setStartTime(startTime);
        task1.setDuration(duration);

        assertEquals("1,TASK,Task1,NEW,descr1,2022-11-26T16:44:00,PT12H15M,", task1.toString(),
                "Строки не совпадают");
    }
}