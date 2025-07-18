<?php
header("Content-Type: application/json");

$hostAddr = "localhost";
$dbName = "bus_ticket_management_system";
$dbUser = "root";
$dbPassword = ""; 
$dbPort = 3306;
$dbConn = new mysqli($hostAddr, $dbUser, $dbPassword, $dbName, $dbPort);

if ($dbConn->connect_error) {
    die(json_encode(["status" => "fail", "message" => "Database connection failed: " . $dbConn->connect_error]));
}

$data = json_decode(file_get_contents("php://input"), true);
$booking_id = $data['booking_id'] ?? '';

if (empty($booking_id)) {
    echo json_encode(["status" => "fail", "message" => "Missing booking_id"]);
    exit;
}

$query = "UPDATE booking_seats SET checkin_status = 'checked-in' WHERE bookingID = ?";
$stmt = $dbConn->prepare($query);
$stmt->bind_param("s", $booking_id);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Status updated to 'checked-in'"]);
} else {
    echo json_encode(["status" => "fail", "message" => "Database update failed"]);
}

$stmt->close();
$dbConn->close();
?>