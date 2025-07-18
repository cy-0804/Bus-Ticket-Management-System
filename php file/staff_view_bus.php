<?php
header('Content-Type: application/json');
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Database connection
$host = "localhost";
$user = "root";
$pass = "";
$db = "bus_ticket_management_system";
$port = 3306;

$conn = new mysqli($host, $user, $pass, $db, $port);
if ($conn->connect_error) {
    echo json_encode(["status" => "fail", "message" => "Database connection failed"]);
    exit;
}

$busID = isset($_GET['busID']) ? $conn->real_escape_string($_GET['busID']) : '';
$date = isset($_GET['date']) ? $conn->real_escape_string($_GET['date']) : '';

if (empty($busID) || empty($date)) {
    echo json_encode(["status" => "fail", "message" => "Missing busID or date"]);
    exit;
}

$sql = "
    SELECT 
        trip.tripID, 
        trip.departureTime, 
        trip.arrivalTime, 
        departure.stationName AS fromStation, 
        arrival.stationName AS toStation, 
        trip.status
    FROM trip
    JOIN station AS departure ON trip.departureStationID = departure.stationID
    JOIN station AS arrival ON trip.arrivalStationID = arrival.stationID
    WHERE trip.busID = '$busID' AND DATE(trip.departureTime) = '$date'
";

$result = $conn->query($sql);

if (!$result) {
    echo json_encode(["status" => "fail", "message" => "Query failed: " . $conn->error]);
    exit;
}

$trips = [];
while ($row = $result->fetch_assoc()) {
    $trips[] = $row;
}

echo json_encode($trips);

$conn->close();
?>
