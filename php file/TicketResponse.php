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

// Read the JSON input
$input = file_get_contents("php://input");
$data = json_decode($input, true); // decode as associative array

if (!isset($data['userID'])) {
    echo json_encode(["status" => "fail", "message" => "No userID received"]);
    exit;
}

$userID = $data['userID'];

// Updated SQL: Added departureDate and arrivalDate
$sql = "
SELECT 
    b.bookingID, 
    t.tripID, 
    s.seatNumber, 
    s.seatID, 
    ds.stationName AS origin,
    asn.stationName AS destination,
    t.departureTime AS departureDate,
    t.arrivalTime AS arrivalDate,
    bu.plateNo,
    b.totalPrice
FROM booking b
JOIN booking_seats bs ON b.bookingID = bs.bookingID
JOIN seat s ON bs.seatID = s.seatID
JOIN trip t ON b.tripID = t.tripID
JOIN bus bu ON t.busID = bu.busID
JOIN station ds ON t.departureStationID = ds.stationID
JOIN station asn ON t.arrivalStationID = asn.stationID
WHERE b.bookedBy = ?
ORDER BY t.departureTime DESC
";


$stmt = $dbConn->prepare($sql);
$stmt->bind_param("i", $userID);
$stmt->execute();
$result = $stmt->get_result();

$tickets = [];
while ($row = $result->fetch_assoc()) {
    $tickets[] = $row;
}

$stmt->close();
$dbConn->close();

echo json_encode(["status" => "success", "data" => $tickets]);
?>
