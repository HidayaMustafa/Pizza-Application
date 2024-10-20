# Pizza Application

## Overview

Pizza App is an Android application designed for a pizza restaurant, allowing users to order pizzas online or using a local database. The app features a user-friendly interface and provides functionalities for both customers and admins.

## Features

### User Functionality
1. **Introduction Layout**
   - "Get Started" button to connect to the server and load pizza types.

2. **Login and Registration**
   - Users can log in using their email and password.
   - Registration includes email validation, phone number checks, name validation, and password security.

3. **Home Layout (Customer)**
   - Navigation Drawer with the following options:
     - **Home:** Displays the restaurant's history.
     - **Pizza Menu:** Shows all pizza types with details, favorites, and ordering options.
     - **Your Orders:** Lists previous orders with details.
     - **Your Favorites:** Displays favorite pizzas with ordering options.
     - **Special Offers:** Showcases special promotions.
     - **Profile:** Allows users to view and edit personal information, add profile picture.
     - **Call Us / Find Us:** Options to call the restaurant, view it on Google Maps, or send an email.
     - **Logout:** Logs the user out and redirects to the login page.

### Admin Functionality
1. **Admin Profile**
   - Admins can view and update their personal information.

2. **Add Admin**
   - Ability to add new admins with necessary validations.

3. **View All Orders**
   - Admins can view all customer orders along with details.

4. **Add Special Offers**
   - Admins can create special offers with various parameters.

5. **Make Report**
   - Admin capabilities to calculate total orders and income for each pizza type.
     
6. **Logout**
   - Logs out the admin and redirects to the login page.

## Technology Stack
- Android Development (Java)
- SQLite Database
- RESTful API for data retrieval
- Shared Preferences for user settings
- Fragments for UI management
- Animations for enhanced user experience
