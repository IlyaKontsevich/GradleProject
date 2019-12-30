package com.internship.controller;

import com.internship.model.entity.User;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IMessageService;
import com.internship.service.interfaces.IUserService;
import com.internship.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.internship.model.enums.Type.USER;
import static com.internship.utils.UtilsForController.changeUrl;
import static com.internship.utils.UtilsForController.createPageRequest;

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
        m.addAttribute("command", new User());
        return "userPages/userForm";
    }

    @RequestMapping("/redirect")
    public String prevSession() {
        return  (infoService.getUserUrl() != null)
                ? "redirect:../user/" + infoService.getUserUrl()
                :"redirect:../user/";
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("user") User user) {
        return  (service.add(user) != null)
                ? "redirect:../user/" + infoService.getUserUrl()
                : "redirect:error/User with same email already exists";
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
        filter = Optional.ofNullable(filter).orElse(new ArrayList<>());
        List<User> list = service.getPage(createPageRequest(page, size, sort, filter, null, USER));
        model
                .addAttribute("url", changeUrl(page, size, sort, filter, USER, infoService))
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

    @Security
    @RequestMapping(value = "/{id}/edit")
    public String edit(@PathVariable Integer id, Model m) {
        User user = service.get(id);
        m.addAttribute("command", user);
        return "userPages/userEditForm";
    }

    @Security
    @RequestMapping(value = "/{id}/viewtask")
    public String viewTask(@PathVariable Integer id) {
        return (infoService.getTaskUrl() != null)
                ? "redirect: /user/{id}/task/" + infoService.getTaskUrl()
                : "redirect: /user/{id}/task/";
    }

    @Security
    @RequestMapping(value = "/{id}/viewMessage")
    public String viewMessage(@PathVariable Integer id) {
         return (infoService.getMessageUrl() != null)
                 ? "redirect: /user/{id}/messages/" + infoService.getMessageUrl()
                 : "redirect: /user/{id}/messages/";
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/user/" + infoService.getUserUrl();
    }
}
