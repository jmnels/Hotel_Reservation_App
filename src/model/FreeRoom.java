package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, double price, RoomType enumeration, boolean open) {
        super(roomNumber, price, enumeration, open);
        this.price = 0;


    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", enumeration=" + enumeration +
                ", open=" + open +
                '}';
    }
}
