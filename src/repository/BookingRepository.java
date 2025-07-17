package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

import dto.BookingRequest;
import util.DatabaseUtil;

public class BookingRepository {

    public int insertBooking(BookingRequest request) throws SQLException {
        int generatedBookingId = -1;
        String sql = "INSERT INTO booking (tripID, managedBy, bookedBy, bookingDate) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, request.getTripId());
            stmt.setInt(2, request.getStaffId());
            stmt.setInt(3, request.getCustomerId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedBookingId = rs.getInt(1);
                }
            }
        }

        return generatedBookingId;
    }

    public void insertBookingSeats(int bookingId, List<Integer> seatIds) throws SQLException {
        String sql = "INSERT INTO booking_seats (bookingID, seatID) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Integer seatId : seatIds) {
                stmt.setInt(1, bookingId);
                stmt.setInt(2, seatId);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
}
