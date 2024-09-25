package gm.task.service;

import gm.task.model.Task;
import gm.task.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService{

    @Autowired
    private ITaskRepository repository;

    @Override
    public List<Task> listTask() {
        return repository.findAll();
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = repository.findById(id).orElse(null);
        return task;
    }

    @Override
    public void saveTask(Task task) {
        repository.save(task);
    }

    @Override
    public void deleteTask(Task task) {
        repository.delete(task);
    }
}
