<?php
header("Content-Type: application/json");

$hostAddr = "localhost";
$dbName = "bus_ticket_management_system";
$dbUser = "root";
$dbPassword = ""; 
$dbPort = 3306;

$dbConn = new mysqli($hostAddr, $dbUser, $dbPassword, $dbName, $dbPort);
if ($dbConn->connect_error) {
    echo json_encode(["status" => "fail", "message" => "Database connection failed: " . $dbConn->connect_error]);
    exit;
}

$data = json_decode(file_get_contents("php://input"), true);
$booking_id = $data['booking_id'] ?? '';

if (empty($booking_id)) {
    echo json_encode(["status" => "fail", "message" => "Missing booking_id"]);
    exit;
}

$query = "
SELECT 
    t.tripID,
    p.name AS passengerName,
    s1.stationName AS from_station,
    s2.stationName AS to_station,
    t.departureTime,
    t.arrivalTime,
    b.plateNo,
    s.seatNumber,
    bs.checkin_status
FROM 
    booking_seats bs
JOIN 
    booking bkg ON bs.bookingID = bkg.bookingID
JOIN 
    passenger p ON bkg.bookedBy = p.passengerID
JOIN 
    trip t ON bkg.tripID = t.tripID
JOIN 
    station s1 ON t.departureStationID = s1.stationID
JOIN 
    station s2 ON t.arrivalStationID = s2.stationID
JOIN 
    bus b ON t.busID = b.busID
JOIN
	seat s ON s.seatID = bs.seatID
WHERE 
    bs.bookingID = ?
";

$stmt = $dbConn->prepare($query);
$stmt->bind_param("s", $booking_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    echo json_encode(["status" => "fail", "message" => "Booking not found"]);
} else {
    $data = $result->fetch_assoc();
    echo json_encode([
        "status" => "success",
        "message" => "Booking retrieved successfully",
        "data" => [
            "tripID" => $data['tripID'],
            "passengerName" => $data['passengerName'],
            "from" => $data['from_station'],
            "to" => $data['to_station'],
            "departureTime" => $data['departureTime'],
            "arrivalTime" => $data['arrivalTime'],
            "busPlate" => $data['plateNo'],
            "seatNo" => $data['seatNumber'],
            "checkinStatus" => $data['checkin_status']
        ]
    ]);
}

$stmt->close();
$dbConn->close();
?>
