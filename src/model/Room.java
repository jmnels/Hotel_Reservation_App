package model;

public class Room implements IRoom{
    String roomNumber;
    double price;
    RoomType enumeration;
    boolean open;

    public Room(String roomNumber, double price, RoomType enumeration, boolean open) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
        this.open = open;
    }

    public String getRoomNumber(){
        return this.roomNumber;
    };
    public double getRoomPrice() {
        return this.price;
    };
    public RoomType getRoomType() {
        return this.enumeration;
    };
    public boolean isFree() {
        return open;
    };

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", enumeration=" + enumeration +
                ", room open=" +open +
                '}';
    }
}
