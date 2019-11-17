package com.internship.service.interfaces;

import com.internship.model.entity.Task;

public interface ITaskService extends IService<Task> {
    Integer getSize(Integer userId);
}
