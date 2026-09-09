import java.util.ArrayList;
import java.util.Scanner;

// Room model
class Room {
    int roomNumber;
    String category; // Standard, Deluxe, Suite
    double pricePerNight;
    boolean isBooked;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.isBooked = false; // Initially vacant
    }
}

// Booking details model
class Booking {
    int bookingId;
    String guestName;
    Room room;
    int stayDays;
    double totalBill;
    String paymentStatus;

    public Booking(int bookingId, String guestName, Room room, int stayDays, double totalBill) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.room = room;
        this.stayDays = stayDays;
        this.totalBill = totalBill;
        this.paymentStatus = "PAID (Simulation)";
    }
}

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int bookingCounter = 1001; // Auto-increment booking ID

    public static void main(String[] args) {
        // Setup initial hotel rooms inventory
        setupRooms();

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println("     HOTEL RESERVATION SYSTEM     ");
            System.out.println("==================================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");

            System.out.print("\nEnter your choice: ");
            choice = getIntegerInput();

            switch (choice) {
                case 1:
                    showAvailableRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    showAllBookings();
                    break;
                case 5:
                    System.out.println("\nThank you for visiting! Have a nice day.");
                    break;
                default:
                    System.out.println("Invalid option! Please enter a choice between 1 and 5.");
            }

        } while (choice != 5);
    }

    // Initialize sample rooms
    static void setupRooms() {
        rooms.add(new Room(101, "Standard", 1500.0));
        rooms.add(new Room(102, "Standard", 1500.0));
        rooms.add(new Room(201, "Deluxe", 3000.0));
        rooms.add(new Room(202, "Deluxe", 3000.0));
        rooms.add(new Room(301, "Suite", 5500.0));
        rooms.add(new Room(302, "Suite", 5500.0));
    }

    // 1. Search and view vacant rooms
    static void showAvailableRooms() {
        System.out.println("\n---------------- AVAILABLE ROOMS ----------------");
        System.out.printf("%-10s %-14s %-12s\n", "Room No", "Category", "Price/Night");
        System.out.println("-------------------------------------------------");

        boolean hasVacant = false;
        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.printf("%-10d %-14s Rs. %-10.2f\n", r.roomNumber, r.category, r.pricePerNight);
                hasVacant = true;
            }
        }

        if (!hasVacant) {
            System.out.println("All rooms are currently occupied!");
        }
        System.out.println("-------------------------------------------------");
    }

    // 2. Book room with payment simulation
    static void bookRoom() {
        showAvailableRooms();

        System.out.print("\nEnter Room Number to book: ");
        int selectedRoomNo = getIntegerInput();

        Room chosenRoom = null;
        for (Room r : rooms) {
            if (r.roomNumber == selectedRoomNo && !r.isBooked) {
                chosenRoom = r;
                break;
            }
        }

        if (chosenRoom == null) {
            System.out.println("Selected room is either booked or does not exist!");
            return;
        }

        System.out.print("Enter Guest Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Guest name cannot be empty.");
            return;
        }

        System.out.print("Enter Number of Nights to stay: ");
        int nights = getIntegerInput();

        if (nights <= 0) {
            System.out.println("Stay must be at least 1 night.");
            return;
        }

        double bill = chosenRoom.pricePerNight * nights;

        // Payment gateway simulation
        System.out.println("\n--- PAYMENT SIMULATION ---");
        System.out.printf("Total Payable Amount: Rs. %.2f\n", bill);
        System.out.println("1. UPI / GPay");
        System.out.println("2. Credit / Debit Card");
        System.out.println("3. Cash at Check-in");
        System.out.print("Choose payment mode: ");
        int mode = getIntegerInput();

        if (mode < 1 || mode > 3) {
            System.out.println("Payment failed. Booking cancelled.");
            return;
        }

        System.out.println("Authorizing payment... [PAYMENT SUCCESSFUL]");

        // Mark room as occupied and record booking
        chosenRoom.isBooked = true;
        Booking newBooking = new Booking(bookingCounter++, name, chosenRoom, nights, bill);
        bookings.add(newBooking);

        System.out.println("\nReservation Confirmed!");
        System.out.println("Booking Reference ID : " + newBooking.bookingId);
        System.out.println("Guest Name           : " + newBooking.guestName);
        System.out.println("Room Allocated       : " + chosenRoom.roomNumber + " (" + chosenRoom.category + ")");
        System.out.printf("Total Amount Paid    : Rs. %.2f\n", bill);
    }

    // 3. Cancel an existing reservation
    static void cancelBooking() {
        if (bookings.isEmpty()) {
            System.out.println("\nNo active reservations found to cancel.");
            return;
        }

        System.out.print("\nEnter Booking Reference ID to cancel: ");
        int id = getIntegerInput();

        Booking toCancel = null;
        for (Booking b : bookings) {
            if (b.bookingId == id) {
                toCancel = b;
                break;
            }
        }

        if (toCancel == null) {
            System.out.println("No booking found with ID: " + id);
            return;
        }

        // Release the booked room
        toCancel.room.isBooked = false;
        bookings.remove(toCancel);

        System.out.println("\nBooking ID #" + id + " has been cancelled.");
        System.out.printf("Refund processed: Rs. %.2f\n", toCancel.totalBill);
    }

    // 4. View all current reservations
    static void showAllBookings() {
        System.out.println("\n================ ACTIVE BOOKING DETAILS ================");
        if (bookings.isEmpty()) {
            System.out.println("No guest reservations registered yet.");
            System.out.println("========================================================");
            return;
        }

        for (Booking b : bookings) {
            System.out.println("Booking ID   : " + b.bookingId);
            System.out.println("Guest Name   : " + b.guestName);
            System.out.println("Room Details : " + b.room.roomNumber + " [" + b.room.category + "]");
            System.out.println("Duration     : " + b.stayDays + " night(s)");
            System.out.printf("Total Billed : Rs. %.2f (%s)\n", b.totalBill, b.paymentStatus);
            System.out.println("--------------------------------------------------------");
        }
    }

    // Helper method to safely read integers without crashing
    static int getIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid entry! Please enter a valid number: ");
            }
        }
    }
}