package com.internship.service.implementation;

import com.internship.dao.interfaces.ITaskDao;
import com.internship.model.Task;
import com.internship.service.interfaces.ITaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.internship.service.utils.UtilsForServices.changePosition;

@Service
public class TaskService implements ITaskService {
    @Autowired
    @Qualifier("taskDao")
    private ITaskDao dao;
    private static final Logger log = Logger.getLogger(TaskService.class);

    @Override
    public List<Task> getPage(Integer position, Integer pageSize, Integer userId, List<String> sortType, List<String> filter) {
        log.info("Get task page");
        filter.add("user:" + userId);
        position = changePosition(position, pageSize);
        return dao.getPage(position, pageSize, sortType, filter);
    }

    @Override
    public void update(Task task) {
        log.info("Update task");
        dao.update(task);
    }

    @Override
    public Task add(Task task) {
        log.info("Add task");
        return dao.add(task);
    }

    @Override
    public void delete(Integer id) {
        log.info("Delete task");
        dao.delete(id);
    }

    @Override
    public Task get(Integer id) {
        log.info("Get task");
        return dao.get(id);
    }


    @Override
    public Integer getSize(Integer userId) {
        List<String> filter = new ArrayList<>();
        filter.add("user:" + userId);
        return dao.getPage(0, 1000, new ArrayList<>(), filter).size();
    }


}
