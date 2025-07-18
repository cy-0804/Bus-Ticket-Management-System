-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2025 at 01:28 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bus_ticket_management_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `bookingID` varchar(8) NOT NULL,
  `tripID` int(11) NOT NULL,
  `bookedBy` int(11) DEFAULT NULL,
  `managedBy` int(11) DEFAULT NULL,
  `paymentID` int(11) DEFAULT NULL,
  `bookingDate` datetime DEFAULT NULL,
  `totalPrice` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`bookingID`, `tripID`, `bookedBy`, `managedBy`, `paymentID`, `bookingDate`, `totalPrice`) VALUES
('BKG225', 2, 2, NULL, 2, '2025-07-18 06:27:21', 90.00),
('BKG476', 1, 1, NULL, 1, '2025-07-18 06:25:14', 90.00),
('BKG477', 1, NULL, 1, 3, '2025-07-18 07:20:39', 45.00);

-- --------------------------------------------------------

--
-- Table structure for table `booking_seats`
--

CREATE TABLE `booking_seats` (
  `bookingID` varchar(8) NOT NULL,
  `seatID` int(11) NOT NULL,
  `passengerID` int(11) DEFAULT NULL,
  `checkin_status` enum('checked-In','pending','missed','') NOT NULL DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking_seats`
--

INSERT INTO `booking_seats` (`bookingID`, `seatID`, `passengerID`, `checkin_status`) VALUES
('BKG225', 11, 3, 'pending'),
('BKG476', 1, 1, 'pending'),
('BKG476', 2, 2, 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `busID` int(11) NOT NULL,
  `busNum` varchar(20) NOT NULL,
  `plateNo` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`busID`, `busNum`, `plateNo`) VALUES
(1, 'BUS1001', 'WXY1234'),
(2, 'BUS2002', 'ABC2345'),
(3, 'BUS3003', 'JKL3456');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `userID` int(11) NOT NULL,
  `memberStatus` enum('member','non-member') DEFAULT NULL,
  `memberNo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`userID`, `memberStatus`, `memberNo`) VALUES
(1, 'member', 'M0001'),
(2, 'non-member', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

CREATE TABLE `passenger` (
  `passengerID` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `gender` enum('male','female') DEFAULT NULL,
  `telNo` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `passenger`
--

INSERT INTO `passenger` (`passengerID`, `name`, `gender`, `telNo`, `age`) VALUES
(1, 'Ali Bin Abu', 'male', '0123456789', 20),
(2, 'Mei Mei', 'female', '0182637162', 20),
(3, 'Yasmin', 'female', '0192738217', 40);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentID` int(11) NOT NULL,
  `paymentDate` datetime DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `paymentMethod` enum('cash','card','online') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentID`, `paymentDate`, `amount`, `paymentMethod`) VALUES
(1, '2025-07-18 06:25:14', 90.00, 'card'),
(2, '2025-07-18 06:27:21', 90.00, 'online'),
(3, '2025-07-18 07:20:39', 45.00, 'cash');

-- --------------------------------------------------------

--
-- Table structure for table `seat`
--

CREATE TABLE `seat` (
  `seatID` int(11) NOT NULL,
  `seatNumber` varchar(10) DEFAULT NULL,
  `tripID` int(11) DEFAULT NULL,
  `status` enum('available','booked') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seat`
--

INSERT INTO `seat` (`seatID`, `seatNumber`, `tripID`, `status`) VALUES
(1, 'A1', 1, 'booked'),
(2, 'A2', 1, 'booked'),
(3, 'B1', 1, 'available'),
(4, 'B2', 1, 'available'),
(5, 'C1', 1, 'available'),
(6, 'C2', 1, 'available'),
(7, 'D1', 1, 'available'),
(8, 'D2', 1, 'available'),
(9, 'A1', 2, 'available'),
(10, 'A2', 2, 'available'),
(11, 'B1', 2, 'booked'),
(12, 'B2', 2, 'available'),
(13, 'C1', 2, 'available'),
(14, 'C2', 2, 'available'),
(15, 'D1', 2, 'available'),
(16, 'D2', 2, 'available'),
(17, 'A1', 3, 'available'),
(18, 'A2', 3, 'available'),
(19, 'B1', 3, 'available'),
(20, 'B2', 3, 'available'),
(21, 'C1', 3, 'available'),
(22, 'C2', 3, 'available'),
(23, 'D1', 3, 'available'),
(24, 'D2', 3, 'available'),
(25, 'A1', 4, 'available'),
(26, 'A2', 4, 'available'),
(27, 'B1', 4, 'available'),
(28, 'B2', 4, 'available'),
(29, 'C1', 4, 'available'),
(30, 'C2', 4, 'available'),
(31, 'D1', 4, 'available'),
(32, 'D2', 4, 'available'),
(33, 'A1', 5, 'available'),
(34, 'A2', 5, 'available'),
(35, 'B1', 5, 'available'),
(36, 'B2', 5, 'available'),
(37, 'C1', 5, 'available'),
(38, 'C2', 5, 'available'),
(39, 'D1', 5, 'available'),
(40, 'D2', 5, 'available');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `userID` int(11) NOT NULL,
  `position` varchar(50) DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `status` enum('active','inactive') DEFAULT NULL,
  `stationID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`userID`, `position`, `department`, `status`, `stationID`) VALUES
(3, 'Station Manager', 'Operations', 'active', 1),
(4, 'Ticket Clerk', 'Front Desk', 'active', 2);

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `stationID` int(11) NOT NULL,
  `stationName` varchar(100) NOT NULL,
  `state` varchar(50) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `telNo` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`stationID`, `stationName`, `state`, `location`, `telNo`) VALUES
(1, 'Terminal Bersepadu Selatan', 'Wilayah Persekutuan', 'Kuala Lumpur', '03-12345678'),
(2, 'Melaka Sentral', 'Melaka', 'Melaka', '06-98765432'),
(3, 'Larkin Sentral', 'Johor', 'Johor Bahru', '07-5551234'),
(4, 'Penang Sentral', 'Penang', 'Butterworth', '04-2233445'),
(5, 'Ipoh Amanjaya', 'Perak', 'Ipoh', '05-3344556');

-- --------------------------------------------------------

--
-- Table structure for table `trip`
--

CREATE TABLE `trip` (
  `tripID` int(11) NOT NULL,
  `busID` int(11) NOT NULL,
  `departureStationID` int(11) NOT NULL,
  `arrivalStationID` int(11) NOT NULL,
  `departureTime` datetime DEFAULT NULL,
  `arrivalTime` datetime DEFAULT NULL,
  `status` enum('scheduled','boarding','departed','delayed') DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trip`
--

INSERT INTO `trip` (`tripID`, `busID`, `departureStationID`, `arrivalStationID`, `departureTime`, `arrivalTime`, `status`, `price`) VALUES
(1, 1, 1, 2, '2025-07-20 08:00:00', '2025-07-20 11:00:00', 'scheduled', 45.00),
(2, 2, 2, 3, '2025-07-21 09:30:00', '2025-07-21 12:30:00', 'scheduled', 90.00),
(3, 3, 1, 2, '2025-07-20 13:00:00', '2025-07-21 15:00:00', 'scheduled', 45.00),
(4, 2, 4, 5, '2025-07-22 17:00:00', '2025-07-22 18:30:00', 'scheduled', 30.00),
(5, 1, 4, 1, '2025-07-23 10:00:00', '2025-07-23 14:00:00', 'scheduled', 60.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phoneNo` varchar(20) DEFAULT NULL,
  `gender` enum('male','female') DEFAULT NULL,
  `icNo` varchar(20) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `userType` enum('customer','staff') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `fullname`, `email`, `phoneNo`, `gender`, `icNo`, `username`, `password`, `userType`) VALUES
(1, 'Ali Bin Abu', 'ali@example.com', '0123456789', 'male', '900101-14-5678', 'aliabu', 'pass123', 'customer'),
(2, 'Siti Aminah', 'siti@example.com', '0132223344', 'female', '880202-08-2233', 'siti88', 'siti@123', 'customer'),
(3, 'Encik Rosli', 'rosli@staff.com', '0175566778', 'male', '850404-05-3344', 'rosli85', 'admin321', 'staff'),
(4, 'Muthu Kumar', 'muthu@staff.com', '0199988776', 'male', '820505-10-5566', 'muthu82', 'muthu@456', 'staff'),
(5, 'chan chee chan', NULL, '0123456789', 'male', NULL, NULL, NULL, 'customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookingID`),
  ADD KEY `booking_ibfk_1` (`tripID`),
  ADD KEY `booking_ibfk_2` (`bookedBy`),
  ADD KEY `booking_ibfk_3` (`managedBy`),
  ADD KEY `booking_ibfk_4` (`paymentID`);

--
-- Indexes for table `booking_seats`
--
ALTER TABLE `booking_seats`
  ADD PRIMARY KEY (`bookingID`,`seatID`),
  ADD KEY `booking_seats_ibfk_2` (`seatID`),
  ADD KEY `booking_seats_ibfk_3` (`passengerID`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`busID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `passenger`
--
ALTER TABLE `passenger`
  ADD PRIMARY KEY (`passengerID`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentID`);

--
-- Indexes for table `seat`
--
ALTER TABLE `seat`
  ADD PRIMARY KEY (`seatID`),
  ADD KEY `seat_ibfk_1` (`tripID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`userID`),
  ADD KEY `staff_ibfk_2` (`stationID`);

--
-- Indexes for table `station`
--
ALTER TABLE `station`
  ADD PRIMARY KEY (`stationID`);

--
-- Indexes for table `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`tripID`),
  ADD KEY `trip_ibfk_1` (`busID`),
  ADD KEY `trip_ibfk_2` (`departureStationID`),
  ADD KEY `trip_ibfk_3` (`arrivalStationID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `busID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `passenger`
--
ALTER TABLE `passenger`
  MODIFY `passengerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `seat`
--
ALTER TABLE `seat`
  MODIFY `seatID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `station`
--
ALTER TABLE `station`
  MODIFY `stationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `trip`
--
ALTER TABLE `trip`
  MODIFY `tripID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`tripID`) REFERENCES `trip` (`tripID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`bookedBy`) REFERENCES `passenger` (`passengerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`managedBy`) REFERENCES `users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_ibfk_4` FOREIGN KEY (`paymentID`) REFERENCES `payment` (`paymentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `booking_seats`
--
ALTER TABLE `booking_seats`
  ADD CONSTRAINT `booking_seats_ibfk_1` FOREIGN KEY (`bookingID`) REFERENCES `booking` (`bookingID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_seats_ibfk_2` FOREIGN KEY (`seatID`) REFERENCES `seat` (`seatID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_seats_ibfk_3` FOREIGN KEY (`passengerID`) REFERENCES `passenger` (`passengerID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`tripID`) REFERENCES `trip` (`tripID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`stationID`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`busID`) REFERENCES `bus` (`busID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`departureStationID`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `trip_ibfk_3` FOREIGN KEY (`arrivalStationID`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
