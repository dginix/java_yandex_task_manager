package org.yandex.praktikum.taskmanager.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    public T taskManager;

    public Task task1;
    public Task task2;
    public Task task3;

    public Epic epic1;
    public Epic epic2;

    public Subtask subtask1;
    public Subtask subtask2;
    public Subtask subtask3;
    public Subtask subtask4;

    abstract public void createNewManager();

    @BeforeEach
    public void beforeEach() {
        createNewManager();
        LocalDateTime task1DateTime = LocalDateTime.of(2022,11,27,15,53);
        LocalDateTime task2DateTime = LocalDateTime.of(2022,11,27,12,2);
        Duration task1Duration = Duration.ofHours(10);
        Duration task2Duration = Duration.ofHours(2);

        task1 = new Task("Task1", "1", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK,
                task1DateTime, task1Duration);
        task2 = new Task("Task2", "2", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK,
                task2DateTime, task2Duration);
        task3 = new Task("Task3", "3", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);

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
    }

    @Test
    public void getIdTest() {
        int count = taskManager.getNewId();
        assertEquals(count + 1, taskManager.getNewId(), "Менеджер не сгенерировал id");
    }

    @Test
    void addTaskTest() {
        taskManager.addTask(null);
        assertTrue(taskManager.getAllTasks().isEmpty(), "Список задач должен быть пустой");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertFalse(taskManager.getAllTasks().isEmpty(), "Список задач не должен быть пустой");
    }
    @Test
    void updateTaskTest() {
        taskManager.addTask(task1);
        Task updateTask1 = new Task("Update Task1", "1", task1.getId(), TaskStatus.NEW, TaskType.TASK);
        taskManager.updateTask(updateTask1);
        assertEquals(taskManager.getTaskById(task1.getId()), updateTask1,
                "Задачи должны быть разные с тем же id");

        Task againUpdateTask1 = new Task("Again Update Task1", "1", taskManager.getNewId(),
                TaskStatus.NEW, TaskType.TASK);

        taskManager.updateTask(againUpdateTask1);
        assertNull(taskManager.getTaskById(againUpdateTask1.getId()),
                "Не должно добавить или обновить задачу с несуществующим id");
    }
    @Test
    void getTaskByIdTest() {
        assertNull(taskManager.getTaskById(1), "Добавленых задач быть не должно");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertEquals(taskManager.getTaskById(task1.getId()), task1,
                "Не вернул ранее добавленную задачу по тому же id");
    }
    @Test
    void deleteTaskByIdTest() {
        taskManager.deleteTaskById(1);
        assertTrue(taskManager.getAllTasks().isEmpty(), "Список должен быть пуст");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.deleteTaskById(task1.getId());
        assertEquals(taskManager.getAllTasks().get(0), task2, "В списке должна остаться только одна задача");
    }
    @Test
    void deleteAllTaskTest() {
        taskManager.deleteAllTask();
        assertTrue(taskManager.getAllTasks().isEmpty(), "Список должен быть пуст");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.deleteAllTask();
        assertTrue(taskManager.getAllTasks().isEmpty(), "Список должен быть пуст");
    }

    @Test
    void addEpicTest() {
        taskManager.addEpic(null);
        assertTrue(taskManager.getAllEpics().isEmpty(), "Список эпиков должен быть пустой");
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        assertFalse(taskManager.getAllEpics().isEmpty(), "Список эпиков не должен быть пустой");
    }
    @Test
    void updateEpicTest() {
        taskManager.addEpic(epic1);
        Epic updateEpic1 = new Epic("Update Epic1", "1", epic1.getId(), TaskStatus.NEW, TaskType.EPIC);
        taskManager.updateEpic(updateEpic1);
        assertEquals(taskManager.getEpicById(epic1.getId()), updateEpic1,
                "Эпики должны быть разные с тем же id");

        Epic againUpdateEpic1 = new Epic("Again Update Epic1", "1", taskManager.getNewId(),
                TaskStatus.NEW, TaskType.EPIC);

        taskManager.updateEpic(againUpdateEpic1);
        assertNull(taskManager.getTaskById(againUpdateEpic1.getId()),
                "Не должно добавить или обновить эпик с несуществующим id");
    }
    @Test
    void getEpicByIdTest() {
        assertNull(taskManager.getEpicById(1), "Добавленых эпиков быть не должно");

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        assertEquals(taskManager.getEpicById(epic1.getId()), epic1,
                "Не вернул ранее добавленный эпик по тому же id");
    }
    @Test
    void deleteEpicByIdTest() {
        taskManager.deleteEpicById(1);
        assertTrue(taskManager.getAllEpics().isEmpty(), "Список должен быть пуст");

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        taskManager.deleteEpicById(epic1.getId());
        assertEquals(taskManager.getAllEpics().get(0), epic2, "В списке должен остаться только один эпик");

        List<Subtask> subtasks = epic1.getSubtaskList();
        assertFalse(taskManager.getAllSubtasks().contains(subtasks), "Связанные подзадачи должны быть удалены");
    }
    @Test
    void deleteAllEpicTest() {
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        taskManager.deleteAllEpic();
        assertTrue(taskManager.getAllEpics().isEmpty(), "Список всез эпиков должен быть пуст");
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Список всех связанных подзадач должен быть пуст");
    }

    @Test
    void checkUpdateEpicStatusTest() {
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(epic1.getStatus(), TaskStatus.NEW, "Статус нового эпика должен быть NEW");

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);

        assertEquals(epic1.getStatus(), TaskStatus.IN_PROGRESS, "Статус эпика должен быть IN_PROGRESS");

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);

        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);

        assertEquals(epic1.getStatus(), TaskStatus.DONE, "Статус эпика должен быть DONE");
    }

    @Test
    void addSubtaskTest() {
        taskManager.addSubtask(null);
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Список подзадач должен быть пустой");

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        assertFalse(taskManager.getAllSubtasks().isEmpty(), "Список эпиков не должен быть пустой");
    }
    @Test
    void updateSubtask() {
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        Subtask updateSubtask1 = new Subtask("Subtask1", "1", subtask1.getId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());

        taskManager.updateSubtask(updateSubtask1);

        assertEquals(taskManager.getSubtaskById(subtask1.getId()), updateSubtask1,
                "Подзадача не попала в менеджер");

        Subtask againUpdateSubtask1 = new Subtask("Update again Subtask1", "1", subtask1.getId(),
                TaskStatus.NEW, TaskType.SUBTASK, epic1.getId());

        taskManager.updateSubtask(againUpdateSubtask1);
        assertNull(taskManager.getTaskById(againUpdateSubtask1.getId()),
                "Подзадача попала в менеджер");
    }
    @Test
    void getSubtaskByIdTest() {
        assertNull(taskManager.getSubtaskById(1), "Добавленых подзадач быть не должно");

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(taskManager.getSubtaskById(subtask1.getId()), subtask1,
                "Не вернул ранее добавленную подзадачу по тому же id");
    }
    @Test
    void deleteSubtaskByIdTest() {
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        taskManager.deleteAllEpic();
        assertTrue(taskManager.getAllEpics().isEmpty(), "Список всез эпиков должен быть пуст");
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Список всех связанных подзадач должен быть пуст");
    }

    @Test
    void deleteAllSubtaskTest() {
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        taskManager.deleteAllSubtask();
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Список всех связанных подзадач должен быть пуст");
    }

    @Test
    void getPrioritizedTasks_CheckSortingTest() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        TreeSet<Task> result = taskManager.getPrioritizedTasks();
        Iterator<Task> it = result.iterator();
        assertEquals(task2, it.next(), "Задачи не отсортированы");
        assertEquals(task1, it.next(), "Задачи не отсортированы");
        assertEquals(task3, it.next(), "Задачи не отсортированы");
    }
    @Test
    void getPrioritizedTasks_CheckIntersectionTest() {
        task2.setDuration(Duration.ofHours(5));
        epic1 = new Epic("Epic2", "2", taskManager.getNewId(), TaskStatus.NEW, TaskType.EPIC);

        subtask1 = new Subtask("Subtask1", "1", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, LocalDateTime.of(2022, 11, 29, 18, 5),
                Duration.ofHours(4), epic1.getId());

        epic1.addSubtask(subtask1);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);

        TreeSet<Task> result = taskManager.getPrioritizedTasks();
        Iterator<Task> it = result.iterator();
        assertEquals(task1, it.next(), "Задачи пересекаются");
        assertEquals(subtask1, it.next(), "Задачи пересекаются");
        assertEquals(task3, it.next(), "Задачи пересекаются");
    }
}