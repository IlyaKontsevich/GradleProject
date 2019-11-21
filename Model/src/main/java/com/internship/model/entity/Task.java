package com.internship.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "tasks")
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

    public Task() {
    }
}
