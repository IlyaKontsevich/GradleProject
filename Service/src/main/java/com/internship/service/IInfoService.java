package com.internship.service;

import com.internship.model.User;

public interface IInfoService {
    String getTaskUrl();

    String getUserUrl();

    User getCurrentUser();

    void changeTaskUrl(String taskUrl);

    void changeUserUrl(String userUrl);
}
