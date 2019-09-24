package com.internship.service;

public interface IInfoService {
    String getTaskUrl(Integer userId);

    String getUserUrl(Integer userId);

    void changeTaskUrl(String taskUrl, Integer userId);

    void changeUserUrl(String userUrl, Integer userId);
}
