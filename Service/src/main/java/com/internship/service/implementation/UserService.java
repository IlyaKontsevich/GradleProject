package com.internship.service.implementation;

import com.internship.dao.interfaces.IUserDao;
import com.internship.model.PageRequest;
import com.internship.model.entity.User;
import com.internship.service.interfaces.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static java.lang.String.format;


@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
public class UserService extends GenericService<User> implements IUserService {
    @Autowired
    private IUserDao dao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void update(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        super.update(user);
    }

    public User add(User user) {
        if (dao.getByEmail(user.getEmail()) != null) {
            throw new RuntimeException(format("User with %s email already registered", user.getEmail()));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return super.add(user);
    }

    public Integer getSize() {
        return super.getPage(new PageRequest(new ArrayList<>(), new ArrayList<>(), 0, 1000)).size();
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }
}
