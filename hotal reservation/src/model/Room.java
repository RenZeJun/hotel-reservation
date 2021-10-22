package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double roomPrice;
    private RoomType roomType;
    private boolean isFree;

    public Room(String roomNumber, Double roomPrice, RoomType roomType, boolean isFree){
        super();
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
        this.isFree = isFree;

    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString(){
        return "Room Number:" + roomNumber + " Room Price:" + roomPrice + " Room Type:" + roomType + " isFree:" + isFree + "\n";
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass()!= obj.getClass()) return false;

        Room room = (Room) obj;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((roomNumber==null)? 0 : roomNumber.hashCode());
        return result;
    }

}
