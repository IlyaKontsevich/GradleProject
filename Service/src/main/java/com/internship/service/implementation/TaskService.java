package com.internship.service.implementation;

import com.internship.dao.interfaces.ITaskDao;
import com.internship.model.PageRequest;
import com.internship.model.entity.Task;
import com.internship.service.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService extends GenericService<Task> implements ITaskService {
    @Autowired
    private ITaskDao dao;


    @Override
    public Task add(Task task) {
        int userId = task.getUser().getId();
        String taskName = task.getName();
        if (dao.getTaskByUserIdAndName(userId, taskName) != null)
            return null;
        return super.add(task);
    }

    @Override
    public Integer getSize(Integer userId) {
        List<String> filter = new ArrayList<>();
        filter.add("user:" + userId);
        return super.getPage(PageRequest
                .builder()
                .filter(filter)
                .sort(new ArrayList<>())
                .pageSize(1000)
                .position(0)
                .build())
                .size();
    }

}
