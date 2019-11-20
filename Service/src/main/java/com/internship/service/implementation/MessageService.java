package com.internship.service.implementation;

import com.internship.dao.interfaces.IMessagesDao;
import com.internship.model.PageRequest;
import com.internship.model.entity.Message;
import com.internship.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends GenericService<Message> implements IMessageService {
    @Autowired
    private IMessagesDao dao;

    @Override
    public Integer getSize(Integer userId) {
        List<String> filter = new ArrayList<>();
        filter.add("receiverUser:" + userId);
        return super.getPage(PageRequest
                .builder()
                .filter(filter)
                .sort(new ArrayList<>())
                .pageSize(1000)
                .position(0)
                .build())
                .size();
    }

    @Override
    public List<Message> getPage(PageRequest pageRequest) {
        List<Message> result =super.getPage(pageRequest);
        pageRequest.setFilter(pageRequest
                                        .getFilter()
                                        .stream()
                                        .map(str -> str.replace("receiverUser","senderUser"))
                                        .collect(Collectors.toList()));
        result.addAll(super.getPage(pageRequest));
        new HashSet<>(result);
        return result;
    }

    @Override
    public List<Message> getBySenderId(Integer senderId) {
        List<String> filter = new ArrayList<>();
        filter.add("senderUser:"+senderId);
        return super.getPage(PageRequest
                .builder()
                .filter(filter)
                .sort(new ArrayList<>())
                .pageSize(1000)
                .position(0)
                .build());
    }

    @Override
    public List<Message> getByReceiverId(Integer receiverId) {
        List<String> filter = new ArrayList<>();
        filter.add("receiverUser:"+receiverId);
        return super.getPage(PageRequest
                .builder()
                .filter(filter)
                .sort(new ArrayList<>())
                .pageSize(1000)
                .position(0)
                .build());
    }

    @Override
    public Integer getUnreadMessages(Integer userId) {
        if(getByReceiverId(userId).isEmpty())
            return 0;
        return dao.getUnreadMessages(userId).size();
    }
}
