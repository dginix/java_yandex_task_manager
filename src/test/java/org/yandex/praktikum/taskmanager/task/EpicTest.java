package org.yandex.praktikum.taskmanager.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Managers managers = new Managers();
    TaskManager testManager;
    Epic epic;

    @BeforeEach
    public void beforeEach() {
        testManager = managers.getDefault();
        epic = new Epic("эпик 1", "бла бла бла", testManager.getNewId(), TaskStatus.NEW,
                TaskType.EPIC);
        testManager.addEpic(epic);
    }

    @Test
    public void emptyEpicList() {
        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика без подзадач должен быть NEW");
    }

    @Test
    public void newSubtasksInEpicList() {
        Subtask subtask1 = new Subtask("подзадача 1", "1", testManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic.getId());

        Subtask subtask2 = new Subtask("подзадача 2", "2", testManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic.getId());

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);
        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    public void doneSubtasksInEpicList() {
        assertEquals(TaskStatus.NEW, epic.getStatus());

        Subtask subtask1 = new Subtask("подзадача 1", "1", testManager.getNewId(), TaskStatus.DONE,
                TaskType.SUBTASK, epic.getId());

        Subtask subtask2 = new Subtask("подзадача 2", "2", testManager.getNewId(), TaskStatus.DONE,
                TaskType.SUBTASK, epic.getId());

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);
        assertEquals(TaskStatus.DONE, epic.getStatus(), "Статус эпика должен быть DONE");
    }

    @Test
    public void newAndDoneSubtasksInEpicList() {
        assertEquals(TaskStatus.NEW, epic.getStatus());

        Subtask subtask1 = new Subtask("подзадача 1", "1", testManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic.getId());

        Subtask subtask2 = new Subtask("подзадача 2", "2", testManager.getNewId(), TaskStatus.DONE,
                TaskType.SUBTASK, epic.getId());

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }

    @Test
    public void inProgressSubtasksInEpicList() {
        assertEquals(TaskStatus.NEW, epic.getStatus());

        Subtask subtask1 = new Subtask("подзадача 1", "1", testManager.getNewId(), TaskStatus.IN_PROGRESS,
                TaskType.SUBTASK, epic.getId());

        Subtask subtask2 = new Subtask("подзадача 2", "2", testManager.getNewId(), TaskStatus.IN_PROGRESS,
                TaskType.SUBTASK, epic.getId());

        testManager.addSubtask(subtask1);
        testManager.addSubtask(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }
}