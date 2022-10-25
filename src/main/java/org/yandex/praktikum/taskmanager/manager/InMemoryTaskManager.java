package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.historymanager.HistoryManager;
import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;

import java.util.*;

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
        if(task != null) {
            taskMap.put(task.getId(), task);
        }
    }

    @Override
    public void updateTask(Task task) {
        if(task != null && taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
        }
    }

    /**
     * Возвращает задачу по идентификатору и выводит ее в консоль
     * @param id идентификатор задач
     * @return объект задачи из сохраняемого списка задач
     */
    public Task getTaskById(int id) {
        Task result = taskMap.get(id);
        historyManager.add(result);
        return result;
    }

    /**
     * Возвращает список всех задач в менеджере
     * @return список задач
     */
    @Override
    public List<Task> getAllTasks() {
        List<Task> result = new ArrayList<>();
        if (!taskMap.isEmpty()) {
            for(Map.Entry<Integer, Task> entry : taskMap.entrySet()){
                result.add(entry.getValue());
            }
        }
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
        List<Task> tasks = getAllTasks();
        for (Task task : tasks) {
            deleteTaskById(task.getId());
        }
    }


    @Override
    public void addEpic(Epic epic) {
        if(epic != null) {
            epicMap.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if(epic != null && epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
        }
    }

    @Override
    public Epic getEpicById(int id){
        Epic result = epicMap.get(id);
        historyManager.add(result);
        return result;
    }

    /**
     * Возвращает список всех эпиков в менеджере
     * @return список эпиков
     */
    @Override
    public List<Epic> getAllEpics() {
        List<Epic> result = new ArrayList<>();
        if (!epicMap.isEmpty()) {
            for(Map.Entry<Integer, Epic> entry : epicMap.entrySet()){
                result.add(entry.getValue());
            }
        }
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
        List<Epic> epics = getAllEpics();
        for (Epic epic : epics) {
            deleteEpicById(epic.getId());
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if(subtask != null) {
            subtaskMap.put(subtask.getId(), subtask);
            epicMap.get(subtask.getEpicId()).addSubtask(subtask);
            epicMap.get(subtask.getEpicId()).updateStatus();
        }
    }

    /**
     * Обновляет подзадачу и проверяет на завершенность эпика, которому эта подзадача принадлежит
     */
    @Override
    public void updateSubtask(Subtask subtask) {
        if(subtask != null && subtaskMap.containsKey(subtask.getId())) {
            subtaskMap.put(subtask.getId(), subtask);
            epicMap.get(subtask.getEpicId()).updateStatus();
        }
    }

    @Override
    public Subtask getSubtaskById(int id){
        Subtask result = subtaskMap.get(id);
        historyManager.add(result);
        return result;
    }

    /**
     * Возвращает список всех подзадач в менеджере
     * @return список подзадач
     */
    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> result = new ArrayList<>();
        if (!subtaskMap.isEmpty()) {
            for(Map.Entry<Integer, Subtask> entry : subtaskMap.entrySet()){
                result.add(entry.getValue());
            }
        }
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
