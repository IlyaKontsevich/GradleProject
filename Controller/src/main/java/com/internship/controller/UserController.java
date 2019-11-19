package com.internship.controller;

import com.internship.model.entity.User;
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

import static com.internship.model.enums.Type.USER;
import static com.internship.utils.UtilsForController.*;

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
            return "redirect:error/User with same email already exists";
    }

    @RequestMapping("/error/{errorCode}")
    public String error(@PathVariable String errorCode, Model m) {
        m.addAttribute("error", errorCode);
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
            changeUrl(page, size, sort, null, infoService, USER);
            model.addAttribute("url", infoService.getUserUrl());
        } else {
            changeUrl(page, size, sort, filter, infoService, USER);
            model.addAttribute("url", infoService.getUserUrl());
        }
        List<User> list = service.getPage(createPageRequest(page, size, sort, filter, null, USER));
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
    public String edit(@PathVariable Integer id, Model m) {
        if (access(id, service, infoService))
            return "redirect:/accessDenied/";
        User user = service.get(id);
        m.addAttribute("command", user);
        return "userPages/userEditForm";
    }

    @RequestMapping(value = "/{id}/viewtask")
    public String viewTask(@PathVariable Integer id) {
        if (access(id, service, infoService))
            return "redirect:/accessDenied/";
        if(infoService.getTaskUrl()!= null)
            return "redirect: /user/{id}/task/" + infoService.getTaskUrl();
        else
            return "redirect: /user/{id}/task/";
    }

    @RequestMapping(value = "/{id}/viewMessage")
    public String viewMessage(@PathVariable Integer id) {
        if (access(id, service, infoService))
            return "redirect:/accessDenied/";
        if (infoService.getMessageUrl() != null)
            return "redirect: /user/{id}/messages/" + infoService.getMessageUrl();
        else
            return "redirect: /user/{id}/messages/";
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/user/" + infoService.getUserUrl();
    }

}
