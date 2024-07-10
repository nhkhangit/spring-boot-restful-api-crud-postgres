package vn.core.global.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import vn.core.global.configuration.DatabaseConfig;
import vn.core.global.entities.SharePeriod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SharePeriodDao {
    private static final Logger log = LoggerFactory.getLogger(SharePeriodDao.class);
    private final DatabaseConfig databaseConfig;

    public SharePeriodDao(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public List<SharePeriod> getAllSharePeriods() {
        List<SharePeriod> sharePeriods = new ArrayList<>();
        String sql = "SELECT share_id, user_id, shared_with_user_id, shared_date FROM Share_Period";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                sharePeriods.add(new SharePeriod(
                        rs.getInt("share_id"),
                        rs.getInt("user_id"),
                        rs.getInt("shared_with_user_id"),
                        rs.getDate("shared_date")
                ));
            }
        } catch (SQLException e) {
            log.error("Failed to fetch share periods", e);
        }
        return sharePeriods;
    }

    public SharePeriod getSharePeriodById(int shareId) {
        String sql = "SELECT share_id, user_id, shared_with_user_id, shared_date FROM Share_Period WHERE share_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shareId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new SharePeriod(
                            rs.getInt("share_id"),
                            rs.getInt("user_id"),
                            rs.getInt("shared_with_user_id"),
                            rs.getDate("shared_date")
                    );
                }
            }
        } catch (SQLException e) {
            log.error("Failed to fetch share period by ID", e);
        }
        return null;
    }

    public boolean createSharePeriod(SharePeriod sharePeriod) {
        String sql = "INSERT INTO Share_Period (user_id, shared_with_user_id, shared_date) VALUES (?, ?, ?)";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sharePeriod.getUserId());
            stmt.setInt(2, sharePeriod.getSharedWithUserId());
            java.util.Date now = new java.util.Date();
            stmt.setDate(3, new Date(now.getTime()));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to create share period", e);
            return false;
        }
    }

    public boolean updateSharePeriod(SharePeriod sharePeriod) {
        String sql = "UPDATE Share_Period SET user_id = ?, shared_with_user_id = ?, shared_date = ? WHERE share_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sharePeriod.getUserId());
            stmt.setInt(2, sharePeriod.getSharedWithUserId());
            java.util.Date now = new java.util.Date();
            stmt.setDate(3, new Date(now.getTime()));
            stmt.setInt(4, sharePeriod.getShareId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to update share period", e);
            return false;
        }
    }

    public boolean deleteSharePeriod(int shareId) {
        String sql = "DELETE FROM Share_Period WHERE share_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shareId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            log.error("Failed to delete share period", e);
            return false;
        }
    }
}