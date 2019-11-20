package com.internship.utils;

import com.internship.model.PageRequest;
import com.internship.model.enums.Type;
import com.internship.service.interfaces.IInfoService;

import java.util.List;
import java.util.stream.Collectors;

import static com.internship.model.enums.Type.MESSAGE;
import static com.internship.model.enums.Type.TASK;
import static com.internship.utils.UtilsForServices.changePosition;

public class UtilsForController {

    public static String changeUrl(String page, String size, List<String> sort,
                                   List<String> filter, Type type, IInfoService infoService) {
        if (filter.isEmpty())
            infoService.changeUrl(
                    "?page=" + page
                            + "&size=" + size
                            + "&sort=" + String.join("&sort=", sort), type);
        else
            infoService.changeUrl(
                    "?page=" + page
                            + "&size=" + size
                            + "&sort=" + String.join("&sort=", sort)
                            + "&filter=" + String.join("&filter=", filter), type);
        switch (type) {
            case MESSAGE:
                return infoService.getMessageUrl();
            case USER:
                return infoService.getUserUrl();
            case TASK:
                return infoService.getTaskUrl();
            default:
                return null;
        }
    }

    public static PageRequest createPageRequest(String page, String size, List<String> sort, List<String> filter, Integer id, Type type) {
        Integer position = Integer.valueOf(page);
        Integer pageSize = Integer.valueOf(size);
        position = changePosition(position, pageSize);
        if (id != null) {
            if (type.equals(TASK)) {
                filter = changeFilterValue(filter, "user");
                filter.add("user:" + id);
            }
            if (type.equals(MESSAGE)) {
                filter =  changeFilterValue(filter, "receiverUser");
                filter.add("receiverUser:" + id);
            }
        }
        return new PageRequest(filter, sort, position, pageSize);
    }

    public static List<String> changeFilterValue(List<String> list, String string) {
        return list
                .stream()
                .filter(str -> !str.contains(string.split(",")[0]))
                .filter(str -> !str.contains(string.split(",")[0]))
                .collect(Collectors.toList());
    }
}
