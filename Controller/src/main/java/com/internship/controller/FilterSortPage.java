package com.internship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FilterSortPage {

    @RequestMapping(value = {"user/savefilter{type}", "user/{userId}/task/savefilter{type}"})
    public String saveFilter(@PathVariable String type, String filtervalue,
                             @RequestParam(value = "page", defaultValue = "1") String page,
                             @RequestParam(value = "size", defaultValue = "3") String size,
                             @RequestParam(value = "sort", defaultValue = "name:asc") List<String> sort,
                             @RequestParam(required = false, value = "filter") List<String> filter) {
        type = type
                .replace("{", "")
                .replace("}", "");

        if (type.equals("nul"))
            return redirect(page, size, sort, null);

        if (!filtervalue.equals("")) {
            if (filter == null)
                filter = new ArrayList<String>();
            type = type + ":" + filtervalue;
            filter.removeIf(str -> contains(sort, str));
            filter.add(type);
            return redirect(page, size, sort, filter);
        } else {
            return redirect(page, size, sort, filter);
        }
    }

    @RequestMapping(value = {"user/changepage{pages}", "user/{userId}/task/changepage{pages}"})
    public String changePage(@PathVariable String pages,
                             @RequestParam(value = "page", defaultValue = "1") String page,
                             @RequestParam(value = "size", defaultValue = "3") String size,
                             @RequestParam(value = "sort", defaultValue = "name:asc") List<String> sort,
                             @RequestParam(required = false, value = "filter") List<String> filter) {
        return redirect(pages, size, sort, filter);
    }

    @RequestMapping(value = {"user/pagesize{pageSize}", "user/{userId}/task/pagesize{pageSize}"})
    public String changePageSize(@PathVariable String pageSize,
                                 @RequestParam(value = "page", defaultValue = "1") String page,
                                 @RequestParam(value = "size", defaultValue = "3") String size,
                                 @RequestParam(value = "sort", defaultValue = "name:asc") List<String> sort,
                                 @RequestParam(required = false, value = "filter") List<String> filter) {
        pageSize = pageSize
                .replace("{", "")
                .replace("}", "");
        return redirect(page, pageSize, sort, filter);
    }

    @RequestMapping(value = {"user/changesort{sorttype}", "user/{userId}/task/changesort{sorttype}"})
    public String changeSortType(@RequestParam(value = "page", defaultValue = "1") String page,
                                 @RequestParam(value = "size", defaultValue = "3") String size,
                                 @RequestParam(value = "sort", defaultValue = "name:asc") List<String> sort,
                                 @RequestParam(required = false, value = "filter") List<String> filter,
                                 @PathVariable String sorttype) {
        sorttype = sorttype
                .replace("{", "")
                .replace("}", "");

        if (sorttype.split(",")[1].equals("nul")) {
            sort.removeIf(str -> contains(sort, str));
            if (!sort.isEmpty()) {
                size = size + "&sort=";
            }
            return redirect(page, size, sort, filter);
        }
        sort.removeIf(str -> contains(sort, str));
        sort.add(sorttype.replace(",", ":"));
        return redirect(page, size, sort, filter);
    }

    private boolean contains(List<String> list, String string) {
        for (String str : list) {
            if (str.contains(string.split(",")[0]))
                return true;
        }
        return false;
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
