package com.internship.dao.interfaces;

import java.util.List;

public interface IDao<T> {
    void update(T type);

    List<T> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter);

    T add(T type);

    T get(Integer id);

    boolean delete(Integer id);
}

