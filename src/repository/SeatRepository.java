package repository;

import model.Seat;
import model.Trip;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatRepository {

    public List<Seat> getAvailableSeats(int tripId) throws SQLException {
        List<Seat> seats = new ArrayList<>();

        String sql = """
            SELECT seatID, seatNumber, status
            FROM seat
            WHERE tripID = ?
              AND status = 'available'
        """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tripId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatID(rs.getInt("seatID"));
                    seat.setSeatNumber(rs.getString("seatNumber"));
                    seat.setStatus(rs.getString("status"));

                    seats.add(seat);
                }
            }
        }

        return seats;
    }

    public void markSeatAsBooked(int seatId) throws SQLException {
        String sql = "UPDATE seat SET status = 'booked' WHERE seatID = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, seatId);
            stmt.executeUpdate();
        }
    }

    public void releaseSeat(int seatId) throws SQLException {
        String sql = "UPDATE seat SET status = 'available' WHERE seatID = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, seatId);
            stmt.executeUpdate();
        }
    }

    public void assignSeats(String bookingId, List<Integer> seatIds) throws SQLException {
        String sql = "INSERT INTO booking_seats (bookingID, seatID) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Integer seatId : seatIds) {
                stmt.setString(1, bookingId);
                stmt.setInt(2, seatId);
                stmt.addBatch();
                markSeatAsBooked(seatId);
            }

            stmt.executeBatch();
        }
    }
}
