
## **Project Member**

1. ANG CHA YAN (B032310478 S1G1)
https://github.com/cy-0804
2. NATALIE TAN WEI MEI (B032310844 S1G1)
https://github.com/nataalietan
3. HO XIN YONG (B032310518 S1G1)
https://github.com/xinyong03
4. CHAI YI JING (B032310827 S1G1)
https://github.com/Jinggggg030
5. LOH JIA YING (B032310401 S1G2)
https://github.com/EmylShamo
6. LIM ZHI YING (B032310439 S1G2)
https://github.com/ZYLIM03

Lecturer :Dr Mohd Hariz Bin Naim @ Mohayat

## **Project Overview**

## Introduction

The Bus Ticketing System is a distributed application which is designed to manage the booking process of bus tickets for both passengers and staff. The system consists of two main applications separated by roles which are Customer Application and Staff Application. Both applications are communicated through the backend application which is developed in PHP that can interact with databases. Besides, the system also uses DocSpring API to generate boarding passes for the passengers.

Frontend applications are built with Java Swing while the backend is PHP and MySQL. The system emphasizes role-based access control, data validation and a smooth communication between components through RESTful API.

The system has offered self-service functions for the customer such as searches for bus trips and booking tickets. While the staff can manually book the ticket for the customer, check-in passengers and view the bus schedule.


Features:  

-Role-based login for customers and staff 

-Online bus searching and booking interface for passengers

-Ticket generation for passengers

-Manual booking and passengers check-in system for staff

-Seat availability and status updates for staff

-Real-time bus status updates for staff



## Technologies Used

 -   Frontend: Java Swing Designer
       
   -   Backend: php, phpMyAdmin, My SQL (Database)
       
   -   Webservices: RESTful API (to get data and update data)
       
   -   Communication: HTTP GET/POST via HttpURLConnection
       
   -   Security Features: SQL Injection, Role-based Control
       
   -   External Integration: Docspring API for boarding pass pdf generation,

    
## Features

Two front-end apps, which are separated by role, which is the staff app and the customer app.



**Customer App:**

  

**1. Search Bus**

  

Customer enters trip details and a list of available trips matching the criteria is displayed to the user.

  

**2. Ticket Booking and Payment**

  

The customer selects a trip from the search results, and the available seats will be displayed. Users are allowed to book one or more seats. The customer needs to fill in passenger details and select a payment method. The frontend sends the booking data to the backend API, which saves the booking and seat assignment in the database. The system responds with a confirmation and booking ID.

  

**3. View Ticket**

  

The customer proceeds to payment (this payment function is only the simulated function). Once payment is successful, the backend updates the payment status of the booking. A receipt or ticket (PDF/boarding pass) can be generated and downloaded or emailed.

  
  

**Staff App**

  

**1. Manual Ticket Booking**

A staff member can manually create a booking for walk-in customers by entering trip details and passenger information. The system displays seat availability, and the booking is stored in the database similarly to customer booking.

  

**2. Passenger Check-In**

The staff enters the booking ID. If the current time is before departure, the booking is  marked as “checked-in”. A boarding pass is generated using DocsSpring API

  

**3. View Bus Schedule**

The staff can view all scheduled trips for a given bus and date. The system will display a list of trips including departure/arrival times and status. Staff can update the bus status. For example, from “scheduled” to “boarding”, “departed”, “delayed”).

**Third Party Integration**

The system uses RESTful APIs developed in PHP to enable communication between client applications with the MySQL database. Tools like Postman are used for API testing, while XAMPP will provide a local Apache-MySQL server environment. These integrations ensure modularity and allow the frontend applications to interact with backend services efficiently. DocSpring API also used to generate and download the boarding pass for passengers in PDF format.

## **System Architecture**
**High Level Diagram**
<img width="940" height="581" alt="image" src="https://github.com/user-attachments/assets/787d085b-7458-4942-8974-af478dae4ef0" />



**Backend Application**

**Technology Stack**

| **COMPONENT** | **TECHNOLOGY** |
| --- | --- |
| Programming Language | PHP ||
| Database | MySQL ||
| Web Server | Apache (via XAMPP) ||
| Development Tool | XAMPP ||
| Data Format | JSON (for input/output) ||

**Api Documentation**

* List of API endpoints, HTTP method and Required Request Parameters

| **API ENDPOINT (PHP)** | **METHOD** | **REQUIRED PARAMETERS** |
| --- | --- | --- |
| /login.php | POST | username, password ||
| /BusResponse.php | GET | bookingID (query) ||
| /BookingSeatRequest.php | GET | tripID (query) ||
| /BookingRequest.php | POST | tripID, bookedBy, paymentMethod, totalPrice, passengers\[\] ||
| /BookingResponse.php | GET | bookingID (query) ||
| /TicketResponse.php | POST | userID ||
| /staff_book_ticket.php | GET | origin, destination, date (query) ||
| /get_available_seat.php | POST | tripID ||
| /confirm_payment.php | POST | name, gender, phone, age, paymentMethod, totalAmount, tripID, userID, selectedSeats\[\] ||
| /get_booking_info.php | POST | booking_id ||
| /checkin(1).php | POST | booking_id || 
| /staff_view_bus.php | GET | busID, date (query) ||
| /bus_status_update.php | POST | tripID, status ||

* Header and body formats (with JSON examples)

| **API ENDPOINT (PHP)** | **HEADER** | **BODY FORMATS/**<br><br>**URL EXAMPLE** |
| --- | --- | --- |
| /login.php | Content-Type: application/json | json { "username": "aliabu", "password": "pass123" } ||
| /BusResponse.php | None | /BusResponse.php?bookingID=BKG001 ||
| /BookingSeatRequest.php | None | /BookingSeatRequest.php?tripID=TRP001 ||
| /BookingRequest.php | Content-Type: application/json | json { "tripID": 1, "bookedBy": 1, "paymentMethod": "cash", "totalPrice": 45.0, "passengers": \[{ "name": "Ahmad Nazri", "gender": "male", "telNo": "0123456789", "age": 25, "seatIDs": \[A3\] }\] } ||
| /BookingResponse.php | None | /BookingResponse.php?bookingID=BKG001 ||
| /TicketResponse.php | Content-Type: application/json | json { "userID": 1 } ||
| /staff_book_ticket.php | None | /staff_book_ticket.php?origin=TBS&destination=Melaka&date=2025-07-20 ||
| /get_available_seat.php | Content-Type: application/json | json { "tripID": 1 } ||
| /confirm_payment.php | Content-Type: application/json | json { "name": "Ahmad Nazri", "gender": "male", "phone": "0123456789", "age": 25, "paymentMethod": "cash", "totalAmount": 45.0, "tripID": 1, "userID": 3, "selectedSeats": \[ { "seatID": A3 } \] } ||
| /get_booking_info.php | Content-Type: application/json | json { "booking_id": "BKG001" } ||
| /checkin(1).php | Content-Type: application/json | json { "booking_id": "BKG001" } ||
| /staff_view_bus.php | None | /staff_view_bus.php?busID=BUS001&date=2025-07-20 ||
| /bus_status_update.php | Content-Type: application/json | json { "tripID": 1, "status": "departed" } ||

* Example of success and error responses

 /checkin.php

| Type | HTTP Status | JSON Response |
| --- | --- | --- |
| Success | 200 OK | { "status": "success", "message": "Status updated to 'checked-in'" } | |
| Error: Missing booking_id | 400 Bad Request | { "status": "fail", "message": "Missing booking_id" } ||
| Error: Database Connection Error | 500 Internal Server Error | { "status": "fail", "message": "Database connection failed: \[error\]" } ||
| Error: Update Failure | 200 OK | { "status": "fail", "message": "Database update failed" } ||

/staff_view_bus.php

| Type | HTTP Status | JSON Response |
| --- | --- | --- |
| Success | 200 OK | \[ { "tripID": "T001", "departureTime": "...", "arrivalTime": "...", "fromStation": "...", "toStation": "...", "status": "..." }, ... \] ||
| Error: Missing Parameters | 400 Bad Request | { "status": "fail", "message": "Missing busID or date" } ||
| Error: Database Connection Error | 500 Internal Server Error | { "status": "fail", "message": "Database connection failed" } ||
| Error: Query Error | 500 Internal Server Error | { "status": "fail", "message": "Query failed: \[error\]" } | |

/bus_status_update.php

| Type | HTTP Status | JSON Response |
| --- | --- | --- |
| Success | 200 OK | { "status": "success", "message": "Trip status updated successfully", "tripID": 12, "newStatus": "boarding" } ||
| Error: Missing or Invalid Input | 400 Bad Request | { "status": "fail", "message": "Missing or invalid tripID or status" } ||
| Error: Invalid Status Value | 400 Bad Request | { "status": "fail", "message": "Invalid status value" } ||
| Error: No Update | 200 OK | { "status": "fail", "message": "No trip was updated. Trip ID may not exist or status unchanged." } ||
| Error: Database Connection Error | 500 Internal Server Error | { "status": "fail", "message": "Database connection failed" } ||

**4\. Security**

1. Prepared Statements (SQL Injection Protection)

Description: SQL queries use prepared statements with bind_param() to securely inject variables into queries.

Explanation: Prevents SQL injection attacks.

Example: $stmt = $dbConn->prepare($sql);

$stmt->bind_param("s", $username);

1. Input Validation

Description: Checks whether required fields like username, password, or booking_id are empty.

Explanation: Prevents null or malformed data from being processed.

Example: if (empty($username)

1. Error Reporting Turned Off

Description: Error messages are suppressed via error_reporting(0) and ini_set().

Explanation: Prevents internal error details from being exposed to users, which is good practice.

Example: error_reporting(0) and ini_set().



## **Frontend Application**
## 1. Customer Application
**Purpose**
This application’s main function is to allow customer to interact with system for self service ticketing. To support this service, key functionalities has been implemented which included:

* User authentication
* Searching and view available bus trips
* Selecting preferred seats.
* Make ticket booking
* Make payment
* Generate ticket

**Technology Stack**

| **COMPONENT** | **TECHNOLOGY** |
| --- | --- |
| Programming Language | Java ||
| Graphical User Interface (GUI) Framework | Java Swing ||
| Event Handling | ActionListener ||
| Networking for API communication | Return JSON message via Http connection | |
| Database | phpMyAdmin, MySQL ||
| Security | Input validation handling for empty field and role based control. ||


**API Integration**
| **FUNCTIONALITY** | **METHOD** | **API ENDPOINT (PHP)** | **DESCRIPTION** |
| --- | --- | --- | --- |
| Login | POST | /login.php | Allow password authentication by validating credential entered by customer with a database and bring the customer to their dashboard based on role. When the API call success, the API will return the status whether success or failed. ||
| Search Available Bus | GET | /BusResponse.php | Allows customers to search for available bus trips based on departure station, arrival station, and date. When user enter the desired departure and arrival station, the system send the request to API and API return the list of scheduled bus trips ||
| Selecting Seats | GET | /BookingSeatRequest.php | Allow customers to select their desired seat based on their selected bus. API return the list of available buses when system requests. | |
| Booking Ticket | POST | /BookingRequest.php | Allow customers to submit the booking detail for selected trips and seat and personal information needed. API return the status success or failed when the system sends a request to upload the booking details to database. ||
| Make payment | GET | /BookingResponse.php | Simulate the payment by adding payment record to the booking submitted. API return the payment status to system when the booking is successfully submitted in database. | |
| View ticket | POST | /TicketResponse.php | Allow customer to view their booking detail. API will return the JSON object that contain list of booking detail and system generate the ticket contain booking details using java extension. ||

## 2. Counter Staff Application

**Purpose**
The counter staff application serves as the main interface for the counter staff at certain stations to perform key ticketing operations. With this application, the counter staff are able to perform tasks including:
* Log into their account and selected task to be perform from the dashboard
* View customers booking details 
* Search and view for available trips upon customers requests
* Manually book bus tickets for walk-in customers
* Proceed to payment and booking confirmation
* Check in the passenger for boarding
* Generate a boarding pass for the customers who have checked in
* Update the bus trip status, such as "departed," "delayed," "scheduled," and "boarding."

A user-friendly and intuitive user interface is developed for each functionality included in the application in order to ensure that staff can perform the operations in a timely manner. The interfaces also appear to be simple yet well structured so that the manual errors can be minimized and help improve the productivity.

**Technology Stack**
Several technologies are applied to the application development:
| **COMPONENT** | **TECHNOLOGY** |
| --- | --- |
| Programming Language | Java ||
| Graphical User Interface (GUI) Framework | Java Swing ||
| Event Handling | ActionListener ||
| JSON Parsing | org.json library ||
| Networking for API communication | Java HttpURLConnection ||
| Database | phpMyAdmin, MySQL ||

**API Integration**
| **FUNCTIONALITY** | **METHOD** | **API ENDPOINT (PHP)** | **DESCRIPTION** |
| --- | --- | --- | --- |
| Login | POST | **/**login.php | Handles user authentication where the backend application receives login credentials from the user and validates the credentials. After that, it returns a JSON response indicating login success or failure, along with requested user data. ||
| Trip Search | GET | /staff_book_ticket.php | When the frontend application makes a fetch trip request, the backend application returns a list of available trips based on the selected destination and departure date. ||
| Manual Book Ticket | POST | /get_available_seat.php | The backend application will retrieve the number of seats available and their seat number based on the trip the customer chooses. ||
|| POST | /confirm_payment.php | The counter staff will manually enter the customer’s details, their selection of seat, and payment method. These data will be passed to the backend application and will update the database. ||
| Check in walk-in customer | POST | /get_booking_info.php | The counter staff inserts the booking ID of the customer, then the backend application will retrieve the booking details for that particular booking and return to the frontend application. ||
|| POST | /checkin(1).php | Handles the check-in status update upon clicking the “Check-In” button. ||
| View bus schedule | GET | /staff_view_bus.php | The bus details, like bus ID and the departure date, are passed to the backend application. The backend application retrieves the scheduled trip of that bus and returns the related trip details back to the front-end application. ||
| Update trip status | POST | /bus_status_update.php | Handles the bus status update upon clicking the “Update Status” button. ||

**External API Integration**  

| **FUNCTIONALITY** |     | **API ENDPOINT** | **DESCRIPTION** |
| --- | --- | --- | --- |
| Generate boarding pass | **Create submission:** POST<br><br>**Submission Status:** GET<br><br>**Download PDF:** GET | <https://api.docspring.com/api/v1/submissions/> | The counter staff integrates with the DocSpring API to generate a boarding pass after checking in the passengers. Trip and passenger details are passed to DocSpring’s REST API upon clicking the “>>Print Boarding Pass” button, and once the API finishes checking the document’s generation status, the boarding pass in PDF is downloaded. ||

## **Interface Design**

Login Page

<img width="825" height="566" alt="image" src="https://github.com/user-attachments/assets/82712482-6bf4-464a-b1c8-0cfb7dea8cbf" />


Customer Dashboard

 <img width="848" height="567" alt="image" src="https://github.com/user-attachments/assets/2b1e4344-0582-48bf-a8a8-87c70b4f599d" />


Customer Booking Page

 <img width="940" height="877" alt="image" src="https://github.com/user-attachments/assets/c47601b4-0472-44b9-8a20-2357445b4537" />

 <img width="940" height="679" alt="image" src="https://github.com/user-attachments/assets/f800937f-19cb-47c4-bc98-3b768f61d315" />

<img width="940" height="675" alt="image" src="https://github.com/user-attachments/assets/91a1a99b-bb82-4237-bb0e-0464b85dabd8" />


Customer View Ticket Page
 
<img width="940" height="429" alt="image" src="https://github.com/user-attachments/assets/93855627-29b0-48ca-a96d-dc4dcff64c7f" />


Customer Payment Page
 
<img width="640" height="756" alt="image" src="https://github.com/user-attachments/assets/33099d5f-4661-4337-96f9-07798c4f8380" />


 
Staff Dashboard

 <img width="940" height="492" alt="image" src="https://github.com/user-attachments/assets/cf0e222b-d70d-4332-84c8-ae4c3ca1851f" />


Staff Manual Booking Page

 <img width="798" height="651" alt="image" src="https://github.com/user-attachments/assets/b0ac3d06-6e5a-4821-9116-87086e38c048" />


Staff Passenger Check-in Page
 
<img width="860" height="444" alt="image" src="https://github.com/user-attachments/assets/93e93b34-f4d3-4fd3-9c8d-8a992eb0737e" />

Boarding Pass Template:
 
<img width="913" height="293" alt="image" src="https://github.com/user-attachments/assets/f8bf64b1-f124-4285-885a-2008f38660cc" />

Staff View Bus Schedule
 
<img width="854" height="472" alt="image" src="https://github.com/user-attachments/assets/23c682b9-0677-470e-9a79-ae11f465c22e" />


## **Database Design**

**Entity-Relationship Diagram (ERD)**
<img width="940" height="1185" alt="image" src="https://github.com/user-attachments/assets/50762485-a402-4483-afa5-10258245861f" />

**Schema Justification**
The database schema for Bus Ticketing System was designed to model a real-world bus booking process in a normalized structure, ensuring data integrity and role separation to support the functionalities of passengers and staff.

* User Management
The tables of users or the role-based tables like customers and staff are used to store the user credentials and role specific attributes such as name, gender and age.
This role-based separation can ensure the proper access control from passengers and staff.
This information will be referenced by table like booking_seats.
* Trip and Bus Management
The table like bus will store bus details such as bus id and plate number while the trip will define the scheduled trips with departure and arrival details.
The trip table will link to seats and booking tables so the seat availability can be tracked.
The trip table also links to the bus table to enable the search functionality.
* Booking and Seats Allocation
The booking table will record the bookings made by staff or customers while the seat table will show the seats availability for every trip.
The booking table which links to the trip and passenger table can track the booking by referencing these tables like bookedBy and tripID.
The seats table that supports the seat availability in both passengers and staff UI can prevent double-booking by validating seat status before booking.
* Station and Bus
These two tables store the data like location and plate number for trip routes and bus assignments.
They are referenced by table like trip and staff to assign trips to physical location.


**Data Validation:**
1. Frontend Application

| **Field** | **Validation Rules** |
| --- | --- |
| Phone Number | Must be numeric, must be unique ||
| Email | Check not empty, must be unique ||
| Gender | Must be selected (male/female dropdown) ||
| IC No | Must be unique ||
| Username | Should be unique via check in DB ||
| Password | Not empty, enforce min 6 characters ||
| Member Status | Must be 'member' or 'non-member' ||
| Member No | Only required if 'member' is selected; otherwise NULL ||
| Department | Staff department can’t be empty ||
| Status | Staff status: active, inactive;Seat status: available, booked; Trip Status: Scheduled, Departed, Arrival, Delayed; Check-In status: Pending, Checked-in, Missed ||
| Station | Must be selected from dropdown list of stations ||

2. Backend Application

| **Field** | **Validation Rules** |
| --- | --- |
| bookingID format | Must follow BKGxxx format, validated in PHP ||
| bus plate number | Must follow BUSxxx format, example BUS001 ||
| check-in status | If check-in time > trip departureTime, set to 'missed' ||
| seat status | Cannot book a seat if status is 'booked' ||
| payment method | Must be one of 'cash', 'card', 'online' ||
| Date fields | Validate datetime format in YYYY-MM-DD hh:mm:ss ||
| FK integrity | Ensure all foreign keys exist and are valid before insertion ||

  
**Use Case Diagram**

The system supports two types of users: Customer and Staff. Customers can search for buses, make bookings, pay, and view tickets. Staff can assist walk-in customers by making bookings for them, check in passengers, generate boarding passes, and update trip statuses.
Below is the system’s use case diagram.

<img width="749" height="606" alt="image" src="https://github.com/user-attachments/assets/145fbeda-04f7-4470-bbf1-54d391b823cb" />


## User Manual

  

1.  Download Source File.
    

2.  Import SQL to your database server. In this project we use PhpMy Admin to create database and import. The sql code in in folder db from your downloaded resources.
    

 
![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdaIsqLBwYZA0edJFbc3TxIxvrll8CIgMhBML7-MrH2i-IFB9tW8fDKIArGtGVveItSrxlHcrLMeGqO3DZnDAdoxJggF3by-G2FaVv9tDl8eSZNtdHyvWeGHsQcRDW8otiPeDCT6w?key=KxEgPuPIz5T9YTOO2d9d8g)

  

3.  Configure your connection to database. In this project we are using XAMPP. The php file need to be put into the following folder structure : `XAMPP\htdocs\busApi\<php files>.` 

<img width="461" height="490" alt="Screenshot 2025-07-18 225044" src="https://github.com/user-attachments/assets/58726a5d-f0a7-4e35-910c-71e69aec0fa8" />


4. Make sure the Apache and MySQL is start in your server.

   <img width="808" height="520" alt="image" src="https://github.com/user-attachments/assets/521ad64b-68e7-4162-91c9-c19185871163" />


5. Run the application and you can start to use the application.
    

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXeEeLa927lr8_CQgVeKxPlOEgd2OqZ8mQeoX-yM52lBCTt_dpid-My6SZV_OaySoZUVJxJMjKdX7ItgyeaVvDOFJaNLmTL3PM5w1UXGNHv5ZXp_oMOn8yKPIQm80Us6RtYBmWtm?key=KxEgPuPIz5T9YTOO2d9d8g)
