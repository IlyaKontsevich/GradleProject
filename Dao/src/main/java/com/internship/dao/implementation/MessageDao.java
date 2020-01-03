package com.internship.dao.implementation;

import com.internship.dao.interfaces.IMessagesDao;
import com.internship.model.entity.Message;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageDao extends GenericDao<Message> implements IMessagesDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Message> getUnreadMessages(Integer userId) {
        return (List<Message>) getSession()
                .createCriteria(Message.class)
                .add(Restrictions.eq("receiverUser.id", userId))
                .add(Restrictions.eq("isRead", false))
                .list();
    }

}
