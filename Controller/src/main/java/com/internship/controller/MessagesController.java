package com.internship.controller;

import com.internship.model.entity.Message;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IMessageService;
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

import static com.internship.model.enums.Type.MESSAGE;
import static com.internship.model.enums.Type.USER;
import static com.internship.utils.UtilsForController.*;

@Controller
@RequestMapping("user/{id}/messages")
@Security
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
        m.addAttribute("userList", userService.getAll());
        return "messagesPages/messagesForm";
    }

    @RequestMapping("{id}/read")
    public String read(@PathVariable Integer id) {
        Message message = service.get(id);
        message.setIsRead(true);
        service.update(message);
        return "redirect: ../" + infoService.getMessageUrl();
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@PathVariable Integer id,
                       @ModelAttribute("message") Message message) {
        message.setIsRead(false);
        message.setSendTime(LocalDate.now());
        message.setSenderUser(userService.get(id));
        message.setReceiverUser(userService.getByEmail(message.getReceiverEmail()));
        service.add(message);
        return "redirect:/user/{id}/messages/" + infoService.getMessageUrl();
    }

    @RequestMapping(value = "/")
    public String view(@PathVariable Integer id,
                       @RequestParam(value = "page", defaultValue = "1") String page,
                       @RequestParam(value = "size", defaultValue = "3") String size,
                       @RequestParam(value = "sort", defaultValue = "sendTime:asc") List<String> sort,
                       @RequestParam(required = false, value = "filter") List<String> filter,
                       Authentication authentication,
                       Model model) {
        filter = Optional.ofNullable(filter).orElse(new ArrayList<>());
        List<Message> list = service.getPage(createPageRequest(page, size, sort, filter, id, MESSAGE));
        model
                .addAttribute("url", changeUrl(page, size, sort, filter, MESSAGE, infoService))
                .addAttribute("login", authentication.getName())
                .addAttribute("filter", String.join(", and by ", filter).replace(":", " value:"))
                .addAttribute("sort", String.join(", and by ", sort).replace(":", " order:"))
                .addAttribute("pageSize", Integer.parseInt(size))
                .addAttribute("size", service.getSize(id))
                .addAttribute("position", page)
                .addAttribute("list", list);
        return "messagesPages/viewMessages";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/user/{id}/messages/" + infoService.getMessageUrl();
    }

}
