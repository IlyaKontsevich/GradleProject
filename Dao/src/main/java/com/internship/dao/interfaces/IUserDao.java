package com.internship.dao.interfaces;

import com.internship.model.User;


public interface IUserDao extends IDao<User> {
    User getByEmail(String email);
}
