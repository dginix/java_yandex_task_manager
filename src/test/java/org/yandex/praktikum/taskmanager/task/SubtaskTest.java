package org.yandex.praktikum.taskmanager.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    Managers managers = new Managers();
    TaskManager taskManager;
    Epic epic1;
    Subtask subtask1;

    @BeforeEach
    public void beforeEach() {
        taskManager = managers.getDefault();
        epic1 = new Epic("Epic1", "descr", taskManager.getNewId(), TaskStatus.NEW, TaskType.EPIC);
        subtask1 = new Subtask("Subtask1", "descr", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());
    }

    @Test
    public void fromStringTaskNoDurationTest() {
        Subtask testStringSubtask = (Subtask) Task.fromString("2,SUBTASK,Subtask1,NEW,descr,,,1");
        assertEquals(subtask1, testStringSubtask, "Подзадачи не совпадают");
    }
    @Test
    public void toStringTaskNoDurationTest() {
        assertEquals("2,SUBTASK,Subtask1,NEW,descr,,,1", subtask1.toString(),
                "Создание подзадачи из задачи без времени не работает корректно");
    }

    @Test
    public void fromStringTaskWithDurationTest() {
        LocalDateTime startTime = LocalDateTime.of(2022, 11, 26, 16, 44);
        Duration duration = Duration.ofHours(12).plusMinutes(15);
        subtask1.setStartTime(startTime);
        subtask1.setDuration(duration);

        Task testStringTask = Task.fromString("2,SUBTASK,Subtask1,NEW,descr,2022-11-26T16:44:00,PT12H15M,1");
        assertEquals(subtask1, testStringTask, "Задачи не совпадают");
    }
    @Test
    public void toStringTaskWithDurationTest() {
        LocalDateTime startTime = LocalDateTime.of(2022, 11, 26, 16, 44);
        Duration duration = Duration.ofHours(12).plusMinutes(15);
        subtask1.setStartTime(startTime);
        subtask1.setDuration(duration);

        assertEquals("2,SUBTASK,Subtask1,NEW,descr,2022-11-26T16:44:00,PT12H15M,1", subtask1.toString(),
                "Строки не совпадают");
    }
}