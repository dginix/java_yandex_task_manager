package org.yandex.praktikum.taskmanager.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Managers managers = new Managers();
    TaskManager testManager;
    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;

    @BeforeEach
    public void beforeEach() {
        testManager = managers.getDefault();

        epic1 = new Epic("Epic1", "descr", testManager.getNewId(), TaskStatus.NEW,
                TaskType.EPIC);

        testManager.addEpic(epic1);

        subtask1 = new Subtask("Subtask1", "descr", testManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());
        subtask2 = new Subtask("Subtask2", "descr", testManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());
    }

    @Test
    public void emptyEpicList() {
        assertEquals(TaskStatus.NEW, epic1.getStatus(), "Статус эпика без подзадач должен быть NEW");
    }

    @Test
    public void newSubtasksInEpicList() {
        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);
        assertEquals(TaskStatus.NEW, epic1.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    public void doneSubtasksInEpicList() {
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);
        assertEquals(TaskStatus.DONE, epic1.getStatus(), "Статус эпика должен быть DONE");
    }

    @Test
    public void newAndDoneSubtasksInEpicList() {
        subtask1.setStatus(TaskStatus.DONE);

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }

    @Test
    public void inProgressSubtasksInEpicList() {
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }

    @Test
    public void fromStringEpicNoDurationTest() {
        Epic testStringEpic = (Epic) Task.fromString("1,EPIC,Epic1,NEW,descr,,,");
        assertEquals(epic1, testStringEpic, "Эпики не совпадают");
    }
    @Test
    public void toStringEpicNoDurationTest() {
        assertEquals("1,EPIC,Epic1,NEW,descr,,,", epic1.toString(),
                "Создание строки из эпика без времени не работает корректно");
    }

    @Test
    public void fromStringEpicWithDurationTest() {
        LocalDateTime startTime = LocalDateTime.of(2022, 11, 26, 16, 44);
        Duration duration = Duration.ofHours(12).plusMinutes(15);
        epic1.setStartTime(startTime);
        epic1.setDuration(duration);

        Epic testStringEpic = (Epic) Task.fromString("1,EPIC,Epic1,NEW,descr,2022-11-26T16:44:00,PT12H15M,");
        assertEquals(epic1, testStringEpic, "Задачи не совпадают");
    }
    @Test
    public void toStringEpicWithDurationTest() {
        LocalDateTime startTime = LocalDateTime.of(2022, 11, 26, 16, 44);
        Duration duration = Duration.ofHours(12).plusMinutes(15);
        epic1.setStartTime(startTime);
        epic1.setDuration(duration);

        assertEquals("1,EPIC,Epic1,NEW,descr,2022-11-26T16:44:00,PT12H15M,", epic1.toString(),
                "Строки не совпадают");
    }
}