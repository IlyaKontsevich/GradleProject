package com.internship.service.implementation;

import com.internship.dao.interfaces.IMessagesDao;
import com.internship.model.Message;
import com.internship.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.internship.service.utils.UtilsForServices.changePosition;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IMessagesDao dao;

    @Override
    public Integer getSize(Integer userId) {
        List<String> filter = new ArrayList<>();
        filter.add("receiverUser:" + userId);
        return dao.getPage(0,1000, new ArrayList<>(), filter).size();
    }

    @Override
    public List<Message> getPage(Integer position, Integer pageSize, Integer userId, List<String> sortType, List<String> filter) {
        if (filter == null)
             filter = new ArrayList<>();
        filter.add("receiverUser:"+userId);
        position = changePosition(position, pageSize);
        List<Message> result= dao.getPage(position,pageSize,sortType,filter);
        result.addAll(getBySenderId(userId));
        return result;
    }

    @Override
    public List<Message> getBySenderId(Integer senderId) {
        List<String> filter = new ArrayList<>();
        filter.add("senderUser:"+senderId);
        return dao.getPage(0,1000, new ArrayList<>(), filter);
    }

    @Override
    public List<Message> getByReceiverId(Integer receiverId) {
        List<String> filter = new ArrayList<>();
        filter.add("receiverUser:"+receiverId);
        return dao.getPage(0,1000, new ArrayList<>(), filter);
    }

    @Override
    public Integer getUnreadMessages(Integer userId) {
        if(getByReceiverId(userId).isEmpty())
            return 0;
        return dao.getUnreadMessages(userId).size();
    }

    @Override
    public void update(Message message) {
         dao.update(message);
    }

    @Override
    public Message add(Message message) {
        return dao.add(message);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public Message get(Integer id) {
        return dao.get(id);
    }
}
