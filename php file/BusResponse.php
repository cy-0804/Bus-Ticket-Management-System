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

	$sql = "SELECT t.tripID, t.departureTime, t.arrivalTime, t.status, t.price,
				   b.busNum, b.plateNo,
				   s1.stationName AS fromStation,
				   s2.stationName AS toStation
			FROM trip t
			JOIN bus b ON t.busID = b.busID
			JOIN station s1 ON t.departureStationID = s1.stationID
			JOIN station s2 ON t.arrivalStationID = s2.stationID";

	$result = $dbConn->query($sql);

	$response = [];
	while ($row = $result->fetch_assoc()) {
		$response[] = $row;
	}

	$dbConn->close();
	echo json_encode($response);
?>
