package com.internship.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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
    @Column(name = "receiver_email")
    private String receiverEmail;

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDate sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(id, message1.id) &&
                Objects.equals(sendTime, message1.sendTime) &&
                Objects.equals(isRead, message1.isRead) &&
                Objects.equals(message, message1.message) &&
                Objects.equals(senderUser, message1.senderUser) &&
                Objects.equals(receiverUser, message1.receiverUser) &&
                Objects.equals(receiverEmail, message1.receiverEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sendTime, isRead, message, senderUser, receiverUser, receiverEmail);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sendTime=" + sendTime +
                ", isRead=" + isRead +
                ", message='" + message + '\'' +
                ", senderUser=" + senderUser +
                ", receiverUser=" + receiverUser +
                ", receiverEmail='" + receiverEmail + '\'' +
                '}';
    }
}
