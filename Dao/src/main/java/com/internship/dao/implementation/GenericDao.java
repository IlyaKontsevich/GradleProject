package com.internship.dao.implementation;

import com.internship.dao.interfaces.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static com.internship.utils.UtilsForDao.mapFilterToPredicates;
import static com.internship.utils.UtilsForDao.mapSortToOrder;

public abstract class GenericDao<ENTITY> implements IDao<ENTITY> {

   @Autowired
    private SessionFactory sessionFactory;

    private Class<ENTITY> entityType = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public void update(ENTITY type) {
        sessionFactory.getCurrentSession().update(type);
    }

    public List<ENTITY> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        EntityManager session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ENTITY> criteriaQuery = cb.createQuery(entityType);
        Root<ENTITY> root = criteriaQuery.from(entityType);

        criteriaQuery
                .select(root)
                .where(mapFilterToPredicates(filter, root, cb))
                .orderBy(mapSortToOrder(sortType, root, cb));
        List<ENTITY> page = session
                .createQuery(criteriaQuery)
                .setFirstResult(position)
                .setMaxResults(pageSize)
                .getResultList();
        session.close();
        return page;
    }

    public ENTITY add(ENTITY entity) {
        getSession().save(entity);
        return entity;
    }

    public ENTITY get(Integer id) {
        return getSession().get(entityType, id);
    }

    public boolean delete(Integer id) {
        ENTITY entity = get(id);
        if (entity != null) {
            sessionFactory.getCurrentSession().delete(entity);
            return true;
        }
        return false;
    }

    Session getSession() {
        return sessionFactory.openSession();
    }
}
