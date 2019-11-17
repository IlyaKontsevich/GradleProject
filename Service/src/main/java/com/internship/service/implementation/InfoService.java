package com.internship.service.implementation;


import com.internship.dao.interfaces.IInfoDao;
import com.internship.model.entity.User;
import com.internship.model.enums.Type;
import com.internship.service.interfaces.IInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation= Propagation.REQUIRED)
public class InfoService implements IInfoService {
    @Autowired
    private IInfoDao dao;

    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Override
    public void changeUrl(String newUrl, Type type) {
        switch (type){
            case TASK:
                changeTaskUrl(newUrl);
                break;
            case USER:
                changeUserUrl(newUrl);
                break;
            case MESSAGE:
                changeMessageUrl(newUrl);
                break;
        }
    }

    @Override
    public String getTaskUrl() {
        return dao.getTaskUrl(getCurrentUser().getId());
    }

    @Override
    public String getUserUrl() {
        return dao.getUserUrl(getCurrentUser().getId());
    }

    @Override
    public String getMessageUrl() {
        return dao.getMessageUrl(getCurrentUser().getId());
    }

    @Override
    public void changeTaskUrl(String taskUrl) {
        dao.changeTaskUrl(taskUrl, getCurrentUser().getId());
    }

    @Override
    public void changeMessageUrl(String messageUrl) {
        dao.changeMessageUrl(messageUrl, getCurrentUser().getId());
    }

    @Override
    public void changeUserUrl(String userUrl) {
        dao.changeUserUrl(userUrl, getCurrentUser().getId());

    }
}
