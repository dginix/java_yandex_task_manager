package org.yandex.praktikum.taskmanager.historymanager;

import org.yandex.praktikum.taskmanager.models.TaskNode;
import org.yandex.praktikum.taskmanager.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    final Map<Integer, TaskNode<Task>> historyListFinder= new HashMap<>();

    public TaskNode<Task> historyListHead;
    public TaskNode<Task> historyListTail;
    protected int historyListSize;

    public InMemoryHistoryManager() {
        this.historyListSize = 0;
        historyListHead = null;
        historyListTail = null;
    }

    /**
     * Добавляет задачу в историю просмотра задач
     * @param task вызываемая задача
     */
    @Override
    public void add(Task task) {
        // TODO не забыть добавить ограничение на 10 задач
        if (historyListFinder.containsKey(task.getId())) {
            remove(task.getId());
        }
        TaskNode<Task> addedTask = linkLast(task);
        historyListFinder.put(task.getId(), addedTask);
    }

    /**
     * Удалаяет узел списка истории и списка пары ключ/место в истории
     * @param id задачи для удаления из списка
     */
    @Override
    public void remove(int id) {
        if (historyListFinder.containsKey(id) && historyListFinder.get(id) != null) {
            TaskNode<Task> removableNode = historyListFinder.get(id);

            TaskNode<Task> prevNode = removableNode.getPrev();
            TaskNode<Task> nextNode = removableNode.getNext();

            if (prevNode == null && nextNode == null) {
                historyListHead = null;
                historyListTail = null;
            }
            else if (prevNode == null) {
                nextNode.prev = null;
                historyListHead = nextNode;
            }
            else if (nextNode == null) {
                prevNode.next = null;
                historyListTail = prevNode;
            }
            else {
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }

            historyListSize--;
            historyListFinder.remove(id);
        }
    }

    /**
     * Выводит историю просмотра задач
     * @return список задач, расположеных в порядке их просмотра
     */
    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> resultList = new ArrayList<>();
        TaskNode<Task> currentNode = historyListHead;
        while(currentNode != null){
            resultList.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return resultList;
    }

    /**
     * Добавляет задачу в двусвязанный список
     * @param task добавляемая в конец списка задача
     */
    public TaskNode<Task> linkLast(Task task) {
        TaskNode<Task> newTailTask = new TaskNode<>(historyListTail, task, null);

        if (historyListTail == null){
            historyListHead = newTailTask;
            historyListTail = newTailTask;
        }
        else {
            historyListTail = newTailTask;
            historyListTail.getPrev().setNext(historyListTail);
        }
        historyListSize++;
        return newTailTask;
    }

    @Override
    public boolean isEmpty() {
        return historyListFinder.isEmpty();
    }
}
