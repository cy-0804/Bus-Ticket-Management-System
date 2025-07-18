//draft only

## **Project Member**

1. ANG CHA YAN (B032310478 S1G1)
@GITHUB LINK
2. NATALIE TAN WEI MEI (B032310844 S1G1) @GIT HUB LINK
3. HO XIN YONG (B032310518 S1G1)
@GITHUB LINK
4. CHAI YI JING (B032310827 S1G1)
@GITHUB LINK
5. LOH JIA YING (B032310401 S1G2)
@ GIT HUB LINK
6. LIM ZHI YING (B032310439 S1G2)
@ GIT HUB LINK

Lecturer :Dr Mohd Hariz Bin Naim @ Mohayat

## **Project Overview**

## Introduction

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

## **System Architecture**
**High Level Diagram**


## **Backend Application**
**Technology Stack**
**API Documentation**

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
| POST | /confirm_payment.php | The counter staff will manually enter the customer’s details, their selection of seat, and payment method. These data will be passed to the backend application and will update the database. ||
| Check in walk-in customer | POST | /get_booking_info.php | The counter staff inserts the booking ID of the customer, then the backend application will retrieve the booking details for that particular booking and return to the frontend application. ||
| POST | /checkin(1).php | Handles the check-in status update upon clicking the “Check-In” button. ||
| View bus schedule | GET | /staff_view_bus.php | The bus details, like bus ID and the departure date, are passed to the backend application. The backend application retrieves the scheduled trip of that bus and returns the related trip details back to the front-end application. ||
| Update trip status | POST | /bus_status_update.php | Handles the bus status update upon clicking the “Update Status” button. ||

**External API Integration**  

| **FUNCTIONALITY** |     | **API ENDPOINT** | **DESCRIPTION** |
| --- | --- | --- | --- |
| Generate boarding pass | **Create submission:** POST<br><br>**Submission Status:** GET<br><br>**Download PDF:** GET | <https://api.docspring.com/api/v1/submissions/> | The counter staff integrates with the DocSpring API to generate a boarding pass after checking in the passengers. Trip and passenger details are passed to DocSpring’s REST API upon clicking the “>>Print Boarding Pass” button, and once the API finishes checking the document’s generation status, the boarding pass in PDF is downloaded. ||

## **Interface Design**

//wait convert image first

## **Database Design**

**Entity-Relationship Diagram (ERD)**


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



## User Manual

  

1.  Download Source File.
    

  

2.  Import SQL to your database server. In this project we use PhpMy Admin to create database and import. The sql code in in folder db from your downloaded resources.
    

  

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdaIsqLBwYZA0edJFbc3TxIxvrll8CIgMhBML7-MrH2i-IFB9tW8fDKIArGtGVveItSrxlHcrLMeGqO3DZnDAdoxJggF3by-G2FaVv9tDl8eSZNtdHyvWeGHsQcRDW8otiPeDCT6w?key=KxEgPuPIz5T9YTOO2d9d8g)

  

3.  Configure your connection to database. In this project we are using XAMPP. The php file need to be put into the following folder structure : `XAMPP\htdocs\busApi\<php files>.` 

4. Make sure the Apache and MySQL is start in your server.

6. Run the application and you can start to use the application.
    

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXeEeLa927lr8_CQgVeKxPlOEgd2OqZ8mQeoX-yM52lBCTt_dpid-My6SZV_OaySoZUVJxJMjKdX7ItgyeaVvDOFJaNLmTL3PM5w1UXGNHv5ZXp_oMOn8yKPIQm80Us6RtYBmWtm?key=KxEgPuPIz5T9YTOO2d9d8g)
