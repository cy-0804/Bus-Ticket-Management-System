//table& diagram not done yet to convert in markdown format

//template only

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

**API Integration**

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

**API Integration**

## **Interface Design**

**Login Page**

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXd6wtHXz_tTpINh75X3vsWEAGpORmyLokOHsSQfaG6flvFjLdJvc7oteyq4v360S0fM-Pt2lQj9vRmI0EQN0db2ZvGp3j3Oz4ZcGogK-7q2UxB7UIjQal6voXTJ1idc4kpB8IGA?key=KxEgPuPIz5T9YTOO2d9d8g)

  
**Customer Dashboard**
![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXekphUAs8x2P7PuAFy2_7-5uTspr-ySFm19B1X0mGFP1NYCzSrPJ4F5qv2mdl4Y1Cd4xE9rzaeLKjCR-JtUuWT0GefzgrKfdwePXV6PRrJcAInVUv1scQGPwXJMfdmO0uHqFcaoIA?key=KxEgPuPIz5T9YTOO2d9d8g)

**Customer Booking Page**
![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXeYYtc9XU1jmaHwiyKL8iGS203VlMK64yRaBX90Xlx2tqx_IhcIvNtt3KsFwRLXObs0Zj3Lu3EUJ-BFVSnSPUnNF-9cqcOBVcdK6i8-4y4nd-retlRUZxQP2Cgg7wULEFWy6afv7Q?key=KxEgPuPIz5T9YTOO2d9d8g)

  

**Customer View Booking Page**

  
  
**Customer Payment Page**

  
  

**Staff Dashboard**

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXcXUbY1sq9innEBDxU_9RketRo2TCFvpRheP0Zu6oMKDqB7kkWoiibh6uP6fV9kvUhkh5j3p_JivSYuUJCiZwIHxJtq-zEayV8rwH0ICWx8XSn9lF2Gnh83Yx6mIZgBe_CaEn-Yyg?key=KxEgPuPIz5T9YTOO2d9d8g)

  

Staff Manual Booking Page

  

Staff Passenger Check-in Page

  
  

Staff View Bus Schedule

  

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXd_s5WRC7Z2DhJgz76aHhG6XDYj_lkF8sUvfKC1m1GZX0sHkHeJu7Kxoke7CG9c_SNWAMdBHfm53EDF-aE_ahFKjdrbWWYB3X5yn2Gf8YkPl8D8P-jygQ0ghAGK8ezRH1mfNMPnZw?key=KxEgPuPIz5T9YTOO2d9d8g)

 

## **Database Design**

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXejCbMiHNZk_k1r0l060PcENVb5eIyJDKKCTlCXDuQLi0YrNeduBzLFlX7eE7MmB22i8xDXgzE-mKGUSypuedYuyO3eNaW_I-bDx9208pZzNYX2oy_mt1sv94lNlP7gNHVouzTD6w?key=KxEgPuPIz5T9YTOO2d9d8g)

**Schema Justification**

**Data Validation**
1. Frontend Application

2. Backend Application


  


## User Manual

  

1.  Download Source File.
    

  

2.  Import SQL to your database server. In this project we use PhpMy Admin to create database and import. The sql code in in folder db from your downloaded resources.
    

  

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdaIsqLBwYZA0edJFbc3TxIxvrll8CIgMhBML7-MrH2i-IFB9tW8fDKIArGtGVveItSrxlHcrLMeGqO3DZnDAdoxJggF3by-G2FaVv9tDl8eSZNtdHyvWeGHsQcRDW8otiPeDCT6w?key=KxEgPuPIz5T9YTOO2d9d8g)

  

3.  Configure your connection to database. In this project we are using XAMPP. The php file need to be put into the following folder structure : `XAMPP\htdocs\busApi\<php files>.` 

4. Make sure the Apache and MySQL is start in your server.

6. Run the application and you can start to use the application.
    

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXeEeLa927lr8_CQgVeKxPlOEgd2OqZ8mQeoX-yM52lBCTt_dpid-My6SZV_OaySoZUVJxJMjKdX7ItgyeaVvDOFJaNLmTL3PM5w1UXGNHv5ZXp_oMOn8yKPIQm80Us6RtYBmWtm?key=KxEgPuPIz5T9YTOO2d9d8g)
