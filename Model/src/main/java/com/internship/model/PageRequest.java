package com.internship.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageRequest {
    private List<String> filter;
    private List<String> sort;
    private Integer position;
    private Integer pageSize;
}
