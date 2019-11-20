package com.internship.service.implementation;

import com.internship.dao.interfaces.IDao;
import com.internship.model.PageRequest;
import com.internship.service.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional(propagation= Propagation.REQUIRED)
public abstract class GenericService<ENTITY> implements IService<ENTITY> {
    private Class<ENTITY> entityType = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    @Autowired
    private IDao<ENTITY> dao;

    @Override
    public void update(ENTITY entity) {
        dao.update(entity);
    }

    @Override
    public ENTITY add(ENTITY entity) {
        return dao.add(entity);
    }

    @Override
    public boolean delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    public ENTITY get(Integer id) {
        return dao.get(id);
    }

    @Override
    public List<ENTITY> getPage(PageRequest pageRequest) {
        return dao.getPage(pageRequest);
    }
}
