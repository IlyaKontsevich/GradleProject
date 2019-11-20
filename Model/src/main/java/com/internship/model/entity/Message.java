package com.internship.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "send_time")
    private LocalDate sendTime;
    @Column(name = "is_read")
    private Boolean isRead;
    @Column(name = "message")
    private String message;
    @OneToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderUser;
    @OneToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverUser;
    @Transient
    private String receiverEmail;
}
