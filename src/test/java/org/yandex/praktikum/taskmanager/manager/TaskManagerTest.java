package org.yandex.praktikum.taskmanager.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.task.*;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    public T taskManager;

    public Task task1;
    public Task task2;

    public Epic epic1;
    public Epic epic2;

    public Subtask subtask1;
    public Subtask subtask2;

    abstract public void createNewManager();

    @BeforeEach
    public void beforeEach() {
        createNewManager();

        task1 = new Task("Task1", "1", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);
        task2 = new Task("Task2", "2", taskManager.getNewId(), TaskStatus.NEW, TaskType.TASK);

        epic1 = new Epic("Epic1", "1", taskManager.getNewId(), TaskStatus.NEW, TaskType.EPIC);
        epic2 = new Epic("Epic2", "2", taskManager.getNewId(), TaskStatus.NEW, TaskType.EPIC);

        subtask1 = new Subtask("Subtask1", "1", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());
        subtask2 = new Subtask("Subtask2", "2", taskManager.getNewId(), TaskStatus.NEW,
                TaskType.SUBTASK, epic1.getId());
    }

    @Test
    public void getIdTest() {
        int count = taskManager.getNewId();
        assertEquals(count+1, taskManager.getNewId(), "Менеджер не сгенерировал id");
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


//    void addEpic(Epic epic);
//    void updateEpic(Epic epic);
//    Epic getEpicById(int id);
//    void deleteEpicById(int id);
//    void deleteAllEpic();
//
//    void addSubtask(Subtask subtask);
//    void updateSubtask(Subtask subtask);
//    Subtask getSubtaskById(int id);
//    void deleteSubtaskById(int id);


}