package com.internship.service.interfaces;

import com.internship.model.entity.Message;

import java.util.List;

public interface IMessageService extends IService<Message> {
    Integer getSize(Integer userId);

    List<Message> getBySenderId(Integer senderId);

    List<Message> getByReceiverId(Integer receiverId);

    Integer getUnreadMessages(Integer userId);
}
