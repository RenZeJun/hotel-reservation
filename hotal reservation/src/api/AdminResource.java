package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        rooms.forEach(reservationService::addRoom);
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.allRoom;
    }

    public Collection<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
