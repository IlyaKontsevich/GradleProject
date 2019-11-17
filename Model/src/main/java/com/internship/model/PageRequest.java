package com.internship.model;


import java.util.List;
import java.util.Objects;

public class PageRequest {
    private List<String> filter;
    private List<String> sort;
    private Integer position;
    private Integer pageSize;

    public PageRequest(List<String> filter, List<String> sort, Integer position, Integer pageSize) {
        this.filter = filter;
        this.sort = sort;
        this.position = position;
        this.pageSize = pageSize;
    }

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageRequest that = (PageRequest) o;
        return Objects.equals(filter, that.filter) &&
                Objects.equals(sort, that.sort) &&
                Objects.equals(position, that.position) &&
                Objects.equals(pageSize, that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter, sort, position, pageSize);
    }
}
