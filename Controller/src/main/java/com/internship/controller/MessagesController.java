package com.internship.controller;

import com.internship.model.entity.Message;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IMessageService;
import com.internship.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.internship.model.enums.Type.MESSAGE;
import static com.internship.model.enums.Type.USER;
import static com.internship.utils.UtilsForController.*;

@Controller
@RequestMapping("user/{userId}/messages")
public class MessagesController {
    @Autowired
    private IMessageService service;
    @Autowired
    private IInfoService infoService;
    @Autowired
    private IUserService userService;

    @RequestMapping("/form")
    public String showForm(Model m) {
        m.addAttribute("command", new Message());
        return "messagesPages/messagesForm";
    }

    @RequestMapping("{id}/answer")
    public String answer(@PathVariable Integer id) {
        if (access(id, userService, infoService))
            return "redirect:/accessDenied/";
        return "redirect: ../form";
    }

    @RequestMapping("{id}/read")
    public String read(@PathVariable Integer id) {
        if (access(id, userService, infoService))
            return "redirect:/accessDenied/";
        Message message = service.get(id);
        message.setRead(true);
        service.update(message);
        return "redirect: ../";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@PathVariable Integer userId,
                       @ModelAttribute("message") Message message) {
        message.setRead(false);
        message.setSendTime(LocalDate.now());
        message.setSenderUser(userService.get(userId));
        if (userService.getByEmail(message.getReceiverEmail()) == null)
            return "redirect:/user/error";
        message.setReceiverUser(userService.getByEmail(message.getReceiverEmail()));
        if (service.add(message) != null)
            return "redirect:/user/{userId}/messages/" + infoService.getMessageUrl();
        else
            return "redirect:/user/error";
    }

    @RequestMapping(value = "/")
    public String view(@PathVariable Integer userId,
                       @RequestParam(value = "page", defaultValue = "1") String page,
                       @RequestParam(value = "size", defaultValue = "3") String size,
                       @RequestParam(value = "sort", defaultValue = "sendTime:asc") List<String> sort,
                       @RequestParam(required = false, value = "filter") List<String> filter,
                       Authentication authentication,
                       Model model) {
        if (access(userId, userService, infoService))
            return "redirect:/accessDenied/";
        if (filter == null) {
            filter = new ArrayList<String>();
            changeUrl(page, size, sort, null, infoService, MESSAGE);
            model.addAttribute("url", infoService.getMessageUrl());
        } else {
            changeUrl(page, size, sort, filter, infoService, MESSAGE);
            model.addAttribute("url", infoService.getMessageUrl());
        }
        List<Message> list = service.getPage(createPageRequest(page, size, sort, filter, userId, MESSAGE));
        model
                .addAttribute("login", authentication.getName())
                .addAttribute("filter", String.join(", and by ", filter).replace(":", " value:"))
                .addAttribute("sort", String.join(", and by ", sort).replace(":", " order:"))
                .addAttribute("pageSize", Integer.parseInt(size))
                .addAttribute("size", service.getSize(userId))
                .addAttribute("position", page)
                .addAttribute("list", list);
        return "messagesPages/viewMessages";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        if (access(id, userService, infoService))
            return "redirect:/accessDenied/";
        service.delete(id);
        return "redirect:/user/{userId}/messages/" + infoService.getMessageUrl();
    }

}
