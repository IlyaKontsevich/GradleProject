package com.internship.dao.implementation;

import com.internship.dao.interfaces.IMessagesDao;
import com.internship.model.Message;
import com.internship.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

import static com.internship.utils.UtilsForDao.mapMessagesFilterToPredicates;
import static com.internship.utils.UtilsForDao.mapSortToOrder;

@Component
public class MessageDao implements IMessagesDao{

    @Override
    public Message get(Integer id) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Message> cr = cb.createQuery(Message.class);
        Root<Message> root = cr.from(Message.class);

        cr.select(root).where(cb.equal(root.get("id"),id));
        List<Message> messages = session.createQuery(cr).getResultList();
        session.close();
        return messages.get(0);
    }

    @Override
    public List<Message> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = cb.createQuery(Message.class);
        Root<Message> root = criteriaQuery.from(Message.class);

        criteriaQuery.select(root)
                .where(mapMessagesFilterToPredicates(filter, root, cb))
                .orderBy(mapSortToOrder(sortType, root, cb));
        List<Message> messages = session
                .createQuery(criteriaQuery)
                .setFirstResult(position)
                .setMaxResults(pageSize)
                .getResultList();
        session.close();
        return messages;
    }

    @Override
    public List<Message> getUnreadMessages(Integer userId) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Message> cr = cb.createQuery(Message.class);
        Root<Message> root = cr.from(Message.class);

        cr.select(root)
                .where(
                        cb.equal(root.get("receiverUser"),userId),
                        cb.equal(root.get("isRead"),false)
                );
        List<Message> messages = session.createQuery(cr).getResultList();
        session.close();
        return messages;
    }

    @Override
    public Message add(Message message) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(message);
        tx1.commit();
        session.close();
        List<Message> messages = (List<Message>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Message WHERE sender_id ='" + message.getSenderUser().getId()
                        +"'AND receiver_id ='"+message.getReceiverUser().getId()
                        +"'AND send_time ='" + message.getSendTime()+ "'").list();
        return messages.get(0);
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

    @Override
    public void update(Message message) {
        Session session = Objects.requireNonNull(HibernateSessionFactoryUtil.getSessionFactory()).openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(message);
        tx1.commit();
        session.close();
    }
}
