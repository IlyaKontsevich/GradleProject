package com.internship.service.interfaces;

import com.internship.model.entity.Task;

import java.util.List;

public interface ITaskService extends IService<Task> {
    List<Task> getPage(Integer position, Integer pageSize, Integer userId, List<String> sortType, List<String> filter);
    Integer getSize(Integer userId);
}
