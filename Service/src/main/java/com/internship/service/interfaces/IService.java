package com.internship.service.interfaces;

public interface IService<T> {
    void update(T type);
    T add(T type);
    boolean delete(Integer id);
    T get(Integer id);
}
