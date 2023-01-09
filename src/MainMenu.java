import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MainMenu {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        String choice = "";
        do {
            System.out.println("1. Find and Reserve a Room\n"
                    + "2. See My Reservations\n"
                    + "3. Create an Account\n"
                    + "4. Admin\n"
                    + "5. Exit");
            choice = scanner.next();
            scanner.nextLine();

            HotelResource hotelResource = HotelResource.getInstance();
            AdminResource adminResource = AdminResource.getInstance();

            switch (choice) {
                case "1":
                    boolean form = true;
                    Date checkIn = new Date();
                    Date currentDate = new Date();
                    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                    currentDate = date.parse(date.format(currentDate));
                    String dateString = "";
                    do {
                        System.out.println("Please enter check in date as dd/mm/yyyy.");
                        dateString = scanner.nextLine();

                        try {
                            checkIn = date.parse(dateString);
                            break;
                        } catch (ParseException e) {
                            System.out.println("Please enter date in the proper format.");
                            form = false;
                        }
                    } while (!form);
                    Date checkOut = new Date();
                    String outString = "";
                    do {
                        System.out.println("Please enter check out date as dd/mm/yyyy.");
                        outString = scanner.nextLine();

                        try {
                            checkOut = date.parse(outString);
                            break;
                        } catch (ParseException e) {
                            System.out.println("Please enter a date in the proper format.");
                            form = false;
                        }
                    }while(!form);
                    if(!checkIn.before(checkOut)) {
                        System.out.println("Please enter successive dates for a reservation.\n");
                        break;
                    }
                    if(checkIn.before(currentDate)) {
                        System.out.println("Please enter dates starting with current date of after.");
                        break;
                    }
                    Date [] dates = {checkIn, checkOut};
                    Collection<IRoom> rooms = hotelResource.findARoom(checkIn, checkOut);
                    if(rooms.size()>0) {
                        System.out.println("Here are the rooms available for your dates.");

                        for (IRoom room : rooms) {
                            System.out.println(room);
                        }
                    }
                    else{
                    Calendar c = Calendar.getInstance();
                    c.setTime(checkIn);
                    c.add(Calendar.DATE, 7);
                    dateString = date.format(c.getTime());
                    Date newCheckIn;
                    try{
                        newCheckIn = date.parse(dateString);
                    }
                    catch(ParseException e){
                        System.out.println("Please enter a valid date.");
                        break;
                    }
                    c.setTime(checkOut);
                    c.add(Calendar.DATE, 7);
                    outString = date.format(c.getTime());
                    Date newCheckOut;
                    try{
                        newCheckOut = date.parse(outString);
                    }
                    catch(Exception e){
                        System.out.println("Please enter a valid date.");
                        break;
                    }
                    dates = new Date[]{newCheckIn, newCheckOut};
                    rooms = hotelResource.findARoom(newCheckIn, newCheckOut);
                    if(rooms.size()>0) {
                        System.out.println("There are no rooms available for the dates you selected\n"
                                +"Here are rooms available from "+dateString+" to "+outString+".");

                        for (IRoom room : rooms) {
                            System.out.println(room);
                        }
                    }
                    else{
                        System.out.println("No rooms available within a week of the dates selected.");
                        break;
                    }
                }
                boolean inValid = true;
                String message = "Please enter a room number.";
                String roomNumber = "";
                do {
                    System.out.println(message);
                    roomNumber = scanner.next();

                    for (IRoom exRoom : rooms) {
                        if (exRoom.getRoomNumber().equals(roomNumber)) {
                            inValid = false;
                            break;
                        }
                    }
                    message = "Please enter a room number from the list provided above.";
                }while(inValid);
                message = "Please enter email.";
                String email = "";
                Collection<Customer> customers = adminResource.getAllCustomers();
                do {
                    System.out.println(message);
                    email = scanner.next();
                    if(email.matches(regex)) {
                        inValid = false;
                    }else{
                        message = "Please enter a valid email";
                        inValid = true;
                    }
                }while(inValid);
                inValid = true;
                do{
                    for (Customer customer : customers) {
                        if (customer.email.equals(email)) {
                            IRoom room = hotelResource.getRoom(roomNumber);
                            hotelResource.bookARoom(email, room, dates[0], dates[1]);
                            System.out.println("Your reservation is confirmed for room number " + roomNumber + " from " + dateString + " through " + outString + ".");
                            inValid = false;
                            break;
                        }
                    }
                    if(inValid) {
                        System.out.println("User not found. Please create an account.\n");
                        break;
                    }
                }while(inValid);
                break;
                case "2":
                    message = "Please enter email.";
                    String emailRes = "";
                    do {
                        System.out.println(message);
                        emailRes = scanner.next();
                        if(emailRes.matches(regex)) {
                            inValid = false;
                        }else{
                            message = "Please enter a valid email";
                            inValid = true;
                        }
                    }while(inValid);
                    Collection<Reservation> customerReservations = hotelResource.getCustomersReservations(emailRes);
                    if(customerReservations.size()>0) {
                        System.out.println("Here are your reservations:");
                        for (Reservation res : customerReservations) {
                            System.out.println(res);
                        }
                    }
                    else{
                        System.out.println("No reservation found for this user.");
                    }
                    break;
                case "3":
                message = "Please enter email.";

                customers = adminResource.getAllCustomers();
                do {
                    System.out.println(message);
                    email = scanner.next();
                    if(email.matches(regex)) {
                        inValid = false;
                        for(Customer c : customers) {
                            if(c.email.equals(email)) {
                                message = "The email entered already exists.\n"
                                            +"Please enter email.";
                                inValid = true;
                                break;
                            }
                        }
                    }else{
                        message = "Please enter a valid email.";
                        inValid = true;
                    }
                }while(inValid);

                System.out.println("Please enter first name.");
                String firstName = scanner.next();
                System.out.println("Please enter last name.");
                String lastName = scanner.next();
                if(!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
                    System.out.println("Please try again and enter valid first and last name.");
                    break;
                }
                hotelResource.createACustomer(email, firstName, lastName);
                System.out.println("Account created for " + firstName + " " + lastName + ".");
                break;
                case "4":
                AdminMenu adminMenu = new AdminMenu();
                String[] arguments = new String[] {"Hello"};
                adminMenu.main(arguments);
                break;
                case "5":
                System.exit(1);
                break;
                default:
                    System.out.println("Please enter a digit from 1 - 5.");
                    break;
            }
        }while(choice!="5");
    }
}