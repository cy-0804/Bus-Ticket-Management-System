<?php
	header("Content-Type: application/json");

	$hostAddr = "localhost";
	$dbName = "bus_ticket_management_system";
	$dbUser = "root";
	$dbPassword = "";
	$dbPort = 3306;
	$dbConn = new mysqli($hostAddr, $dbUser, $dbPassword, $dbName, $dbPort);

	$data = json_decode(file_get_contents("php://input"));

	if (!$data) {
		echo json_encode(["status" => "fail", "message" => "No data received"]);
		exit;
	}

	$tripID = $data->tripID;
	$bookedBy = $data->bookedBy;
	$paymentMethod = $data->paymentMethod;
	$totalPrice = $data->totalPrice;
	$passengers = $data->passengers;

	$stmt = $dbConn->prepare("INSERT INTO payment (paymentDate, amount, paymentMethod) VALUES (NOW(), ?, ?)");
	$stmt->bind_param("ds", $totalPrice, $paymentMethod);
	$stmt->execute();
	$paymentID = $stmt->insert_id;
	$stmt->close();

	$bookingID = "BKG" . str_pad(rand(1, 999), 3, '0', STR_PAD_LEFT);

	$stmt = $dbConn->prepare("INSERT INTO booking (bookingID, tripID, bookedBy, paymentID, bookingDate, totalPrice) VALUES (?, ?, ?, ?, NOW(), ?)");
	$stmt->bind_param("siiid", $bookingID, $tripID, $bookedBy, $paymentID, $totalPrice);
	$stmt->execute();
	$stmt->close();

	foreach ($passengers as $p) {
		$stmt = $dbConn->prepare("INSERT INTO passenger (name, gender, telNo, age) VALUES (?, ?, ?, ?)");
		$stmt->bind_param("sssi", $p->name, $p->gender, $p->telNo, $p->age);
		$stmt->execute();
		$passengerID = $stmt->insert_id;
		$stmt->close();

		foreach ($p->seatIDs as $seatID) {
			$stmt = $dbConn->prepare("INSERT INTO booking_seats (bookingID, seatID, passengerID) VALUES (?, ?, ?)");
			$stmt->bind_param("sii", $bookingID, $seatID, $passengerID);
			$stmt->execute();
			$stmt->close();

			$stmt = $dbConn->prepare("UPDATE seat SET status='booked' WHERE seatID=?");
			$stmt->bind_param("i", $seatID);
			$stmt->execute();
			$stmt->close();
		}
	}

	echo json_encode([
		"status" => "success",
		"message" => "Booking successful",
		"bookingID" => $bookingID
	]);

	$dbConn->close();
?>
