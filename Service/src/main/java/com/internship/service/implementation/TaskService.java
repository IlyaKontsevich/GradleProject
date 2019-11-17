package com.internship.service.implementation;

import com.internship.dao.interfaces.ITaskDao;
import com.internship.model.PageRequest;
import com.internship.model.entity.Task;
import com.internship.service.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
public class TaskService extends GenericService<Task> implements ITaskService {
    @Autowired
    private ITaskDao dao;


    @Override
    public Task add(Task task) {
        int userId = task.getUser().getId();
        String taskName = task.getName();
        if (dao.getTaskByUserIdAndName(userId, taskName) != null) {
            throw new RuntimeException(String.format("Task %s exists for %s user", taskName, task.getUser().getName()));
        }
        return super.add(task);
    }

    @Override
    public Integer getSize(Integer userId) {
        List<String> filter = new ArrayList<>();
        filter.add("user:" + userId);
        return super.getPage(
                new PageRequest(filter, new ArrayList<>(), 0, 1000)).size();
    }

}
