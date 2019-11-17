package com.internship.dao.implementation;

import com.internship.dao.interfaces.ITaskDao;
import com.internship.model.entity.Task;
import org.springframework.stereotype.Component;

import static org.hibernate.criterion.Restrictions.eq;

@Component
public class TaskDao extends GenericDao<Task> implements ITaskDao {

    @Override
    @SuppressWarnings("unchecked")
    public Task getTaskByUserIdAndName(Integer userId, String name) {
        return (Task) getSession()
                .createCriteria(Task.class)
                .add(eq("user.id", userId))
                .add(eq("name", name))
                .uniqueResult();
    }
}
