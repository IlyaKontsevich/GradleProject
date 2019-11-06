package com.internship.dao;

import com.internship.model.User;

import java.util.Collection;
import java.util.List;

public interface IUserDao extends IDao<User> {
    Integer getSize();
    Collection<User> getAll();
    User getByEmail(String email);
}
