package com.internship.service.interfaces;

import com.internship.model.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    List<User> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter);
    Integer getSize();
    User getByEmail(String email);
}
