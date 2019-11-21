package com.internship.controller;

import com.internship.model.entity.Task;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.ITaskService;
import com.internship.service.interfaces.IUserService;
import com.internship.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.internship.model.enums.Type.TASK;
import static com.internship.utils.UtilsForController.*;


@Controller
@RequestMapping("user/{userId}/task")
@Security
public class TaskController{
    @Autowired
    private ITaskService service;
    @Autowired
    private IUserService userService;
    @Autowired
    private IInfoService infoService;

    @RequestMapping("/form")
    public String showForm(@PathVariable Integer userId, Model m) {
        m.addAttribute("command", new Task());
        return "taskPages/taskForm";
    }

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(@PathVariable Integer userId, String date, @ModelAttribute("task") Task task){
        task.setUser(userService.get(userId));
        LocalDate localDate = LocalDate.parse(date);
        LocalDate todayDate = LocalDate.now();
        if(localDate.isBefore(todayDate)) {
            return "redirect:/user/error";
        }else {
            task.setTimeAdd(todayDate);
            task.setDeadLine(localDate);
            task.setIsDone(false);
            return  (service.add(task) != null)
                    ? "redirect:../task/" + infoService.getTaskUrl()
                    : "redirect:/user/error/{Task with same name already exists}";
        }
    }

    @RequestMapping("/")
    public String view(@PathVariable Integer userId,
                       @RequestParam(value="page", defaultValue = "1") String page,
                       @RequestParam(value="size", defaultValue = "3") String size,
                       @RequestParam(value="sort",defaultValue = "name:asc") List<String> sort,
                       @RequestParam(required = false, value="filter") List<String> filter,
                       Authentication authentication, Model model){
        filter = Optional.ofNullable(filter).orElse(new ArrayList<>());
        List<Task> list = service.getPage(createPageRequest(page, size, sort, filter, userId, TASK));
        model
                .addAttribute("url", changeUrl(page, size, sort, filter, TASK, infoService))
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
    public String editSave(@PathVariable Integer userId, String time,String done,String date, @ModelAttribute("task") Task task){
        task.setUser(userService.get(userId));
        task.setTimeAdd(LocalDate.parse(time));
        task.setDeadLine(LocalDate.parse(date));
        task.setIsDone(Boolean.parseBoolean(done));
        service.update(task);
        return "redirect: ../" + infoService.getTaskUrl();
    }

    @RequestMapping(value="/{id}/edit")
    public String edit(@PathVariable Integer userId,@PathVariable Integer id, Model m){
        Task task = service.get(id);
        m.addAttribute("command",task);
        m.addAttribute("date",task.getTimeAdd());
        return "taskPages/taskEditForm";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable Integer userId){
        service.delete(id);
        return "redirect: ../task/" + infoService.getTaskUrl();
    }
}

