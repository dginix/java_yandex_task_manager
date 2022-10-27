package org.yandex.praktikum.taskmanager.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{
    final private ArrayList<Subtask> subtaskList = new ArrayList<>();

    public Epic(String name, String description, int id, TaskStatus status, TaskType type) {
        super(name, description, id, status, type);
    }

    public Epic(String name, String description, int id, TaskStatus status, TaskType type, LocalDateTime startTime,
                Duration duration) {
        super(name, description, id, status, type, startTime, duration);
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

    //TODO стоит расчитывать время и продолжительность при добавлении подзадачи
    @Override
    public LocalDateTime getStartTime() {
        if (subtaskList.isEmpty()) {
            return null;
        }

        LocalDateTime findStartTime = LocalDateTime.MAX;
        for (Subtask subtask : subtaskList) {
            if (subtask.getStartTime() == null) {
                continue;
            }

            if (findStartTime.isAfter(subtask.getStartTime())) {
                findStartTime = subtask.getStartTime();
            }
        }
        return findStartTime;
    }

    @Override
    public Duration getDuration() {
        if (subtaskList.isEmpty()) {
            return null;
        }

        Duration duration = Duration.ZERO;

        for (Subtask subtask : subtaskList) {
            if (subtask.getDuration() == null) {
                continue;
            }
            duration = duration.plus(subtask.getDuration());
        }
        return duration;
    }

    @Override
    public LocalDateTime getEndTime() {
        if (subtaskList.isEmpty()) {
            return null;
        }
        LocalDateTime findEndTime = LocalDateTime.MIN;

        for (Subtask subtask : subtaskList) {
            if (subtask.getEndTime() == null) {
                continue;
            }

            if (findEndTime.isBefore(subtask.getEndTime())) {
                findEndTime = subtask.getEndTime();
            }
        }
        return findEndTime;
    }

    @Override
    public String toString() {
        return super.toString();
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
            this.setStatus(TaskStatus.DONE);
        }
        else if (statusCount.get(TaskStatus.NEW) == subtaskList.size()) {
            this.setStatus(TaskStatus.NEW);
        }
        else {
            this.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
