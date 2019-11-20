package com.internship.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "info")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_url")
    private String userUrl;
    @Column(name = "task_url")
    private String taskUrl;
    @Column(name = "message_url")
    private String messageUrl;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
