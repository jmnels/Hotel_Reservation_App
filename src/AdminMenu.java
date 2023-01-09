import api.AdminResource;
import model.*;
import test.TestData;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AdminMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("1. See all customers\n"
                             + "2. See all rooms\n"
                             + "3. See all reservations\n"
                             + "4. Add a room\n"
                             + "5. Add test data\n"
                             + "6. Back to main menu");

            choice = scanner.nextInt();
            AdminResource adminResource = AdminResource.getInstance();

            switch(choice) {
                case 1:
                Collection<Customer> customers = adminResource.getAllCustomers();
                for (Customer customer : customers) {
                    System.out.println(customer);
                }
                break;
                case 2:
                Collection<IRoom> irooms = adminResource.getAllRooms();
                for (IRoom room : irooms) {
                    System.out.println(room);
                }
                break;
                case 3:
                adminResource.displayAllReservations();
                break;
                case 4:
                List<IRoom> rooms = new ArrayList<>();
                String roomNumber = "";
                while(true) {
                    System.out.println("Enter room number to add.");
                    roomNumber = scanner.next();
                    try {
                        int num = Integer.parseInt(roomNumber);
                        break;
                    } catch (NumberFormatException ignore) {
                        System.out.println("Please enter an integer for room number.");

                    }
                }
                Collection<IRoom> existingRooms = adminResource.getAllRooms();
                boolean taken = false;
                for(IRoom exRoom : existingRooms) {
                    if(exRoom.getRoomNumber().equals(roomNumber)) {
                        System.out.println("This room already in system. Please use a new number.");
                        taken = true;
                    }
                }
                if(taken) break;
                double price = 0;
                while(true) {
                    System.out.println("Enter the room price.");
                    try {
                        price = Double.parseDouble(scanner.next());
                        break;
                    } catch (NumberFormatException ignore) {
                        System.out.println("Please try again with a valid price");
                    }
                }
                String type = "";
                while(true) {
                    System.out.println("Enter room type 'single' or 'double'.");
                    type = scanner.next();
                    if (type.toUpperCase().equals("SINGLE") || type.toUpperCase().equals("DOUBLE")) {
                        break;
                    }
                    System.out.println("Please enter room only with permitted types.");
                }
                RoomType roomType = RoomType.valueOf(type.toUpperCase());
                IRoom room = new Room(roomNumber, price, roomType, true);
                rooms.add(room);
                adminResource.addRoom(rooms);
                break;
                case 5:
                    TestData test = new TestData();
                    test.addCustomers();
                    test.addRooms();
                    test.addReservations();
                break;
                case 6:
                    break;
                default:
                System.out.println("Please enter a digit from 1 - 5.");
                break;
            }
        }while(choice!=6);
    }
}
