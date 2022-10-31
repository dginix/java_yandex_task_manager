package org.yandex.praktikum.taskmanager.historymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.manager.InMemoryTaskManager;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;
import org.yandex.praktikum.taskmanager.task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    public HistoryManager historyManager;
    public TaskManager taskManager;

    public Task task1;
    public Task task2;
    public Task task3;
    public Task task4;
    public Task task5;
    public Task task6;
    public Task task7;
    public Task task8;

    public Epic epic1;
    public Epic epic2;

    public Subtask subtask1;
    public Subtask subtask2;
    public Subtask subtask3;
    public Subtask subtask4;

    @BeforeEach
    void beforeEach() {
        Managers manager = new Managers();
        taskManager = manager.getDefault();
        historyManager = taskManager.getHistoryManager();

        LocalDateTime task1DateTime = LocalDateTime.of(2022,11,27,15,53);
        LocalDateTime task2DateTime = LocalDateTime.of(2022,11,27,12,2);
        Duration task1Duration = Duration.ofHours(10);
        Duration task2Duration = Duration.ofHours(2);

        task1 = new Task("Task1", "1", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK,
                task1DateTime, task1Duration);
        task2 = new Task("Task2", "2", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK,
                task2DateTime, task2Duration);
        task3 = new Task("Task3", "3", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);
        task4 = new Task("Task4", "4", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);
        task5 = new Task("Task5", "5", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);
        task6 = new Task("Task6", "6", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);
        task7 = new Task("Task7", "7", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);
        task8 = new Task("Task8", "8", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);

        epic1 = new Epic("Epic1", "1", taskManager.getNewId(), TaskStatus.NEW, TaskType.EPIC);
        epic2 = new Epic("Epic2", "2", taskManager.getNewId(), TaskStatus.NEW, TaskType.EPIC);

        subtask1 = new Subtask("Subtask1", "1", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());
        subtask2 = new Subtask("Subtask2", "2", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());
        subtask3 = new Subtask("Subtask3", "3", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic2.getId());
        subtask4 = new Subtask("Subtask4", "4", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic2.getId());

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addTask(task5);
        taskManager.addTask(task6);
        taskManager.addTask(task7);
        taskManager.addTask(task8);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        epic2.addSubtask(subtask3);
        epic2.addSubtask(subtask4);
    }

    @Test
    void add() {
    }

    @Test
    void remove() {
    }

    @Test
    void getHistory_EmptyHistoryTest() {
        ArrayList<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "Список истории должен быть пуст");
    }
    @Test
    void getHistory_CasualHistoryTest() {
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task5.getId());
        taskManager.getSubtaskById(subtask4.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getEpicById(epic1.getId());

        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(task2, history.get(0), "Неверный порядок истории");
        assertEquals(task5, history.get(1), "Неверный порядок истории");
        assertEquals(subtask4, history.get(2), "Неверный порядок истории");
        assertEquals(subtask1, history.get(3), "Неверный порядок истории");
        assertEquals(epic1, history.get(4), "Неверный порядок истории");
    }
    @Test
    void getHistory_FullHistoryTest() {
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task5.getId());
        taskManager.getSubtaskById(subtask4.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task4.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task8.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getEpicById(epic2.getId());

        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(task5, history.get(0), "Неверный порядок истории");
        assertEquals(subtask4, history.get(1), "Неверный порядок истории");
        assertEquals(subtask1, history.get(2), "Неверный порядок истории");
        assertEquals(epic1, history.get(3), "Неверный порядок истории");
        assertEquals(task1, history.get(4), "Неверный порядок истории");
        assertEquals(task4, history.get(5), "Неверный порядок истории");
        assertEquals(task3, history.get(6), "Неверный порядок истории");
        assertEquals(task8, history.get(7), "Неверный порядок истории");
        assertEquals(subtask2, history.get(8), "Неверный порядок истории");
        assertEquals(epic2, history.get(9), "Неверный порядок истории");

        assertEquals(10, history.size(), "Колличество элементов не может быть больше 10");
    }
}