package com.internship.service.interfaces;

import com.internship.model.entity.Message;

import java.util.List;

public interface IMessageService extends IService<Message> {
    Integer getSize(Integer userId);

    List<Message> getPage(Integer position, Integer pageSize, Integer userId, List<String> sortType, List<String> filter);

    List<Message> getBySenderId(Integer senderId);

    List<Message> getByReceiverId(Integer receiverId);

    Integer getUnreadMessages(Integer userId);
}
