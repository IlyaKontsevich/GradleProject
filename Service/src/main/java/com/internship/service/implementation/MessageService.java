package com.internship.service.implementation;

import com.internship.dao.interfaces.IMessagesDao;
import com.internship.model.PageRequest;
import com.internship.model.entity.Message;
import com.internship.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
public class MessageService extends GenericService<Message> implements IMessageService {
    @Autowired
    private IMessagesDao dao;

    @Override
    public Integer getSize(Integer userId) {
        List<String> filter = new ArrayList<>();
        filter.add("receiverUser:" + userId);
        return super.getPage(new PageRequest(filter, new ArrayList<>(), 0, 1000)).size();
    }

    @Override
    public List<Message> getPage(PageRequest pageRequest) {
        List<Message> result = super.getPage(pageRequest);
        result.addAll(getBySenderId(result
                .get(0)
                .getSenderUser()
                .getId()));
        return result;
    }

    @Override
    public List<Message> getBySenderId(Integer senderId) {
        List<String> filter = new ArrayList<>();
        filter.add("senderUser:"+senderId);
        return super.getPage(new PageRequest(filter, new ArrayList<>(), 0, 1000));
    }

    @Override
    public List<Message> getByReceiverId(Integer receiverId) {
        List<String> filter = new ArrayList<>();
        filter.add("receiverUser:"+receiverId);
        return super.getPage(new PageRequest(filter, new ArrayList<>(), 0, 1000));
    }

    @Override
    public Integer getUnreadMessages(Integer userId) {
        if(getByReceiverId(userId).isEmpty())
            return 0;
        return dao.getUnreadMessages(userId).size();
    }
}
