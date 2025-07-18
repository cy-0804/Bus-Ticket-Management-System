<?php
header("Content-Type: application/json");
$db = new mysqli("localhost", "root", "", "bus_ticket_management_system", 3306);
$tripID = $_GET["tripID"];
$result = $db->query("SELECT seatID, seatNumber, status FROM seat WHERE tripID = $tripID");
$seats = [];
while ($row = $result->fetch_assoc()) $seats[] = $row;
echo json_encode($seats);
$db->close();
?>
