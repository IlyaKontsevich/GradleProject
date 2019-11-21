package com.internship.controller;

import com.internship.config.HibernateConfig;
import com.internship.config.LoginSecurityConfig;
import com.internship.config.ProjectConfig;
import com.internship.model.PageRequest;
import com.internship.model.entity.User;
import com.internship.service.interfaces.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class,
        LoginSecurityConfig.class,
        ProjectConfig.class})
public class UserServiceTest {
    @Autowired
    private IUserService userService;


    public User initialisationUser() {
        return userService.add(User
                .builder()
                .age(23)
                .password("1111")
                .email("email@mail.ru")
                .name("username")
                .build());
    }

    @Test
    public void update_CHANGE_PARAMETR() {
        User user = initialisationUser();
        Integer newAge = 22;
        user.setAge(newAge);
        userService.update(user);
        Assert.assertEquals(newAge, userService.get(user.getId()).getAge());
        userService.delete(user.getId());
    }

    @Test
    public void getPage_RETURN_ONLY_USERS_WITH_ENTER_PARAMETR() {
        User user = initialisationUser();
        List<String> sort = new ArrayList();
        List<String> filter = new ArrayList();
        sort.add("name:desc");
        filter.add("name:username");
        filter.add("age:23");
        filter.add("email:email@mail.ru");
        User resUser = userService.getPage(PageRequest
                .builder()
                .position(0)
                .pageSize(1)
                .sort(sort)
                .filter(filter)
                .build())
                .get(0);
        Assert.assertTrue(resUser.getEmail().equals("email@mail.ru") &&
                resUser.getName().equals("username") && resUser.getAge().equals(23));
        userService.delete(user.getId());
    }

    @Test
    public void add_NULL_IF_USER_HAVE_SAME_EMAIL() {
        User user = initialisationUser();
        Assert.assertNull(userService.add(user));
        userService.delete(user.getId());
    }

    @Test
    public void delete_GET_USER_RETURN_NULL() {
        User user = initialisationUser();
        userService.delete(user.getId());
        Assert.assertNull(userService.get(user.getId()));
    }

    @Test
    public void get_GET_RETURN_SAME_USER() {
        User user = initialisationUser();
        Assert.assertEquals(userService.get(user.getId()).getId(), user.getId());
        userService.delete(user.getId());
    }

    @Test
    public void getSize_CURRENT_SIZE_MORE_PRIMARY() {
        Integer tableSize = userService.getSize();
        User user = initialisationUser();
        Assert.assertTrue(tableSize < userService.getSize());
        userService.delete(user.getId());
    }

    @Test
    public void getAll_TABLE_NOT_NULL() {
        User user = initialisationUser();
        Assert.assertNotNull(userService.getAll());
        userService.delete(user.getId());
    }

}
