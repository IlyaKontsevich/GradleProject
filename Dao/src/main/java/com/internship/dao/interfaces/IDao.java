package com.internship.dao.interfaces;

import com.internship.model.PageRequest;

import java.util.List;

public interface IDao<T> {
    void update(T type);

    List<T> getPage(PageRequest pageRequest);

    T add(T type);

    T get(Integer id);

    boolean delete(Integer id);
}

