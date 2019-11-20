package com.internship.service.interfaces;

import com.internship.model.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    Integer getSize();
    User getByEmail(String email);
    List<User> getAll();
}
