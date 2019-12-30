package com.internship.service.interfaces;

import com.internship.model.PageRequest;

import java.util.List;

public interface IService<ENTITY> {
    void update(ENTITY type);
    ENTITY add(ENTITY type);
    boolean delete(Integer id);
    ENTITY get(Integer id);
    List<ENTITY> getPage(PageRequest pageRequest);
    List<ENTITY> getAll();
}
