package com.internship.controller.rest;

import com.internship.model.entity.User;
import com.internship.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.internship.model.enums.Type.USER;
import static com.internship.utils.UtilsForController.createPageRequest;

@RestController
@RequestMapping("/api")
public class RestUserController {
    @Autowired
    private IUserService service;


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Collection<User> view(@RequestParam(value="page", defaultValue = "1") String page,
                                 @RequestParam(value="size", defaultValue = "3") String size,
                                 @RequestParam(value="sort",defaultValue = "name:asc") List<String> sort,
                                 @RequestParam(required = false, value="filter") List<String> filter){
        if(filter == null)
            filter = new ArrayList<String>();

        Collection<User> list = service.getPage(createPageRequest(page, size, sort, filter, null, USER));
        return list;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public void delete( @PathVariable int id){
        service.delete(id);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public void edit(@ModelAttribute("user") User user){
        service.update(user);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.POST)
    public User save(@ModelAttribute("user") User user){
        if(service.add(user) != null)
            return user;
        else
            return null;
    }
}
