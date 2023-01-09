package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService reservationService;
    public static Collection<IRoom> rooms = new HashSet<>();
    private Map<IRoom, ArrayList<Reservation>> reservations = new HashMap<>();
    private ArrayList<Reservation> stays ;
    private ReservationService(){}

    public static ReservationService getInstance() {
        if(reservationService==null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) {
        rooms.add(room);
        stays = new ArrayList<>();
        reservations.put(room, stays);
    }

    public IRoom getARoom(String RoomId) {
        for(IRoom room : rooms) {
            if(room.getRoomNumber().equals(RoomId)) {
                return room;
            }
        }
        IRoom none = null;
        return none;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        stays = reservations.get(room);
        stays.add(reservation);
        reservations.put(room, stays);
        return reservation;
    }
    private boolean isTaken(ArrayList<Reservation> reservations, Date checkInDate, Date checkOutDate) {
        if(reservations.isEmpty()) return false;
        for(Reservation checkRoom : reservations) {
            if (checkInDate.before(checkRoom.checkInDate) && checkOutDate.after(checkRoom.checkInDate) || checkRoom.checkInDate.before(checkInDate) && checkRoom.checkOutDate.after(checkInDate) || checkInDate.compareTo(checkRoom.checkInDate)==0) {
                return true;
            }
        }
        return false;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>();
        for(IRoom room : rooms) {
            if(!isTaken(reservations.get(room), checkInDate, checkOutDate)) availableRooms.add(room);
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> reserved = new ArrayList<>();
        ArrayList<Reservation> sta = getReservations();
            for(Reservation res : sta) {
                if (customer == res.customer) {
                    reserved.add(res);
                }
            }
        return reserved;
    }
    ArrayList<Reservation> getReservations() {
        ArrayList<Reservation> all = new ArrayList<>();
        for(ArrayList<Reservation> sta : reservations.values()) {
            for(Reservation reservation : sta) {
                all.add(reservation);
            }
        }
        return all;
    }
    public void printAllReservation() {
        ArrayList<Reservation> sta = getReservations();
        sta.stream()
                .forEach(s-> System.out.println(s));
    }
}
