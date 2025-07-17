package repository;

import util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class CheckInRepository {

    //Insert a check-in record 
    public void checkInPassenger(String bookingId) throws SQLException {
        String sql = """
            INSERT INTO checkin (booking_id, checkin_time, status)
            VALUES (?, ?, 'checked-in')
        """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bookingId);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            stmt.executeUpdate();
        }
    }

    // ✅ Check if a passenger is already checked in
    public boolean isPassengerCheckedIn(String bookingId) throws SQLException {
        String sql = """
            SELECT COUNT(*) AS count
            FROM checkin
            WHERE booking_id = ? AND status = 'checked-in'
        """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        }

        return false;
    }

    // ✅ Optional: Cancel or reverse check-in (e.g., for last-minute cancellation)
    public void cancelCheckIn(String bookingId) throws SQLException {
        String sql = """
            UPDATE checkin
            SET status = 'not-checked-in'
            WHERE booking_id = ?
        """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bookingId);
            stmt.executeUpdate();
        }
    }
}
