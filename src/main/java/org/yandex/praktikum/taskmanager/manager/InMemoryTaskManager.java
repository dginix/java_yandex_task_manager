package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;
import org.yandex.praktikum.taskmanager.task.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int idCount = 0;

    private final HashMap <Integer, Task> taskMap = new HashMap<>();
    private final HashMap <Integer, Epic> epicMap = new HashMap<>();
    private final HashMap <Integer, Subtask> subtaskMap = new HashMap<>();
    private final ArrayList<Task> historyList = new ArrayList<>();
    @Override
    public int getNewId(){
        return ++idCount;
    }
    @Override
    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }
    @Override
    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    /**
     * Возвращает задачу по идентификатору и выводит ее в консоль
     * @param id идентификатор задач
     * @return объект задачи из сохраняемого списка задач
     */
    @Override
    public Task getTaskById(int id) {
        Task result = taskMap.get(id);
        System.out.println(result);
        addToHistory(result);
        return result;
    }

    /**
     * Удаление задачи из списка всех задач
     * @param id идентификатор задачи
     */
    @Override
    public void deleteTaskById(int id){
        System.out.println("Удалена задача:\n" + taskMap.get(id));
        taskMap.remove(id);
    }
    @Override
    public void addEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
    }
    @Override
    public void updateEpic(Epic epic){
        epicMap.put(epic.getId(), epic);
    }
    @Override
    public Epic getEpicById(int id){
        Epic result = epicMap.get(id);
        System.out.println(result);
        addToHistory(result);
        return result;
    }
    @Override
    public void addSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
    }

    /**
     * Обновляет подзадачу и проверяет на завершенность эпика, которому эта подзадача принадлежит
     */
    @Override
    public void updateSubtask(Subtask subtask) {
        boolean changeStatus = true;
        for (Subtask subtaskInEpic : subtask.getEpicOwned().getSubtaskList()) {
            if (subtaskInEpic.getStatus() != TaskStatus.DONE) {
                changeStatus = false;
                break;
            }
        }

        if (changeStatus) {
            subtask.getEpicOwned().setStatus(TaskStatus.DONE);
        }
        else {
            subtask.getEpicOwned().setStatus(TaskStatus.IN_PROGRESS);
        }

        subtaskMap.put(subtask.getId(), subtask);
    }
    @Override
    public Subtask getSubtaskById(int id){
        Subtask result = subtaskMap.get(id);
        System.out.println(result);
        addToHistory(result);
        return result;
    }

    /**
     * Выводит список всех задач, подзадач и эпиков
     */
    @Override
    public void getAllTasks() {
        System.out.println("Список всех задач:");
        for(Map.Entry<Integer, Task> entry : taskMap.entrySet()){
            System.out.println(entry.getValue());
        }
        System.out.println("Список всех эпиков:");
        for(Map.Entry<Integer, Epic> entry : epicMap.entrySet()){
            System.out.println(entry.getValue());
        }
        System.out.println("Список всех подазадач:");
        for(Map.Entry<Integer, Subtask> entry : subtaskMap.entrySet()){
            System.out.println(entry.getValue());
        }
    }

    /**
     * Очищает все списки задач, подзадач и эпиков
     */
    @Override
    public void deleteAllTasks() {
        System.out.println("Удаление всех задач");
        taskMap.clear();
        subtaskMap.clear();
        epicMap.clear();
    }

    @Override
    public void addToHistory(Task task){
        if(historyList.size() >= 10){
            historyList.remove(0);
            historyList.add(task);
        }
        else{
            historyList.add(task);
        }
    }

    /**
     *
     * @return последние вызванные 10 задач
     */
    @Override
    public ArrayList<Task> getHistory(){
        return historyList;
    }
}
