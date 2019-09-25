package com.internship.controller;

import com.internship.model.Task;
import com.internship.service.IInfoService;
import com.internship.service.ITaskService;
import com.internship.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("user/{userId}/task")
public class TaskController {
    @Autowired
    private ITaskService service;
    @Autowired
    private IUserService userService;
    @Autowired
    private IInfoService infoService;

    @RequestMapping("/form")
    public String showForm(@PathVariable Integer userId, Model m) {
        if(!access(userId)){
            return "redirect:/accessDenied/";
        }
        m.addAttribute("command", new Task("taskname"));
        return "taskForm";
    }

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(String date,@PathVariable Integer userId, @ModelAttribute("task") Task task){
        if(!access(userId)){
            return "redirect:/accessDenied/";
        }
        task.setUser(userService.get(userId));
        LocalDate localDate = LocalDate.parse(date);
        LocalDate todayDate = LocalDate.now();
        if(localDate.isBefore(todayDate)) {
            return "redirect:/user/error";
        }else {
            task.setTimeadd(todayDate);
            task.setDeadline(localDate);
            task.setIsdone(false);
            if (service.add(task) != null)
                return "redirect:../task/" + infoService.getTaskUrl();
            else
                return "redirect:/user/error";
        }
    }

    private boolean access(Integer userId){
        return infoService.getCurrentUser().equals(userService.get(userId)) ||
                infoService.getCurrentUser().getEmail().equals("admin@mail.ru");
    }

    @RequestMapping("/")
    public String view(@PathVariable Integer userId,
                       @RequestParam(value="page", defaultValue = "1") String page,
                       @RequestParam(value="size", defaultValue = "3") String size,
                       @RequestParam(value="sort",defaultValue = "name:asc") List<String> sort,
                       @RequestParam(required = false, value="filter") List<String> filter, Model m){
        if(!access(userId)){
            return "redirect:/accessDenied/";
        }
        if(filter == null) {
            filter = new ArrayList<String>();
            filter.add("");
            infoService.changeTaskUrl("?page="+page+"&size="+size+"&sort="+String.join("&sort=",sort));
            m.addAttribute("url", infoService.getTaskUrl());
        }else {
            infoService.changeTaskUrl("?page="+page+"&size="+size+"&sort="+String.join("&sort=",sort)+"&filter="+String.join("&filter=",filter));
            m.addAttribute("url", infoService.getTaskUrl());
        }
        Collection<Task> list = service.getPage(Integer.parseInt(page),Integer.parseInt(size),userId,sort,filter);
        m.addAttribute("userUrl", infoService.getUserUrl());
        m.addAttribute("filter",String.join(", and by ",filter).replace(":"," value:"));
        m.addAttribute("sort",String.join(", and by ",sort).replace(":"," order:"));
        m.addAttribute("pageSize",Integer.parseInt(size));
        m.addAttribute("size",service.getSize(userId));
        m.addAttribute("position",page);
        m.addAttribute("list",list);
        return "task";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public String editSave(String time,String done,String date,@PathVariable Integer userId, @ModelAttribute("task") Task task){
        if(!access(userId)){
            return "redirect:/accessDenied/";
        }
        task.setUser(userService.get(userId));
        task.setTimeadd(LocalDate.parse(time));
        task.setDeadline(LocalDate.parse(date));
        task.setIsdone(Boolean.parseBoolean(done));
        service.update(task);
        return "redirect: ../" + infoService.getTaskUrl();
    }

    @RequestMapping(value="/{id}/edit")
    public String edit(@PathVariable Integer userId,@PathVariable Integer id, Model m){
        if(!access(userId)){
            return "redirect:/accessDenied/";
        }
        Task task = service.get(id);
        m.addAttribute("command",task);
        m.addAttribute("date",task.getTimeadd());
        return "taskEditForm";
    }
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable Integer userId){
        if(!access(userId)){
            return "redirect:/accessDenied/";
        }
        service.delete(id);
        return "redirect: ../task/" + infoService.getTaskUrl();
    }
}

