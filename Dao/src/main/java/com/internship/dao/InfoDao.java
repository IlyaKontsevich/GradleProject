package com.internship.dao;

import com.internship.model.Info;

import com.internship.model.User;
import com.internship.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Objects;

@Component
public class InfoDao implements IInfoDao {

    private Info createIfNotExists(Integer userId) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Info> cr = cb.createQuery(Info.class);
        Root<Info> root = cr.from(Info.class);
        User user = session.get(User.class, userId);
        cr.select(root).where(cb.equal(root.get("user"), user));
        Info info = session.createQuery(cr).uniqueResult();
        if (info == null) {
            info = new Info();
            info.setUser(user);
            Transaction tx1 = session.beginTransaction();
            session.save(info);
            tx1.commit();
        }
        session.close();
        return info;
    }

    @Override
    public String getTaskUrl(Integer userId) {
        return createIfNotExists(userId).getTaskUrl();
    }

    @Override
    public String getUserUrl(Integer userId) {
        return createIfNotExists(userId).getUserUrl();
    }

    @Override
    public void changeTaskUrl(String taskUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        info.setTaskUrl(taskUrl);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(info);
        tx1.commit();
        session.close();
    }

    @Override
    public void changeUserUrl(String userUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        info.setUserUrl(userUrl);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(info);
        tx1.commit();
        session.close();
    }

}
