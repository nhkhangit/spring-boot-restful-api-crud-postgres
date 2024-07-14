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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT User_id, email, gender, created_at, updated_at FROM \"User\"";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("User_id"),
                        rs.getString("email"),
                        null,
                        rs.getString("gender"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException e) {
            log.error("Failed to fetch Uer", e);
        }
        return users;
    }

    public User getUserById(int UerId) {
        String sql = "SELECT User_id, email, gender, created_at, updated_at FROM \"User\" WHERE User_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("User_id"),
                            rs.getString("email"),
                            null,
                            rs.getString("gender"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                }
            }
        } catch (SQLException e) {
            log.error("Failed to fetch Uer by ID", e);
        }
        return null;
    }

    public User getUserByEmail(String userEmail) {
        String sql = "SELECT User_id, email, password, gender, created_at, updated_at FROM \"User\" WHERE email LIKE ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + userEmail + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Hello World!");
                    System.out.println("Hello World!");
                    System.out.println("Hello World!");
                    return new User(
                            rs.getInt("User_id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("gender"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                }
            }
        } catch (SQLException e) {
            log.error("Failed to fetch Uer by email", e);
            return null;
        }
        return null;
    }

    public boolean createUser(User User) {
        String sql = "INSERT INTO \"User\" (email, password, gender) VALUES (?, ?, ?)";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, User.getEmail());
            stmt.setString(2, User.getPassword());
            stmt.setString(3, User.getGender());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to create Uer", e);
            return false;
        }
    }

    public boolean updateUser(User User) {
        String sql = "UPDATE \"User\" SET email = ?, gender = ?, updated_at = CURRENT_TIMESTAMP WHERE User_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, User.getEmail());
            stmt.setString(2, User.getGender());
            stmt.setInt(3, User.getUserId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to update User", e);
            return false;
        }
    }

    public boolean deleteUser(int UerId) {
        String sql = "DELETE FROM \"User\" WHERE User_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, UerId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to delete Uer", e);
            return false;
        }
    }
}
