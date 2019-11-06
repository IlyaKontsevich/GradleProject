package com.internship.service;

import com.internship.dao.IUserDao;
import com.internship.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;


@Service
public class UserService implements IUserService {
    @Autowired
    private IUserDao dao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger log = Logger.getLogger(UserService.class);

    @Override
    public void update(User user) {
        log.info("Update user");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        dao.update(user);
    }

    public User add(User user) {
        log.info("Add user");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return dao.add(user);
    }

    public Collection<User> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        log.info("Get user page");

        UnaryOperator<Integer> changePosition = pos -> {
            if (pos != 1)
                pos += pageSize - 2;
            else
                pos -= 1;
            return pos;
        };
        position = changePosition.apply(position);

        return dao.getPage(position, pageSize, sortType, filter);
    }

    public void delete(Integer id) {
        log.info("Delete user");
        dao.delete(id);
    }

    public User get(Integer id) {
        log.info("Get user");
        return dao.get(id);
    }


    public Integer getSize() {
        return dao.getSize();
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

    public Collection<User> getAll() {
        return dao.getAll();
    }
}
