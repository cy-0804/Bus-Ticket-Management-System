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
    echo json_encode([
        "status" => "error",
        "message" => "Database connection failed: " . $conn->connect_error
    ]);
    exit;
}

$data = json_decode(file_get_contents("php://input"), true);

if (json_last_error() !== JSON_ERROR_NONE) {
    error_log("JSON Decode Error: " . json_last_error_msg());
    echo json_encode(["status" => "error", "message" => "Invalid JSON input."]);
    exit;
}

$required_keys = ["name", "gender", "phone", "age", "paymentMethod", "totalAmount", "tripID", "userID", "selectedSeats"];
foreach ($required_keys as $key) {
    if (!isset($data[$key])) {
        error_log("Missing POST parameter: " . $key);
        echo json_encode(["status" => "error", "message" => "Missing required parameter: " . $key]);
        exit;
    }
}

$name = $conn->real_escape_string($data["name"]);
$gender = $conn->real_escape_string($data["gender"]);
$phone = $conn->real_escape_string($data["phone"]);
$age = intval($data["age"]);
$paymentMethod = $conn->real_escape_string($data["paymentMethod"]);
$totalAmount = floatval($data["totalAmount"]);
$tripID = intval($data["tripID"]);
$managedBy = intval($data["userID"]);
$selectedSeats = $data["selectedSeats"]; // This will be an array of seat objects {seatID, seatNumber}

error_log("PHP Input - Name: $name, Gender: $gender, Phone: $phone, Age: $age, PaymentMethod: $paymentMethod, TotalAmount: $totalAmount, TripID: $tripID, ManagedBy (UserID): $managedBy, Selected Seats: " . json_encode($selectedSeats));

try {
    $conn->begin_transaction();

    // 1. Insert into passenger table
    $stmtPassenger = $conn->prepare("INSERT INTO passenger (name, gender, telNo, age) VALUES (?, ?, ?, ?)");
    $stmtPassenger->bind_param("sssi", $name, $gender, $phone, $age);
    $stmtPassenger->execute();
    $passengerID = $conn->insert_id;
    $stmtPassenger->close();
    error_log("Generated passengerID: " . $passengerID);

    // 2. Insert into payment table
    $stmtPayment = $conn->prepare("INSERT INTO payment (paymentDate, amount, paymentMethod) VALUES (NOW(), ?, ?)");
    $stmtPayment->bind_param("ds", $totalAmount, $paymentMethod);
    $stmtPayment->execute();
    $paymentID = $conn->insert_id;
    $stmtPayment->close();
    error_log("Generated paymentID: " . $paymentID);

    // 3. Generate bookingID
    $resBooking = $conn->query("SELECT bookingID FROM booking ORDER BY bookingID DESC LIMIT 1");
    if ($resBooking->num_rows > 0) {
        $lastBookingID = $resBooking->fetch_assoc()["bookingID"];
        $bookingNum = intval(substr($lastBookingID, 3)) + 1;
        $bookingID = "BKG" . str_pad($bookingNum, 3, "0", STR_PAD_LEFT);
    } else {
        $bookingID = "BKG001";
    }
    error_log("Generated bookingID: " . $bookingID);

    // 4. Insert into booking table
    error_log("Booking Values before INSERT: bookingID=$bookingID, tripID=$tripID, bookedBy (passengerID)=$passengerID, managedBy (userID)=$managedBy, paymentID=$paymentID, totalAmount=$totalAmount");

    $stmtBooking = $conn->prepare("INSERT INTO booking (bookingID, tripID, bookedBy, managedBy, paymentID, bookingDate, totalPrice)
                                 VALUES (?, ?, ?, ?, ?, NOW(), ?)");
    $stmtBooking->bind_param("siiiid", $bookingID, $tripID, $passengerID, $managedBy, $paymentID, $totalAmount);
    $stmtBooking->execute();
    $stmtBooking->close();

    // 5. Update seat status and insert into booking_seats for each selected seat
    $stmtUpdateSeat = $conn->prepare("UPDATE seat SET status = 'booked' WHERE seatID = ?");
    $stmtBookingSeats = $conn->prepare("INSERT INTO booking_seats (bookingID, seatID, passengerID, checkin_status) VALUES (?, ?, ?, 'pending')");

    foreach ($selectedSeats as $seat) {
        $seatID = intval($seat['seatID']);

        // Update seat status
        $stmtUpdateSeat->bind_param("i", $seatID);
        $stmtUpdateSeat->execute();
        if ($stmtUpdateSeat->affected_rows === 0) {
            throw new Exception("Failed to update status for seatID: " . $seatID . ". It might already be booked or not exist.");
        }
        error_log("Updated seat status for seatID: " . $seatID);

        // Insert into booking_seats
        $stmtBookingSeats->bind_param("sii", $bookingID, $seatID, $passengerID);
        $stmtBookingSeats->execute();
        error_log("Inserted into booking_seats for bookingID: $bookingID, seatID: $seatID, passengerID: $passengerID");
    }

    $stmtUpdateSeat->close();
    $stmtBookingSeats->close();

    $conn->commit();

    error_log("Transaction committed successfully.");
    echo json_encode([
        "status" => "success",
        "bookingID" => $bookingID,
        "passengerID" => $passengerID,
        "paymentID" => $paymentID,
        "message" => "Booking successful!"
    ]);

} catch (Exception $e) {
    $conn->rollback(); // Rollback transaction on error
    error_log("Booking Failed (Exception caught): " . $e->getMessage() . " on line " . $e->getLine() . " in " . $e->getFile());
    if ($conn->errno) {
        error_log("MySQL Error No: " . $conn->errno . ", MySQL Error: " . $conn->error);
    }
    echo json_encode([
        "status" => "error",
        "message" => "Booking failed: " . $e->getMessage() . " (DB error: " . $conn->error . ")"
    ]);
} finally {
    $conn->close();
}
?>