package com.internship.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Column(name = "name")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "age")
    private Integer age;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Transient
    private Collection<GrantedAuthority> authorities;

    public User() {
    }

    @Override
    public String getUsername() {
        return this.name;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(id, user.id) &&
                Objects.equals(age, user.age) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, age, password, email);
    }
}