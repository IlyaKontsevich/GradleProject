package com.internship.dao;

import java.util.Collection;
import java.util.List;

public interface IDao<T> {
    void update(T type);

    Collection<T> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filters);

    T add(T type);

    T get(Integer id);

    boolean delete(Integer id);
}

