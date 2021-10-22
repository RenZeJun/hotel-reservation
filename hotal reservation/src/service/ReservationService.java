package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService RESERVATIONSINGLETON;
    private ReservationService(){};
    public static ReservationService getInstance(){
        if(RESERVATIONSINGLETON == null){
            RESERVATIONSINGLETON = new ReservationService();

        }
        return RESERVATIONSINGLETON;
    }


    public Map<String, Reservation> reservations = new HashMap<>();
    public Collection<IRoom> availableRooms = new HashSet<>();
    public Set<IRoom> allRoom = new HashSet<>();
    public Set<Reservation> allReserve = new HashSet<>();

    public void addRoom(IRoom room) {
        allRoom.add(room);
    }

     public IRoom getARoom(String roomId){
         for (IRoom r: availableRooms){
             if (r.getRoomNumber().equals(roomId))
                 return r;
         }
         return null;
    }

    public void updateAvailableRooms(Collection<IRoom> newAvailableRooms){
        availableRooms = new ArrayList<>(newAvailableRooms);
    }


     public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.put(customer.getEmail(), reservation);
        allReserve.add(reservation);
        
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return searchForRoom(checkInDate,checkOutDate);
    }




    Collection<IRoom> searchForRoom(Date checkInDate, Date checkOutDate){
        Collection<IRoom> notAvailableRooms = new HashSet<>();
        Collection<IRoom> allRooms = new HashSet<>(allRoom);
        if(availableRooms.isEmpty()){
            for (Reservation r : reservations.values()) {
                if (r.getCheckInDate().getTime() <= checkInDate.getTime() && r.getCheckOutDate().getTime() >= checkInDate.getTime()) {
                    notAvailableRooms.add(r.getRoom());
                }else if(r.getCheckOutDate().getTime() >= checkOutDate.getTime() && r.getCheckInDate().getTime() <= checkOutDate.getTime()){
                    notAvailableRooms.add(r.getRoom());
                }
            }
            allRooms.removeAll(notAvailableRooms);

            return allRooms;
        } else {
            for (Reservation r : reservations.values()) {
                if (r.getCheckInDate().getTime() <= checkInDate.getTime() && r.getCheckOutDate().getTime() >= checkInDate.getTime()) {
                    notAvailableRooms.add(r.getRoom());
                }else if(r.getCheckOutDate().getTime() >= checkOutDate.getTime() && r.getCheckInDate().getTime() <= checkOutDate.getTime()){
                    notAvailableRooms.add(r.getRoom());
                }
            }
            availableRooms.removeAll(notAvailableRooms);

            return availableRooms;
        }


    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
            Collection<Reservation> getCustomerR = new HashSet<>(allReserve);
            for(Reservation r: getCustomerR){
                if(r.getCustomer().equals(customer)){
                    getCustomerR.add(r);
                }
            }
            return getCustomerR;
    }

    public void printAllReservation(){
        Collection<Reservation> allReservation = new HashSet<>(reservations.values());

        if (!reservations.isEmpty())
            for (Reservation r: allReserve){
                System.out.println(r);
            }
    }
}

