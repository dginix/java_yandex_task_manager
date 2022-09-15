package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.historymanager.HistoryManager;
import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int idCount = 0;

    private final HashMap <Integer, Task> taskMap = new HashMap<>();
    private final HashMap <Integer, Epic> epicMap = new HashMap<>();
    private final HashMap <Integer, Subtask> subtaskMap = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();

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
        historyManager.add(result);
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
        historyManager.remove(id);
    }

    @Override
    public void deleteAllTask() {
        System.out.println("Удаление всех задач");
        taskMap.clear();
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
        historyManager.add(result);
        return result;
    }

    @Override
    public void deleteEpicById(int id) {
        //TODO при удалении эпика очищает все подзадачи
        Epic deletedEpic = epicMap.get(id);
        ArrayList<Subtask> deletedSubtasks = deletedEpic.getSubtaskList();

        for(Subtask subtask : deletedSubtasks){
            historyManager.remove(subtask.getId());
            subtaskMap.remove(subtask.getId());
        }

        historyManager.remove(id);
        epicMap.remove(id);
    }

    @Override
    public void deleteAllEpic() {
        epicMap.clear();
        subtaskMap.clear();
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
        subtaskMap.put(subtask.getId(), subtask);
        subtask.getEpicOwned().updateStatus();
    }

    @Override
    public Subtask getSubtaskById(int id){
        Subtask result = subtaskMap.get(id);
        System.out.println(result);
        historyManager.add(result);
        return result;
    }

    @Override
    public void deleteSubtaskById(int id){
        Subtask deletedSubtask = subtaskMap.get(id);
        Epic fromEpic = deletedSubtask.getEpicOwned();
        fromEpic.getSubtaskList().remove(id);
        subtaskMap.remove(id);
        fromEpic.updateStatus();
    }

    /**
     * Выводит список всех задач, подзадач и эпиков
     */
    @Override
    public void getAll() {
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
    public void deleteAll() {
        System.out.println("Удаление всех задач");
        taskMap.clear();
        subtaskMap.clear();
        epicMap.clear();
    }

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }
}
