package org.yandex.praktikum.taskmanager.manager;

import org.junit.jupiter.api.Test;
import org.yandex.praktikum.taskmanager.task.Task;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    @Override
    public void createNewManager() {
        taskManager = new FileBackedTaskManager();
    }

    @Test
    public void save_EmptyTaskManagerTest() {
        taskManager.save();
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(new File(FileBackedTaskManager.savePath));
        assertTrue(loadedManager.getAllTasks().isEmpty());
        assertTrue(loadedManager.getAllEpics().isEmpty());
        assertTrue(loadedManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void saveAndLoad_CasualTaskManagerTest() {
        taskManager.addTask(task1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(new File(FileBackedTaskManager.savePath));

        assertEquals(task1, loadedManager.getTaskById(task1.getId()));
        assertEquals(epic2, loadedManager.getEpicById(epic2.getId()));
        assertEquals(subtask3, loadedManager.getSubtaskById(subtask3.getId()));
    }

    @Test
    public void saveAndLoad_HistoryTaskManagerTest() {
        taskManager.addTask(task1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);

        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getSubtaskById(subtask3.getId());

        taskManager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(new File(FileBackedTaskManager.savePath));

        List<Task> history = loadedManager.getHistory();

        assertEquals(task1, history.get(0));
        assertEquals(epic2, history.get(1));
        assertEquals(subtask3, history.get(2));
    }
}