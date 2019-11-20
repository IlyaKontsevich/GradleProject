package com.internship.utils;

public class UtilsForServices {
    public static Integer changePosition(Integer position, Integer pageSize) {
        return (position != 1)
                ? position + (pageSize - 2)
                : position - 1;
    }
}
