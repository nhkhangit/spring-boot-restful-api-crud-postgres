package vn.core.global.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import vn.core.global.configuration.DatabaseConfig;
import vn.core.global.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private final DatabaseConfig databaseConfig;

    public UserDao(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public List<User> getAllUsers(int page) {
        return getAllUsers(page, 10);
    }

    public List<User> getAllUsers(int page, int pageSize) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users OFFSET ? LIMIT ?";
        int offset = (page - 1) * pageSize;

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to fetch users from the database", e);
        }
        return users;
    }
}