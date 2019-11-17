package com.internship.dao.interfaces;

import com.internship.model.entity.Message;
import java.util.List;

public interface IMessagesDao extends IDao<Message> {
    List<Message> getUnreadMessages(Integer userId);
}
