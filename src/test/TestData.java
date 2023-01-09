package test;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.*;

public class TestData {
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();
    Customer josh = new Customer("Joshua", "Nelson", "J@gmail.com");
    Customer bob = new Customer("Bob", "Hess", "B@gmail.com");
    Customer adam = new Customer("Adam", "Bangert", "A@gmail.com");
    Customer charlie = new Customer("Charlie", "Nelson", "C@gmail.com");
    ArrayList<IRoom> roomsToAdd = new ArrayList<>();

    public void addCustomers() {
        customerService.addCustomer("J@gmail.com","Joshua", "Nelson");
        customerService.addCustomer("A@gmail.com", "Adam", "Bangert");
        customerService.addCustomer("B@gmail.com", "Bob", "Hess");
        customerService.addCustomer("C@gmail.com", "Charlie", "Nelson");
    }

    public void addRooms() {
        String roomNumber = "1";
        AdminResource adminResource = AdminResource.getInstance();
        Collection<IRoom> existingRooms = adminResource.getAllRooms();
        ArrayList<String> num = new ArrayList<>();

        while(num.size()<4) {
            if(existingRooms.size()>0) {
                for (IRoom exRoom : existingRooms) {
                    if (exRoom.getRoomNumber().equals(roomNumber) || num.contains(roomNumber)) {
                        int roomMath = Integer.parseInt(roomNumber);
                        roomMath = roomMath * 3;
                        roomNumber = String.valueOf(roomMath);
                    }
                    num.add(roomNumber);
                    if(num.size()>3) break;
                }
            }
            else{
                int roomMath = Integer.parseInt(roomNumber);
                roomMath = roomMath +1;
                roomNumber = String.valueOf(roomMath);
                num.add(roomNumber);
            }
        }
        IRoom room1 = new Room(num.get(0), 123, RoomType.DOUBLE, true);
        IRoom room2 = new Room(num.get(1), 123, RoomType.DOUBLE, true);
        IRoom room3 = new Room(num.get(2), 80, RoomType.SINGLE, true);
        IRoom room4 = new Room(num.get(3), 100, RoomType.SINGLE, true);
        reservationService.addRoom(room1);
        reservationService.addRoom(room2);
        reservationService.addRoom(room3);
        reservationService.addRoom(room4);
        roomsToAdd.add(room1);
        roomsToAdd.add(room2);
        roomsToAdd.add(room3);
        roomsToAdd.add(room4);
    }

    public void addReservations() {
        reservationService.reserveARoom(customerService.getCustomer("J@gmail.com"), roomsToAdd.get(0), new GregorianCalendar(2023, Calendar.JANUARY, 3).getTime(), new GregorianCalendar(2023, Calendar.JANUARY, 9).getTime());
        reservationService.reserveARoom(customerService.getCustomer("A@gmail.com"), roomsToAdd.get(1), new GregorianCalendar(2023, Calendar.FEBRUARY, 10).getTime(), new GregorianCalendar(2023, Calendar.FEBRUARY, 19).getTime());
        reservationService.reserveARoom(customerService.getCustomer("B@gmail.com"), roomsToAdd.get(2), new GregorianCalendar(2023, Calendar.JANUARY, 11).getTime(), new GregorianCalendar(2023, Calendar.JANUARY, 13).getTime());
        reservationService.reserveARoom(customerService.getCustomer("C@gmail.com"), roomsToAdd.get(3), new GregorianCalendar(2023, Calendar.MARCH, 1).getTime(), new GregorianCalendar(2023, Calendar.MARCH, 6).getTime());
    }
}
