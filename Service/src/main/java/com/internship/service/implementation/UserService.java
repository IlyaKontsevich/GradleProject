package com.internship.service.implementation;

import com.internship.dao.interfaces.IUserDao;
import com.internship.model.User;
import com.internship.service.interfaces.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.internship.service.utils.UtilsForServices.changePosition;


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

    public List<User> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        log.info("Get user page");
        position = changePosition(position, pageSize);
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
        return dao.getPage(0, 1000, new ArrayList<>(), new ArrayList<>()).size();
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

}
