package com.internship.service.interfaces;

import com.internship.model.entity.Info;
import com.internship.model.entity.User;
import com.internship.model.enums.Type;

public interface IInfoService extends IService<Info> {
    String getTaskUrl();

    String getUserUrl();

    String getMessageUrl();

    User getCurrentUser();

    void changeUrl(String newUrl, Type type);

    void changeTaskUrl(String taskUrl);

    void changeMessageUrl(String messageUrl);

    void changeUserUrl(String userUrl);
}
