<?php
header("Content-Type: application/json");
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

ini_set('display_errors', 0);
ini_set('log_errors', 1);
error_reporting(E_ALL);

// Database connection
$hostAddr = "localhost";
$dbName = "bus_ticket_management_system";
$dbUser = "root";
$dbPassword = "";
$dbPort = 3306;

$conn = new mysqli($hostAddr, $dbUser, $dbPassword, $dbName, $dbPort);
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["status" => "fail", "message" => "Database connection failed"]);
    exit;
}

$input = file_get_contents("php://input");
$data = json_decode($input, true);

$tripId = isset($data['tripID']) ? intval($data['tripID']) : 0;
$status = isset($data['status']) ? trim($data['status']) : '';

if ($tripId <= 0 || empty($status)) {
    http_response_code(400);
    echo json_encode(["status" => "fail", "message" => "Missing or invalid tripID or status"]);
    exit;
}

$allowedStatuses = ['scheduled', 'boarding', 'delayed', 'departed', 'cancelled'];
if (!in_array(strtolower($status), $allowedStatuses)) {
    http_response_code(400);
    echo json_encode(["status" => "fail", "message" => "Invalid status value"]);
    exit;
}

// Update the trip status
$stmt = $conn->prepare("UPDATE trip SET status = ? WHERE tripID = ?");
$stmt->bind_param("si", $status, $tripId);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo json_encode([
        "status" => "success",
        "message" => "Trip status updated successfully",
        "tripID" => $tripId,
        "newStatus" => $status
    ]);
} else {
    echo json_encode([
        "status" => "fail",
        "message" => "No trip was updated. Trip ID may not exist or status unchanged."
    ]);
}

$stmt->close();
$conn->close();
?>
