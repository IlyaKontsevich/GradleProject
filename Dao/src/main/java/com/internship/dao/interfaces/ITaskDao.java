package com.internship.dao.interfaces;

import com.internship.model.entity.Task;

public interface ITaskDao extends IDao<Task> {
    Task getTaskByUserIdAndName(Integer userId, String name);
}
