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
    private int idCount;

    protected final HashMap <Integer, Task> taskMap;
    protected final HashMap <Integer, Epic> epicMap;
    protected final HashMap <Integer, Subtask> subtaskMap;
    protected final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.idCount = 0;
        this.historyManager = Managers.getDefaultHistory();
        this.subtaskMap = new HashMap<>();
        this.epicMap = new HashMap<>();
        this.taskMap = new HashMap<>();
    }

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
        taskMap.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteAllTask() {
        for(Integer id : taskMap.keySet()){
            deleteTaskById(id);
        }
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
        Epic deletedEpic = epicMap.get(id);
        ArrayList<Subtask> deletedSubtasks = deletedEpic.getSubtaskList();

        if(deletedSubtasks != null){
            for(Subtask subtask : deletedSubtasks){
                historyManager.remove(subtask.getId());
                subtaskMap.remove(subtask.getId());
            }
        }

        historyManager.remove(id);
        epicMap.remove(id);
    }

    @Override
    public void deleteAllEpic() {
        for(Integer id : epicMap.keySet()){
            deleteEpicById(id);
        }
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
        epicMap.get(subtask.getEpicId()).updateStatus();
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
        Epic fromEpic = epicMap.get(deletedSubtask.getEpicId());
        fromEpic.getSubtaskList().remove(deletedSubtask);

        historyManager.remove(id);
        subtaskMap.remove(id);

        fromEpic.updateStatus();
    }

    /**
     * Выводит список всех задач, подзадач и эпиков
     */
    @Override
    public void getAll() {
        if (!taskMap.isEmpty()) {
            System.out.println("Список всех задач:");
            for(Map.Entry<Integer, Task> entry : taskMap.entrySet()){
                System.out.println(entry.getValue());
            }
        }

        if (!epicMap.isEmpty()) {
            System.out.println("Список всех эпиков:");
            for(Map.Entry<Integer, Epic> entry : epicMap.entrySet()){
                System.out.println(entry.getValue());
            }
        }

        if (!subtaskMap.isEmpty()) {
            System.out.println("Список всех подазадач:");
            for(Map.Entry<Integer, Subtask> entry : subtaskMap.entrySet()){
                System.out.println(entry.getValue());
            }
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
