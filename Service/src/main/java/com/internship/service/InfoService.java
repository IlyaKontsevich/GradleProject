package com.internship.service;

import com.internship.dao.IInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService implements IInfoService {
    @Autowired
    private IInfoDao infoDao;

    @Override
    public String getTaskUrl(Integer userId) {
        return infoDao.getTaskUrl(userId);
    }

    @Override
    public String getUserUrl(Integer userId) {
        return infoDao.getUserUrl(userId);
    }


    @Override
    public void changeTaskUrl(String taskUrl, Integer userId) {
        infoDao.changeTaskUrl(taskUrl, userId);
    }

    @Override
    public void changeUserUrl(String userUrl, Integer userId) {
        infoDao.changeUserUrl(userUrl, userId);

    }
}
