<?php
header("Content-Type: application/json");
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT); 

$host = "localhost";
$dbname = "bus_ticket_management_system";
$user = "root";
$pass = "";
$port = 3306;

// Connect to MySQL database
$conn = new mysqli($host, $user, $pass, $dbname, $port);
if ($conn->connect_error) {
    echo json_encode([
        "status" => "error",
        "message" => "Database connection failed: " . $conn->connect_error
    ]);
    exit;
}

$origin = isset($_GET['origin']) ? trim($_GET['origin']) : '';
$destination = isset($_GET['destination']) ? trim($_GET['destination']) : '';
$date = isset($_GET['date']) ? trim($_GET['date']) : '';

if (empty($origin) || empty($destination) || empty($date)) {
    echo json_encode([
        "status" => "error",
        "message" => "Missing required parameters"
    ]);
    $conn->close();
    exit;
}

$sql = "SELECT 
    t.tripID,
    s1.stationName AS fromStation,
    s2.stationName AS toStation,
    t.departureTime,
    t.arrivalTime,
    b.plateNo,
    t.price,
    COUNT(se.seatID) AS availableSeats
FROM trip t
JOIN station s1 ON t.departureStationID = s1.stationID
JOIN station s2 ON t.arrivalStationID = s2.stationID
JOIN bus b ON t.busID = b.busID
LEFT JOIN seat se ON t.tripID = se.tripID AND se.status = 'available'
WHERE s1.stationName = ? 
  AND s2.stationName = ?
  AND DATE(t.departureTime) = ?
GROUP BY t.tripID";

$stmt = $conn->prepare($sql);
if (!$stmt) {
    echo json_encode([
        "status" => "error",
        "message" => "SQL prepare failed: " . $conn->error
    ]);
    $conn->close();
    exit;
}

$stmt->bind_param("sss", $origin, $destination, $date);
$stmt->execute();
$result = $stmt->get_result();

$trips = [];
while ($row = $result->fetch_assoc()) {
    $trips[] = $row;
}

$stmt->close();
$conn->close();

echo json_encode([
    "status" => "success",
    "message" => count($trips) > 0 ? "Trips found" : "No trips found",
    "trips" => $trips
]);
exit;
