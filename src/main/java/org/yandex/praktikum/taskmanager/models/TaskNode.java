package org.yandex.praktikum.taskmanager.models;

import org.yandex.praktikum.taskmanager.task.Task;

public class TaskNode<T extends Task> {
    public T data;
    public TaskNode<T> next;
    public TaskNode<T> prev;

    public TaskNode(T current, TaskNode<T> next, TaskNode<T> prev) {
        this.data = current;
        this.next = next;
        this.prev = prev;
    }
}
