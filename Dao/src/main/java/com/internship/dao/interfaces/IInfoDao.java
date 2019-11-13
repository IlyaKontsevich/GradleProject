package com.internship.dao.interfaces;

public interface IInfoDao {
    String getTaskUrl(Integer userId);

    String getUserUrl(Integer userId);

    String getMessageUrl(Integer userId);

    void changeMessageUrl(String messageUrl, Integer userId);

    void changeTaskUrl(String taskUrl, Integer userId);

    void changeUserUrl(String userUrl, Integer userId);
}

