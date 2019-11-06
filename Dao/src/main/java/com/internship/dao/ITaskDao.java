package com.internship.dao;

import com.internship.model.Task;

import java.util.Collection;
import java.util.List;

public interface ITaskDao extends IDao<Task> {
    Integer getSize(Integer userId);
    Collection<Task> getAll(Integer userId);
}
