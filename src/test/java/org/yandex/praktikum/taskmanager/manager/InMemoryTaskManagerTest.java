package org.yandex.praktikum.taskmanager.manager;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    public void createNewManager() {
        taskManager = new InMemoryTaskManager();
    }
}