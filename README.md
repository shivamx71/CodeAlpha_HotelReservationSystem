# Hotel Reservation System

A console-based hotel booking and room management application developed for the **CodeAlpha Java Programming Internship**.

## Description
This project simulates an interactive hotel reservation desk. It allows users to browse vacant rooms across multiple tiers (Standard, Deluxe, Suite), reserve rooms for a custom duration, simulate real-time digital payment processing, view active reservation records, and cancel bookings with refund confirmation.

## Features
- **Room Categorization:** Supports Standard, Deluxe, and Suite rooms with varying pricing models.
- **Availability Search:** Real-time lookup of currently vacant rooms.
- **Reservation Workflow:** Captures guest info, room preference, and duration of stay.
- **Payment Simulation:** Simulated checkout with UPI, Card, and Cash options.
- **Booking Cancellation:** Free up room occupancy and initiate refund logic using Booking ID.
- **Robust Input Handling:** Graceful exception handling for input mismatches.

## Tech Stack
- **Language:** Java (JDK 8+)
- **Core Concepts:** Object-Oriented Programming (OOP), ArrayList, Scanner, Helper methods.

## How to Run

1. Open terminal in the project folder.
2. Compile the source file:
   ```bash
   javac HotelReservationSystem.java