<?php
header("Content-Type: application/json");
error_reporting(0); // Optional: hide warnings
ini_set('display_errors', 0);

$hostAddr = "localhost";
$dbName = "bus_ticket_management_system";
$dbUser = "root";
$dbPassword = ""; 
$dbPort = 3306;

$dbConn = new mysqli($hostAddr, $dbUser, $dbPassword, $dbName, $dbPort);

if ($dbConn->connect_error) {
    echo json_encode(["status" => "fail", "message" => "Database connection failed"]);
    exit;
}

$data = json_decode(file_get_contents("php://input"), true);

$username = $data['username'] ?? '';
$password = $data['password'] ?? '';

if (empty($username) || empty($password)) {
    echo json_encode(["status" => "fail", "message" => "Missing username or password"]);
    exit;
}

$sql = "SELECT * FROM users WHERE username = ?";
$stmt = $dbConn->prepare($sql);
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

if ($user = $result->fetch_assoc()) {
    if ($password === $user['password']) {
        echo json_encode([
            "status" => "success",
            "message" => "Login successful",
            "data" => [
                "user_id" => $user["userID"],
                "username" => $user["username"],
                "role" => $user["userType"]
            ]
        ]);
    } else {
        echo json_encode(["status" => "fail", "message" => "Invalid password"]);
    }
} else {
    echo json_encode(["status" => "fail", "message" => "User not found"]);
}

$stmt->close();
$dbConn->close();
?>
