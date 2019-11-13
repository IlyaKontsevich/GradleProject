package com.internship.dao.implementation;

import com.internship.dao.interfaces.IInfoDao;
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

    @Override
    public String getTaskUrl(Integer userId) {
        return createIfNotExists(userId).getTaskUrl();
    }

    @Override
    public String getUserUrl(Integer userId) {
        return createIfNotExists(userId).getUserUrl();
    }

    @Override
    public String getMessageUrl(Integer userId) {
        return createIfNotExists(userId).getMessageUrl();
    }

    @Override
    public void changeMessageUrl(String messageUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        info.setMessageUrl(messageUrl);
        update(info);
    }

    @Override
    public void changeTaskUrl(String taskUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        info.setTaskUrl(taskUrl);
        update(info);
    }

    @Override
    public void changeUserUrl(String userUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        info.setUserUrl(userUrl);
        update(info);
    }

    private void update(Info info){
        Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(info);
        tx1.commit();
        session.close();
    }

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
}
