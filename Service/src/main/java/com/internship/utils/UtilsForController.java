package com.internship.utils;

import com.internship.model.PageRequest;
import com.internship.model.enums.Type;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IUserService;

import java.util.List;

import static com.internship.model.enums.Type.MESSAGE;
import static com.internship.model.enums.Type.TASK;
import static com.internship.utils.UtilsForServices.changePosition;

public class UtilsForController {

    public static boolean access(Integer userId, IUserService userService, IInfoService infoService) {
        return !infoService.getCurrentUser().equals(userService.get(userId)) &&
                !infoService.getCurrentUser().getEmail().equals("admin@mail.ru");
    }

    public static void changeUrl(String page, String size, List<String> sort, List<String> filter, IInfoService infoService, Type type) {
        if (filter == null)
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
    }

    public static PageRequest createPageRequest(String page, String size, List<String> sort, List<String> filter, Integer id, Type type) {
        Integer position = Integer.valueOf(page);
        Integer pageSize = Integer.valueOf(size);
        position = changePosition(position, pageSize);
        if (id != null) {
            if (type.equals(TASK)) {
                filter.add("user:" + id);
            }
            if (type.equals(MESSAGE)) {
                filter.add("receiverUser:" + id);
            }
        }
        return new PageRequest(filter, sort, position, pageSize);
    }
}
