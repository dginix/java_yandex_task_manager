package org.yandex.praktikum.taskmanager.task;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{
    final private ArrayList<Subtask> subtaskList = new ArrayList<>();

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

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    /**
     * Обновляет статус всех подзадач внутри
     */
    public void updateStatus() {
        HashMap<TaskStatus, Integer> statusCount = new HashMap<>();
        statusCount.put(TaskStatus.NEW, 0);
        statusCount.put(TaskStatus.IN_PROGRESS, 0);
        statusCount.put(TaskStatus.DONE, 0);

        for (Subtask innerSubtask : subtaskList) {
            switch (innerSubtask.getStatus()) {
                case NEW -> statusCount.put(TaskStatus.NEW, statusCount.get(TaskStatus.NEW) + 1);
                case DONE -> statusCount.put(TaskStatus.DONE, statusCount.get(TaskStatus.DONE) + 1);
                case IN_PROGRESS -> statusCount.put(TaskStatus.IN_PROGRESS,
                        statusCount.get(TaskStatus.IN_PROGRESS) + 1);
            }
        }

        if (statusCount.get(TaskStatus.DONE) == subtaskList.size()) {
            this.status = TaskStatus.DONE;
        }
        else if (statusCount.get(TaskStatus.NEW) == subtaskList.size()) {
            this.status = TaskStatus.NEW;
        }
        else {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }
}
