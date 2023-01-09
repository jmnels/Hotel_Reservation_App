package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource adminResource;

    private AdminResource(){};

    public static AdminResource getInstance() {
        if(adminResource==null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email) {
        CustomerService service = CustomerService.getInstance();
        return service.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        ReservationService service = ReservationService.getInstance();
        for(IRoom room : rooms) {
            service.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        ReservationService service = ReservationService.getInstance();
        return service.rooms;
    }

    public Collection<Customer> getAllCustomers() {
        CustomerService service = CustomerService.getInstance();
        return service.getAllCustomers();
    }

    public void displayAllReservations() {
        ReservationService service = ReservationService.getInstance();
        service.printAllReservation();
    }
}
