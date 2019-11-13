package com.internship.dao.implementation;

import com.internship.dao.interfaces.ITaskDao;
import com.internship.model.Task;
import com.internship.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

import static com.internship.utils.UtilsForDao.mapSortToOrder;
import static com.internship.utils.UtilsForDao.mapTaskFilterToPredicates;

@Component
public class TaskDao implements ITaskDao {

    @Override
    public List<Task> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = cb.createQuery(Task.class);
        Root<Task> root = criteriaQuery.from(Task.class);

        criteriaQuery
                .select(root)
                .where(mapTaskFilterToPredicates(filter, root, cb))
                .orderBy(mapSortToOrder(sortType, root, cb));
        List<Task> tasks = session
                                        .createQuery(criteriaQuery)
                                        .setFirstResult(position)
                                        .setMaxResults(pageSize)
                                        .getResultList();
        session.close();
        return tasks;
    }

    @Override
    public void update(Task task) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(task);
        tx1.commit();
        session.close();
    }

    @Override
    public Task add(Task task) {
        List<Task> tasks = (List<Task>)  HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Task WHERE userid = '"+task.getUser().getId()+"'AND name ='"+task.getName()+"'").list();
        if(tasks.size()!=0){
            return null;
        }
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(task);
        tx1.commit();
        session.close();
        tasks = (List<Task>)  HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Task WHERE userid = '"+task.getUser().getId()+"'AND name ='"+task.getName()+"'").list();
        return tasks.get(0);
    }

    @Override
    public Task get(Integer id) {
        return Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession().get(Task.class, id);
    }

    @Override
    public boolean delete(Integer id) {
        if(get(id)==null){
            return false;
        }
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(get(id));
        tx1.commit();
        session.close();
        return true;
    }
}
