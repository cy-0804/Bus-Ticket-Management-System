<?php
header("Content-Type: application/json");

$hostAddr = "localhost";
$dbName = "bus_ticket_management_system";
$dbUser = "root";
$dbPassword = "";
$dbPort = 3306;
$dbConn = new mysqli($hostAddr, $dbUser, $dbPassword, $dbName, $dbPort);

if ($dbConn->connect_error) {
    echo json_encode(["status" => "fail", "message" => "DB connection error"]);
    exit;
}

if (!isset($_GET['bookingID'])) {
    echo json_encode(["status" => "fail", "message" => "Missing bookingID"]);
    exit;
}

$bookingID = $_GET['bookingID'];

$sql = "SELECT 
            b.bookingID,
            s.seatID,
            seat.seatNumber,
            p.name,
            p.gender,
            p.telNo,
            p.age
        FROM booking_seats s
        JOIN booking b ON s.bookingID = b.bookingID
        JOIN passenger p ON s.passengerID = p.passengerID
        JOIN seat seat ON s.seatID = seat.seatID
        WHERE b.bookingID = ?";

$stmt = $dbConn->prepare($sql);
$stmt->bind_param("s", $bookingID);
$stmt->execute();
$result = $stmt->get_result();

$bookings = [];
while ($row = $result->fetch_assoc()) {
    $bookings[] = $row;
}

echo json_encode($bookings);

$stmt->close();
$dbConn->close();
?>