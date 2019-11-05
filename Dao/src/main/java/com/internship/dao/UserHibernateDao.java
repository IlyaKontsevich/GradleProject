package com.internship.dao;

import com.internship.model.User;
import com.internship.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class UserHibernateDao implements IUserDao {

    @Override
    public Collection<User> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);

        Function<List<String>, Order[]> mappingSortToOrder = list -> list
                    .stream()
                    .map((str) -> {
                        String[] parts = str.split(":");
                        if (parts[1].equals("asc"))
                            return cb.asc(root.get(parts[0]));
                        return cb.desc(root.get(parts[0]));
                    })
                    .toArray(Order[]::new);
        Function<List<String>, Predicate[]> mappingFilterToPredicates = list -> list
                .stream()
                .map((str) -> {
                    String[] parts = str.split(":");
                    return cb.equal(root.get(parts[0]), parts[1]);
                })
                .toArray(Predicate[]::new);

        cr.select(root).where(mappingFilterToPredicates.apply(filter));
        cr.orderBy(mappingSortToOrder.apply(sortType));
        Collection<User> users = session.createQuery(cr).setFirstResult(position).setMaxResults(pageSize).getResultList();
        session.close();
        return users;
    }

    @Override
    public Integer getSize() {
        return getAll().size();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    @Override
    public User add(User user) {
        List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From User WHERE email ='" + user.getEmail() + "'").list();
        if (users.size() != 0) {
            return null;
        }
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
        users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From User WHERE email ='" + user.getEmail() + "'").list();
        return users.get(0);
    }

    @Override
    public User get(Integer id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public Collection<User> getAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root);
        Collection<User> users = session.createQuery(cr).getResultList();
        session.close();
        return users;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = (List<User>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From User WHERE email ='" + email + "'").list();
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean delete(Integer id) {
        if (get(id) == null)
            return false;
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(get(id));
        tx1.commit();
        session.close();
        return true;
    }
}
