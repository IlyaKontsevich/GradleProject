package com.internship.controller;

import com.internship.config.HibernateConfig;
import com.internship.config.LoginSecurityConfig;
import com.internship.config.ProjectConfig;
import com.internship.model.PageRequest;
import com.internship.model.entity.Task;
import com.internship.service.interfaces.ITaskService;
import com.internship.service.interfaces.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class,
        LoginSecurityConfig.class,
        ProjectConfig.class})
public class TaskServiceTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITaskService TaskService;

    public Task initialisationTask() {
        Task task = new Task();
        task.setUser(userService.get(2));
        task.setName("dosumthing");
        task.setIsDone(false);
        task.setDeadLine(LocalDate.parse("2019-12-12"));
        task.setTimeAdd(LocalDate.parse("2019-12-12"));
        task.setPriority("low");
        return TaskService.add(task);
    }

    @Test
    public void getPage_RETURN_ONLY_TASKS_WITH_ENTER_PARAMETR() {
        Task task = initialisationTask();
        List<String> sort = new ArrayList();
        List<String> filter = new ArrayList();
        sort.add("name:desc");
        filter.add("user:" + task.getUser().getId());
        filter.add("name:dosumthing");
        filter.add("deadLine:2019-12-12");
        filter.add("timeAdd:2019-12-12");
        filter.add("isDone:false");
        filter.add("priority:low");
        Task resTask = TaskService.getPage(PageRequest
                .builder()
                .position(0)
                .pageSize(1)
                .sort(sort)
                .filter(filter)
                .build())
                .get(0);
        Assert.assertTrue(resTask.getName().equals("dosumthing")
                && resTask.getPriority().equals("low")
                && resTask.getIsDone().equals(false)
                && resTask.getPriority().equals("low"));
        TaskService.delete(task.getId());
    }

    @Test
    public void update_CHANGE_PARAMETR() {
        Task task = initialisationTask();
        task.setIsDone(true);
        TaskService.update(task);
        Assert.assertTrue(TaskService.get(task.getId()).getIsDone());
        TaskService.delete(task.getId());
    }

    @Test
    public void add_NULL_IF_TASK_HAVE_SAME_NAME() {
        Task task = initialisationTask();
        Assert.assertNull(TaskService.add(task));
        TaskService.delete(task.getId());
    }

    @Test
    public void delete_GET_TASK_RETURN_NULL() {
        Task task = initialisationTask();
        TaskService.delete(task.getId());
        Assert.assertNull(TaskService.get(task.getId()));
    }

    @Test
    public void get_GET_RETURN_SAME_TASK() {
        Task task = initialisationTask();
        Assert.assertEquals(TaskService.get(task.getId()).getId(), task.getId());
        TaskService.delete(task.getId());
    }
}

