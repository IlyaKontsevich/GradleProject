package com.internship.dao.implementation;

import com.internship.dao.interfaces.IInfoDao;
import com.internship.model.entity.Info;
import com.internship.model.entity.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class InfoDao extends GenericDao<Info> implements IInfoDao {

    @Override
    public String getTaskUrl(Integer userId) {
        return createIfNotExists(userId).getTaskUrl();
    }

    @Override
    public String getUserUrl(Integer userId) {
        return createIfNotExists(userId).getUserUrl();
    }

    @Override
    public String getMessageUrl(Integer userId) {
        return createIfNotExists(userId).getMessageUrl();
    }

    @Override
    public void changeMessageUrl(String messageUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        info.setMessageUrl(messageUrl);
        update(info);
    }

    @Override
    public void changeTaskUrl(String taskUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        info.setTaskUrl(taskUrl);
        update(info);
    }

    @Override
    public void changeUserUrl(String userUrl, Integer userId) {
        Info info = createIfNotExists(userId);
        info.setUserUrl(userUrl);
        update(info);
    }

    private Info createIfNotExists(Integer userId) {
        User user = getSession().get(User.class, userId);

        Info info = (Info) getSession()
                .createCriteria(Info.class)
                .add(Restrictions.eq("user", user))
                .uniqueResult();
        if(info == null){
            info = new Info();
            info.setUser(user);
            add(info);

        }
        return info;
    }
}
