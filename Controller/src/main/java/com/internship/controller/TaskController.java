package com.internship.controller;

import com.internship.model.entity.Task;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.ITaskService;
import com.internship.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.internship.model.enums.Type.TASK;
import static com.internship.utils.UtilsForController.*;


@Controller
@RequestMapping("user/{userId}/task")
public class TaskController{
    @Autowired
    private ITaskService service;
    @Autowired
    private IUserService userService;
    @Autowired
    private IInfoService infoService;

    @RequestMapping("/form")
    public String showForm(@PathVariable Integer userId, Model m) {
        if(access(userId, userService, infoService))
            return "redirect:/accessDenied/";

        m.addAttribute("command", new Task("taskname"));
        return "taskPages/taskForm";
    }

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(String date,@PathVariable Integer userId, @ModelAttribute("task") Task task){
        task.setUser(userService.get(userId));
        LocalDate localDate = LocalDate.parse(date);
        LocalDate todayDate = LocalDate.now();
        if(localDate.isBefore(todayDate)) {
            return "redirect:/user/error";
        }else {
            task.setTimeAdd(todayDate);
            task.setDeadLine(localDate);
            task.setIsDone(false);
            if (service.add(task) != null)
                return "redirect:../task/" + infoService.getTaskUrl();
            else
                return "redirect:/user/error";
        }
    }

    @RequestMapping("/")
    public String view(@PathVariable Integer userId,
                       @RequestParam(value="page", defaultValue = "1") String page,
                       @RequestParam(value="size", defaultValue = "3") String size,
                       @RequestParam(value="sort",defaultValue = "name:asc") List<String> sort,
                       @RequestParam(required = false, value="filter") List<String> filter,
                       Authentication authentication, Model model){
        if(access(userId, userService, infoService))
            return "redirect:/accessDenied/";

        if(filter == null) {
            filter = new ArrayList<String>();
            changeUrl(page, size, sort, filter, infoService, TASK);
            model.addAttribute("url", infoService.getTaskUrl());
        }else {
            changeUrl(page, size, sort, filter, infoService, TASK);
            model.addAttribute("url", infoService.getTaskUrl());
        }
        List<Task> list = service.getPage(Integer.parseInt(page),Integer.parseInt(size),userId,sort,filter);
        model
                .addAttribute("login", authentication.getName())
                .addAttribute("filter",String.join(", and by ",filter).replace(":"," value:"))
                .addAttribute("sort",String.join(", and by ",sort).replace(":"," order:"))
                .addAttribute("pageSize",Integer.parseInt(size))
                .addAttribute("size",service.getSize(userId))
                .addAttribute("position",page)
                .addAttribute("list",list);
        return "taskPages/task";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public String editSave(String time,String done,String date,@PathVariable Integer userId, @ModelAttribute("task") Task task){
        task.setUser(userService.get(userId));
        task.setTimeAdd(LocalDate.parse(time));
        task.setDeadLine(LocalDate.parse(date));
        task.setIsDone(Boolean.parseBoolean(done));
        service.update(task);
        return "redirect: ../" + infoService.getTaskUrl();
    }

    @RequestMapping(value="/{id}/edit")
    public String edit(@PathVariable Integer userId,@PathVariable Integer id, Model m){
        if(access(userId, userService, infoService))
            return "redirect:/accessDenied/";

        Task task = service.get(id);
        m.addAttribute("command",task);
        m.addAttribute("date",task.getTimeAdd());
        return "taskPages/taskEditForm";
    }
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable Integer userId){
        if(access(userId, userService, infoService))
            return "redirect:/accessDenied/";

        service.delete(id);
        return "redirect: ../task/" + infoService.getTaskUrl();
    }
}

