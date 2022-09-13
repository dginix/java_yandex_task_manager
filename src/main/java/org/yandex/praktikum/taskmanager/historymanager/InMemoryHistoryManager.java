package org.yandex.praktikum.taskmanager.historymanager;

import org.yandex.praktikum.taskmanager.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    final List<Task> historyList = new ArrayList<>();
    @Override
    public void add(Task task){
        if(historyList.size() >= 10){
            historyList.remove(0);
            historyList.add(task);
        }
        else{
            historyList.add(task);
        }
    }

    @Override
    public void remove(int id){
        historyList.remove(id);
    }

    /**
     *
     * @return последние вызванные 10 задач
     */
    @Override
    public List<Task> getHistory(){
        return historyList;
    }
}
