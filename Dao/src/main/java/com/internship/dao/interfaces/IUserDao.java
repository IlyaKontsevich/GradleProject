package com.internship.dao.interfaces;

import com.internship.model.entity.User;


public interface IUserDao extends IDao<User> {
    User getByEmail(String email);
}
