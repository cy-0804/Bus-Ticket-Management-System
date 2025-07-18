<?php
header("Content-Type: application/json");
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

$host = "localhost";
$user = "root";
$password = "";
$dbname = "bus_ticket_management_system";
$port = 3306;

$conn = new mysqli($host, $user, $password, $dbname, $port);

if ($conn->connect_error) {
    error_log("DB Connection Failed: " . $conn->connect_error);
    echo json_encode(["status" => "error", "message" => "Database connection failed."]);
    exit;
}

$data = json_decode(file_get_contents("php://input"), true);

if (json_last_error() !== JSON_ERROR_NONE) {
    echo json_encode(["status" => "error", "message" => "Invalid JSON input."]);
    exit;
}

if (!isset($data['tripID'])) {
    echo json_encode(["status" => "error", "message" => "tripID is required."]);
    exit;
}

$tripID = intval($data['tripID']);

try {
    $stmt = $conn->prepare("SELECT seatID, seatNumber, status FROM seat WHERE tripID = ? AND status = 'available'");
    $stmt->bind_param("i", $tripID);
    $stmt->execute();
    $result = $stmt->get_result();

    $seats = [];
    while ($row = $result->fetch_assoc()) {
        $seats[] = $row;
    }

    $stmt->close();
    $conn->close();

    echo json_encode(["status" => "success", "seats" => $seats]);

} catch (Exception $e) {
    error_log("Failed to retrieve seats: " . $e->getMessage());
    echo json_encode(["status" => "error", "message" => "Failed to retrieve seats: " . $e->getMessage()]);
}
?>