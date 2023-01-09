package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource userInterface;

    private HotelResource(){};

    public static HotelResource getInstance() {
        if(userInterface==null) {
            userInterface = new HotelResource();
        }
        return userInterface;
    }

    public Customer getCustomer(String email) {
        CustomerService service = CustomerService.getInstance();
        return service.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        CustomerService service = CustomerService.getInstance();
        service.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        ReservationService service = ReservationService.getInstance();
        return service.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom roomNumber, Date checkInDate, Date checkOutDate) {
        ReservationService service = ReservationService.getInstance();
        CustomerService customerService = CustomerService.getInstance();
        Customer customer = customerService.getCustomer(customerEmail);
        return service.reserveARoom(customer, roomNumber, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        ReservationService service = ReservationService.getInstance();
        return service.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        ReservationService service = ReservationService.getInstance();
        return service.findRooms(checkIn,checkOut);
    }
}
