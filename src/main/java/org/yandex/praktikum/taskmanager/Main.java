package org.yandex.praktikum.taskmanager;

import org.yandex.praktikum.taskmanager.manager.FileBackedTaskManager;
import org.yandex.praktikum.taskmanager.manager.Managers;
import org.yandex.praktikum.taskmanager.manager.TaskManager;
import org.yandex.praktikum.taskmanager.task.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        testLoadFile();
    }

    public static void testLoadFile() {

        File file = new File("/home/kruchinin/IdeaProjects/java_yandex_task_manager/src/main/resources" +
                "/state_saver/data.csv");

        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(file);

        manager.getAll();
        System.out.println(manager.getHistory());
    }
}
