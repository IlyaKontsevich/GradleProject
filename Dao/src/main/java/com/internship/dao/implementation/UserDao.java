package com.internship.dao.implementation;

import com.internship.dao.interfaces.IUserDao;
import com.internship.model.entity.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends GenericDao<User> implements IUserDao {

    @Override
    @SuppressWarnings("unchecked")
    public User getByEmail(String email) {
        return (User) getSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

}
