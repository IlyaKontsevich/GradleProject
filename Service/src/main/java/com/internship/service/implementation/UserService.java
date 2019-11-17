package com.internship.service.implementation;

import com.internship.dao.interfaces.IUserDao;
import com.internship.model.entity.User;
import com.internship.service.interfaces.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.internship.utils.UtilsForServices.changePosition;
import static java.lang.String.format;


@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
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

        if (dao.getByEmail(user.getEmail()) != null) {
            throw new RuntimeException(format("User with %s email already registered", user.getEmail()));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return dao.add(user);
    }

    public List<User> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        log.info("Get user page");
        position = changePosition(position, pageSize);
        return dao.getPage(position, pageSize, sortType, filter);
    }

    public boolean delete(Integer id) {
        log.info("Delete user");
        return dao.delete(id);
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
