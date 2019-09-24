package com.internship.controller;

import com.internship.model.User;
import com.internship.service.IInfoService;
import com.internship.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;
    @Autowired
    private IInfoService infoService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    Integer userId;


    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping("/form")
    public String showForm(Model m) {
        m.addAttribute("command", new User("username"));
        return "userForm";
    }

    @RequestMapping("/prevsession")
    public String prevSession(Authentication authentication) {
        //Object principal = authentication.getPrincipal();
        //User user = (User)principal;
        // User actingUser = (User) authentication.getPrincipal();
        User user = service.getByEmail(authentication.getName());
        userId = user.getId();
        return "redirect:/user/" + infoService.getUserUrl(userId);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("user") User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (service.add(user) != null)
            return "redirect:../user/" + infoService.getUserUrl(userId);
        else
            return "redirect:error";
    }

    @RequestMapping("/error")
    public String error(Model m) {
        m.addAttribute("url", infoService.getUserUrl(userId));
        return "error";
    }

    @RequestMapping(value = "/")
    public String view(@RequestParam(value = "page", defaultValue = "1") String page,
                       @RequestParam(value = "size", defaultValue = "3") String size,
                       @RequestParam(value = "sort", defaultValue = "name:asc") List<String> sort,
                       @RequestParam(required = false, value = "filter") List<String> filter,
                       Authentication authentication,
                       Model m) {
        if (filter == null) {
            filter = new ArrayList<String>();
            filter.add("");
            infoService.changeUserUrl("?page=" + page + "&size=" + size + "&sort=" + String.join("&sort=", sort), userId);
            m.addAttribute("url", infoService.getUserUrl(userId));
        } else {
            infoService.changeUserUrl("?page=" + page + "&size=" + size + "&sort=" + String.join("&sort=", sort) + "&filter=" + String.join("&filter=", filter), userId);
            m.addAttribute("url", infoService.getUserUrl(userId));
        }
        Collection<User> list = service.getPage(Integer.parseInt(page), Integer.parseInt(size), sort, filter);
        m.addAttribute("taskUrl", infoService.getTaskUrl(userId));
        m.addAttribute("login", authentication.getName());
        m.addAttribute("filter", String.join(", and by ", filter).replace(":", " value:"));
        m.addAttribute("sort", String.join(", and by ", sort).replace(":", " order:"));
        m.addAttribute("pageSize", Integer.parseInt(size));
        m.addAttribute("size", service.getSize());
        m.addAttribute("position", page);
        m.addAttribute("list", list);
        return "viewUser";
    }


    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String editSave(@ModelAttribute("user") User user) {
        service.update(user);
        return "redirect:/user/" + infoService.getUserUrl(userId);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}/edit")
    public String edit(@PathVariable int id, Model m) {
        User user = service.get(id);
        m.addAttribute("command", user);
        return "userEditForm";
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/user/" + infoService.getUserUrl(userId);
    }
}
