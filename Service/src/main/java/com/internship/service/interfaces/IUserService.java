package com.internship.service.interfaces;

import com.internship.model.entity.User;

public interface IUserService extends IService<User> {
    Integer getSize();
    User getByEmail(String email);
}
