package com.internship.model;


import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "dead_line")
    private LocalDate deadLine;
    @Column(name = "time_add")
    private LocalDate timeAdd;
    @Column(name = "priority")
    private String priority;
    @Column(name = "is_done")
    private Boolean isDone;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean done) {
        isDone = done;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(LocalDate timeAdd) {
        this.timeAdd = timeAdd;
    }

    public Task(String name) {
        this.name = name;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
