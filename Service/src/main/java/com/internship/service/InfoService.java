package com.internship.service;


import com.internship.dao.IInfoDao;
import com.internship.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InfoService implements IInfoService {
    @Autowired
    private IInfoDao infoDao;

    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Override
    public String getTaskUrl() {
        return infoDao.getTaskUrl(getCurrentUser().getId());
    }

    @Override
    public String getUserUrl() {
        return infoDao.getUserUrl(getCurrentUser().getId());
    }


    @Override
    public void changeTaskUrl(String taskUrl) {
        infoDao.changeTaskUrl(taskUrl, getCurrentUser().getId());
    }

    @Override
    public void changeUserUrl(String userUrl) {
        infoDao.changeUserUrl(userUrl, getCurrentUser().getId());

    }
}
