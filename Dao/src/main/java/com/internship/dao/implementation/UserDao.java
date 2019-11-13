package com.internship.dao.implementation;

import com.internship.dao.interfaces.IUserDao;
import com.internship.model.User;
import com.internship.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

import static com.internship.utils.UtilsForDao.mapFilterToPredicates;
import static com.internship.utils.UtilsForDao.mapSortToOrder;

@Component
public class UserDao implements IUserDao {

    @Override
    public List<User> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery
                .select(root)
                .where(mapFilterToPredicates(filter, root, cb))
                .orderBy(mapSortToOrder(sortType, root, cb));
        List<User> users = session
                                        .createQuery(criteriaQuery)
                                        .setFirstResult(position)
                                        .setMaxResults(pageSize)
                                        .getResultList();
        session.close();
        return users;
    }

    @Override
    public void update(User user) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
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
        return Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession().get(User.class, id);
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
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(get(id));
        tx1.commit();
        session.close();
        return true;
    }
}
