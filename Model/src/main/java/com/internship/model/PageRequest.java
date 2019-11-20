package com.internship.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class PageRequest {
    private List<String> filter;
    private List<String> sort;
    private Integer position;
    private Integer pageSize;
}
