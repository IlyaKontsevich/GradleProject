package com.internship.service.interfaces;

import com.internship.model.User;

public interface IInfoService {
    String getTaskUrl();

    String getUserUrl();

    String getMessageUrl();

    User getCurrentUser();

    void changeTaskUrl(String taskUrl);

    void changeMessageUrl(String messageUrl);

    void changeUserUrl(String userUrl);
}
