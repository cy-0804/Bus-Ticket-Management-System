package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Trip;
import util.DatabaseUtil;

public class TripRepository {

    public List<Trip> getUpcomingTrips() throws SQLException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM trip WHERE departureTime >= NOW() ORDER BY departureTime ASC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trip trip = mapResultSetToTrip(rs);
                trips.add(trip);
            }
        }

        return trips;
    }

    // Get a specific trip by its ID
    public Trip getTripById(int tripId) throws SQLException {
        String sql = "SELECT * FROM trip WHERE id = ?";
        Trip trip = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tripId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    trip = mapResultSetToTrip(rs);
                }
            }
        }

        return trip;
    }

    private Trip mapResultSetToTrip(ResultSet rs) throws SQLException {
        Trip trip = new Trip();
        trip.setId(rs.getInt("id"));
        trip.setBusId(rs.getInt("bus_id"));
        trip.setDepartureStationID(rs.getInt("departure_station_id"));
        trip.setArrivalStationID(rs.getInt("arrival_station_id"));
        trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        trip.setStatus(rs.getString("status"));
        trip.setPrice(rs.getDouble("price"));
        return trip;
    }
}
