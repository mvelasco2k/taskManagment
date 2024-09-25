package gm.task.service;

import gm.task.model.Task;

import java.util.List;

public interface ITaskService {
    public List<Task> listTask();
    public Task getTaskById(Integer id);
    public void saveTask(Task task);
    public void deleteTask(Task task);
}
