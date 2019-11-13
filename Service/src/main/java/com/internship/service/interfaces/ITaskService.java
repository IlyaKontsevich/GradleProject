package com.internship.service.interfaces;

import com.internship.model.Task;
import com.internship.service.interfaces.IService;

import java.util.Collection;
import java.util.List;

public interface ITaskService extends IService<Task> {
    List<Task> getPage(Integer position, Integer pageSize, Integer userId, List<String> sortType, List<String> filter);
    Integer getSize(Integer userId);
}
