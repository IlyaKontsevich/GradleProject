package com.internship.dao;

import com.internship.model.Task;
import com.internship.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class TaskHibernateDao implements ITaskDao {

    @Override
    public Collection<Task> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Task> cr = cb.createQuery(Task.class);
        Root<Task> root = cr.from(Task.class);

        Function<List<String>, Order[]> mappingSortToOrder = list -> list
                .stream()
                .map((str) -> {
                    String[] parts = str.split(":");
                    if (parts[1].equals("asc"))
                        return cb.asc(root.get(parts[0]));
                    return cb.desc(root.get(parts[0]));
                })
                .toArray(Order[]::new);
        cr.orderBy(mappingSortToOrder.apply(sortType));

        Function<List<String>, Predicate[]> mappingFilterToPredicates = list -> list
                .stream()
                .map((str) -> {
                    String[] parts = str.split(":");
                    switch (parts[0]) {
                        case "isdone":
                            return cb.equal(root.get(parts[0]), Boolean.parseBoolean(parts[1]));
                        case "timeadd":
                        case "deadline":
                            return cb.equal(root.get(parts[0]), LocalDate.parse(parts[1]));
                        case "user":
                            return cb.equal(root.get("user"), Integer.valueOf(parts[1]));
                        default:
                            return cb.equal(root.get(parts[0]), parts[1]);
                    }
                })
                .toArray(Predicate[]::new);

        Predicate[] predicates = mappingFilterToPredicates.apply(filter);
        cr.select(root).where(predicates);
        Collection<Task> tasks = session.createQuery(cr).setFirstResult(position).setMaxResults(pageSize).getResultList();
        session.close();
        return tasks;
    }

    @Override
    public Integer getSize(Integer userId) {
        return getAll(userId).size();
    }

    @Override
    public void update(Task task) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
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
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Task.class, id);
    }

    @Override
    public Collection<Task> getAll(Integer userId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Task> cr = cb.createQuery(Task.class);
        Root<Task> root = cr.from(Task.class);
        cr.select(root).where(cb.equal(root.get("user"),userId));
        Collection<Task> tasks = session.createQuery(cr).getResultList();
        session.close();
        return tasks;
    }

    @Override
    public boolean delete(Integer id) {
        if(get(id)==null){
            return false;
        }
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(get(id));
        tx1.commit();
        session.close();
        return true;
    }
}
