package com.internship.controller.rest;

import com.internship.model.entity.Task;
import com.internship.service.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.internship.model.enums.Type.TASK;
import static com.internship.utils.UtilsForController.createPageRequest;

@RestController
@RequestMapping("/api/{userId}/task")
public class RestTaskController {
    @Autowired
    private ITaskService service;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Collection<Task> view(@PathVariable Integer userId,
                                 @RequestParam(value="page", defaultValue = "1") String page,
                                 @RequestParam(value="size", defaultValue = "10") String size,
                                 @RequestParam(value="sort",defaultValue = "name:asc") List<String> sort,
                                 @RequestParam(required = false, value="filter") List<String> filter){
        filter = Optional.ofNullable(filter).orElse(new ArrayList<>());
        return service.getPage(createPageRequest(page, size, sort, filter, userId, TASK));
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public void delete( @PathVariable int id){
        service.delete(id);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public void edit(@ModelAttribute("task") Task task){
        service.update(task);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.POST)
    public Task save(@ModelAttribute("task") Task task){
        if(service.add(task) != null)
            return task;
        else
            return null;
    }
}
