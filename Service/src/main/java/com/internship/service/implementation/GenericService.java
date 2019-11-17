package com.internship.service.implementation;

import com.internship.dao.implementation.GenericDao;
import com.internship.dao.interfaces.IDao;
import com.internship.model.PageRequest;
import com.internship.service.interfaces.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional(propagation= Propagation.REQUIRED,readOnly = false)
public abstract class GenericService<ENTITY> implements IService<ENTITY> {
    private static final Logger log = Logger.getLogger(GenericDao.class);
    private Class<ENTITY> entityType = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    @Autowired
    private IDao<ENTITY> dao;

    @Override
    public void update(ENTITY entity) {
        log.info("Update " + entityType);
        dao.update(entity);
    }

    @Override
    public ENTITY add(ENTITY entity) {
        log.info("Add " + entityType);
        return dao.add(entity);
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Delete " + entityType + "Id: " + id);
        return dao.delete(id);
    }

    @Override
    public ENTITY get(Integer id) {
        log.info("Get " + entityType + "Id: " + id);
        return dao.get(id);
    }

    @Override
    public List<ENTITY> getPage(PageRequest pageRequest) {
        log.info("Get Page " + entityType);
        return dao.getPage(pageRequest);
    }
}
