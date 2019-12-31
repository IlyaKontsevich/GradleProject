package com.internship.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
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

    public Message() {
    }
}
