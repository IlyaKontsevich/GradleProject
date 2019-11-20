package com.internship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.internship.utils.UtilsForController.changeFilterValue;


@Controller
public class FilterSortPage {

    @RequestMapping(value = {"user/savefilter{type}",
            "user/{userId}/task/savefilter{type}",
            "user/{userId}/messages/savefilter{type}",})
    public String saveFilter(@PathVariable String type, String filtervalue,
                             @RequestParam(value = "page", defaultValue = "1") String page,
                             @RequestParam(value = "size", defaultValue = "3") String size,
                             @RequestParam(value = "sort", defaultValue = "id:asc") List<String> sort,
                             @RequestParam(required = false, value = "filter") List<String> filter) {
        filter = Optional.ofNullable(filter).orElse(new ArrayList<>());
        if (type.equals("nul"))
            return redirect(page, size, sort, null);
        if (filtervalue!= null && !filtervalue.isEmpty() ) {
            type = type + ":" + filtervalue;
            filter = changeFilterValue(filter, type);
            filter.add(type);
            return redirect(page, size, sort, filter);
        } else {
            return redirect(page, size, sort, filter);
        }
    }

    @RequestMapping(value = {"user/changesort{sorttype}",
            "user/{userId}/task/changesort{sorttype}",
            "user/{userId}/messages/changesort{sorttype}"})
    public String changeSortType(@RequestParam(value = "page", defaultValue = "1") String page,
                                 @RequestParam(value = "size", defaultValue = "3") String size,
                                 @RequestParam(value = "sort", defaultValue = "id:asc") List<String> sort,
                                 @RequestParam(required = false, value = "filter") List<String> filter,
                                 @PathVariable String sorttype) {
        sort = changeFilterValue(sort, sorttype);
        if (sorttype.split(",")[1].equals("nul"))
            return redirect(page, size, sort, filter);
        sort.add(sorttype.replace(",", ":"));
        return redirect(page, size, sort, filter);
    }

    @RequestMapping(value = {"user/changepage{pages}",
            "user/{userId}/task/changepage{pages}",
            "user/{userId}/messages/changepage{pages}"})
    public String changePage(@PathVariable String pages,
                             @RequestParam(value = "page", defaultValue = "1") String page,
                             @RequestParam(value = "size", defaultValue = "3") String size,
                             @RequestParam(value = "sort", defaultValue = "id:asc") List<String> sort,
                             @RequestParam(required = false, value = "filter") List<String> filter) {
        return redirect(pages, size, sort, filter);
    }

    @RequestMapping(value = {"user/pagesize{pageSize}",
            "user/{userId}/task/pagesize{pageSize}",
            "user/{userId}/messages/pagesize{pageSize}"})
    public String changePageSize(@PathVariable String pageSize,
                                 @RequestParam(value = "page", defaultValue = "1") String page,
                                 @RequestParam(value = "size", defaultValue = "3") String size,
                                 @RequestParam(value = "sort", defaultValue = "id:asc") List<String> sort,
                                 @RequestParam(required = false, value = "filter") List<String> filter) {
        return redirect(page, pageSize, sort, filter);
    }

    private String redirect(String page, String size, List<String> sort, List<String> filter) {
        if (filter == null)
            return "redirect:./?page=" + page
                    + "&size=" + size
                    + "&sort=" + String.join("&sort=", sort);

        return "redirect:./?page=" + page
                + "&size=" + size
                + "&sort=" + String.join("&sort=", sort)
                + "&filter=" + String.join("&filter=", filter);
    }
}
