package com.internship.utils;

import java.util.function.UnaryOperator;

public class UtilsForServices {
    public static Integer changePosition(Integer position, Integer pageSize) {
        UnaryOperator<Integer> changePosition = pos -> {
            if (pos != 1)
                pos += pageSize - 2;
            else
                pos -= 1;
            return pos;
        };
        return changePosition.apply(position);
    }
}
