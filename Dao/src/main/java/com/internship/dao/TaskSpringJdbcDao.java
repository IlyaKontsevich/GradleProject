package com.internship.dao;

import com.internship.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Deprecated
@Repository
public class TaskSpringJdbcDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskSpringJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(id SERIAL PRIMARY KEY, name varchar NOT NULL);");
        //jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS tasks(Id SERIAL PRIMARY KEY, name VARCHAR NOT NULL, deadLine DATE NOT NULL, userId INTEGER NOT NULL, FOREIGN KEY (userId) REFERENCES users (Id) ON DELETE CASCADE);");
    }

    public Task add(Task task) {
        if (0 != jdbcTemplate.queryForObject("Select COUNT(*) from tasks WHERE name = ? AND userid = ?", new Object[]{task.getName(), task.getUser().getId()}, Integer.class)) {
            return null;
        }
        jdbcTemplate.update("INSERT INTO  tasks (name, deadLine, userId,timeadd,priority,isdone) VALUES  (?,?,?,?,?,?)",
                task.getName(), task.getDeadLine(), task.getUser().getId(), task.getTimeAdd(), task.getPriority(), task.getIsDone());
        return jdbcTemplate.queryForObject("SELECT * FROM tasks WHERE name = ? AND userid = ?", new Object[]{task.getName(), task.getUser().getId()}, new TaskMapper());
    }

    public Task get(Integer id) {
        Task task;
        try {
            task = jdbcTemplate.queryForObject("SELECT * FROM tasks WHERE id = ?", new Object[]{id}, new TaskMapper());
        } catch (DataAccessException dataAccessException) {
            throw new RuntimeException(dataAccessException);
        }
        return task;
    }

    public Collection<Task> getPage(Integer position, Integer userId, Integer pageSize, List<String> sortType, List<String> filter) {
        Collection<Task> tasks = jdbcTemplate.query("Select * from tasks WHERE userid =" + userId + filter + sortType +
                " LIMIT '" + pageSize + "' OFFSET '" + position + "'", new TaskMapper());
        return tasks;
    }

    public Integer getSize(Integer userId) {
        Integer count = jdbcTemplate.queryForObject("Select COUNT (*) from tasks where userid = '" + userId + "'", Integer.class);
        return count;
    }

    public void update(Task task) {
        String sql = "update tasks set name = ?,deadline = ?,priority = ?,isdone = ? where id = ? ";
        jdbcTemplate.update(sql, task.getName(), task.getDeadLine(), task.getPriority(), task.getIsDone(), task.getId());
    }

    public Collection<Task> getAll(Integer userId) {
        Collection<Task> tasks = jdbcTemplate.query("Select * from tasks", new TaskMapper());
        return tasks;
    }


    public boolean delete(Integer id) {
        int rez = jdbcTemplate.update("DELETE FROM tasks WHERE id = ?", id);
        return rez != 0;
    }

    private class TaskMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Task.builder()
                    .deadLine(LocalDate.parse(rs.getString("dead_line")))
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .isDone(rs.getBoolean("is_done"))
                    .priority(rs.getString("priority"))
                    .timeAdd(LocalDate.parse(rs.getString("time_add")))
                    .build();
        }
    }
}
