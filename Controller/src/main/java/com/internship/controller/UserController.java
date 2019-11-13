package com.internship.controller;

import com.internship.model.User;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IMessageService;
import com.internship.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;
    @Autowired
    private IInfoService infoService;
    @Autowired
    private IMessageService messageService;


    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping("/form")
    public String showForm(Model m) {
        m.addAttribute("command", new User("username"));
        return "userPages/userForm";
    }

    @RequestMapping("/redirect")
    public String prevSession() {
        if (infoService.getUserUrl() != null)
            return "redirect:../user/" + infoService.getUserUrl();
        else
            return "redirect:../user/";
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("user") User user) {
        if (service.add(user) != null)
            return "redirect:../user/" + infoService.getUserUrl();
        else
            return "redirect:error";
    }

    @RequestMapping("/error")
    public String error(Model m) {
        m.addAttribute("url", infoService.getUserUrl());
        return "userPages/error";
    }

    @RequestMapping(value = "/")
    public String view(@RequestParam(value = "page", defaultValue = "1") String page,
                       @RequestParam(value = "size", defaultValue = "3") String size,
                       @RequestParam(value = "sort", defaultValue = "name:asc") List<String> sort,
                       @RequestParam(required = false, value = "filter") List<String> filter,
                       Authentication authentication,
                       Model model) {
        if (filter == null) {
            filter = new ArrayList<String>();
            changeUserUrl(page, size, sort, null);
            model.addAttribute("url", infoService.getUserUrl());
        } else {
            changeUserUrl(page, size, sort, filter);
            model.addAttribute("url", infoService.getUserUrl());
        }
        List<User> list = service.getPage(Integer.parseInt(page), Integer.parseInt(size), sort, filter);
        model
                .addAttribute("unreadMessages", messageService.getUnreadMessages(((User) authentication.getPrincipal()).getId()))
                .addAttribute("userId", ((User) authentication.getPrincipal()).getId())
                .addAttribute("taskUrl", infoService.getTaskUrl())
                .addAttribute("login", authentication.getName())
                .addAttribute("filter", String.join(", and by ", filter).replace(":", " value:"))
                .addAttribute("sort", String.join(", and by ", sort).replace(":", " order:"))
                .addAttribute("pageSize", Integer.parseInt(size))
                .addAttribute("size", service.getSize())
                .addAttribute("position", page)
                .addAttribute("list", list);
        return "userPages/viewUser";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String editSave(@ModelAttribute("user") User user) {
        service.update(user);
        return "redirect:/user/" + infoService.getUserUrl();
    }

    @RequestMapping(value = "/{id}/edit")
    public String edit(@PathVariable int id, Model m) {
        if (!access(id))
            return "redirect:/accessDenied/";
        User user = service.get(id);
        m.addAttribute("command", user);
        return "userPages/userEditForm";
    }

    @RequestMapping(value = "/{id}/viewtask")
    public String viewTask(@PathVariable int id) {
        if (!access(id))
            return "redirect:/accessDenied/";
        return "redirect: /user/{id}/task/" + infoService.getTaskUrl();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/user/" + infoService.getUserUrl();
    }


    private boolean access(Integer userId) {
        return infoService.getCurrentUser().equals(service.get(userId)) ||
                infoService.getCurrentUser().getEmail().equals("admin@mail.ru");
    }

    private void changeUserUrl(String page, String size, List<String> sort, List<String> filter) {
        if (filter == null)
            infoService.changeUserUrl(
                    "?page=" + page
                            + "&size=" + size
                            + "&sort=" + String.join("&sort=", sort));
        else
            infoService.changeUserUrl(
                    "?page=" + page
                            + "&size=" + size
                            + "&sort=" + String.join("&sort=", sort)
                            + "&filter=" + String.join("&filter=", filter));
    }
}
