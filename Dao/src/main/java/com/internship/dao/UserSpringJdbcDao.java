package com.internship.dao;

import com.internship.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Deprecated
@Repository
public class UserSpringJdbcDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserSpringJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        //jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(id SERIAL PRIMARY KEY, name varchar NOT NULL);");
    }

    public User add(User user) {
        if (0 != jdbcTemplate.queryForObject("Select COUNT(*) from users WHERE email = ?", new Object[]{user.getEmail()}, Integer.class)) {
            return null;
        }
        jdbcTemplate.update("INSERT INTO  users (name,age,email) VALUES  (?,?,?)", user.getName(), user.getAge(), user.getEmail());
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", new Object[]{user.getEmail()}, new UserMapper());
    }


    public User get(Integer id) {
        User user;
        try {
            user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new Object[]{id}, new UserMapper());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("Select * from users", new UserMapper());
        return users;
    }


    public List<User> getPage(Integer position, Integer pageSize, List<String> sortType, List<String> filter) {
        List<User> users = jdbcTemplate.query("Select * from users " + filter + sortType +
                " LIMIT '" + pageSize + "' OFFSET '" + position + "'", new UserMapper());
        return users;
    }


    public Integer getSize() {
        Integer count = jdbcTemplate.queryForObject("Select COUNT(*) from users", Integer.class);
        return count;
    }

    public void update(User user) {
        String sql = "update users set name = ?, age = ?, email = ? where id = ? ";
        jdbcTemplate.update(sql, user.getName(), user.getAge(), user.getEmail(), user.getId());
    }

    public boolean delete(Integer id) {
        int rez = jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
        return rez != 0;
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .age(rs.getInt("age"))
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .build();
        }
    }
}
