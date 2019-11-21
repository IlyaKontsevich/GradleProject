package com.internship.service.implementation;

import com.internship.dao.interfaces.IUserDao;
import com.internship.model.PageRequest;
import com.internship.model.entity.User;
import com.internship.service.interfaces.IUserService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        if (dao.getByEmail(user.getEmail()) != null)
            return null;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return super.add(user);
    }

    public Integer getSize() {
        return super.getPage(PageRequest
                .builder()
                .filter(new ArrayList<>())
                .sort(new ArrayList<>())
                .pageSize(1000)
                .position(0)
                .build())
                .size();
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return super.getPage(PageRequest
                .builder()
                .filter(new ArrayList<>())
                .sort(new ArrayList<>())
                .pageSize(1000)
                .position(0)
                .build());
    }
}
