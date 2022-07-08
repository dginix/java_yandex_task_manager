package org.yandex.praktikum.taskmanager.task;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Subtask> subtaskList = new ArrayList<>();

    public Epic(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
    }

    public void addSubtask(Subtask subtask){
        subtaskList.add(subtask);
    }

    public void addSubtaskMany(ArrayList<Subtask> subtasks){
        subtaskList.addAll(subtasks);
    }

    public ArrayList<Subtask> getSubtaskList() {
        return subtaskList;
    }
}
