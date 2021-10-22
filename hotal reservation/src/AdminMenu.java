import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {
    private static AdminResource adminResource = new AdminResource();

    public static void adminMenu() {
        String input = "";

        printMenu();

        Scanner scanner = new Scanner(System.in);

        try {
            do {
                input = scanner.nextLine();

                if (input.length() == 1) {
                    switch (input.charAt(0)) {
                        case '1':
                            displayAllCustomer();
                            break;
                        case '2':
                            displayAllRoom();
                            break;
                        case '3':
                            displayAllReservation();
                            break;
                        case '4':
                            addNewRoom();
                            break;
                        case '5':
                            MainMenu.mainMenu();
                            break;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                } else {
                    System.out.println("invalid input");
                }
            } while (input.charAt(0) != '5' || input.length() != 1);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: invalid input.");
        }
    }

    private static void displayAllCustomer() {
        Collection<Customer> customers = adminResource.getAllCustomer();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            adminResource.getAllCustomer().forEach(System.out::println);
        }
    }

    private static void displayAllRoom() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No room found.");
        } else {
            for(IRoom r: rooms){
                System.out.println(r);
            }
        }
    }

    private static void displayAllReservation() {
        adminResource.displayAllReservations();
    }


    private static void addNewRoom() {
        Scanner scanner = new Scanner(System.in);
        double roomPrice = 0;
        boolean isFree = false;


        System.out.println("please enter the room number");
        String roomNumber = scanner.nextLine();

        System.out.println("Please enter the price for the room");
        try {
            roomPrice = Double.parseDouble(scanner.nextLine());
            if (roomPrice == 0) {
                isFree = true;
            } else {
                isFree = false;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid price, please enter again");
        }

        System.out.println("Room type: 1. Single bed. 2. Double bed. (please enter 1 or 2)");
        int roomType = Integer.parseInt(scanner.nextLine());

        if (roomType == 1) {
            Room room = new Room(roomNumber,roomPrice,RoomType.SINGLE,isFree);
            adminResource.addRoom(Collections.singletonList(room));
            System.out.println("Room added successfully!");
            //adminResource.addRoom(roomNumber,roomPrice,roomType);
//            room.setRoomNumber(roomNumber);
//            room.setRoomPrice(roomPrice);
//            room.setRoomType(RoomType.SINGLE);
//            room.setFree(isFree);
        } else if (roomType == 2) {
            Room room = new Room(roomNumber,roomPrice,RoomType.DOUBLE,isFree);
            adminResource.addRoom(Collections.singletonList(room));
//            room.setRoomNumber(roomNumber);
//            room.setRoomPrice(roomPrice);
//            room.setRoomType(RoomType.DOUBLE);
//            room.setFree(isFree);
        } else {
            System.out.println("Invalid Input");
        }

        System.out.println("would you like to add another room? Y/N");
        addAnotherRoom();
    }

    private static void addAnotherRoom() {
        Scanner scanner = new Scanner(System.in);
        String anotherRoom;
        try {
            anotherRoom = scanner.nextLine();

            while ((anotherRoom.charAt(0) != 'Y' && anotherRoom.charAt(0) != 'N')) {
                System.out.println("please enter Y or N");
                anotherRoom = scanner.nextLine();
            }

            if (anotherRoom.charAt(0) == 'Y') {
                addNewRoom();
            } else if (anotherRoom.charAt(0) == 'N') {
                printMenu();
            } else {
                addAnotherRoom();
            }
        } catch (StringIndexOutOfBoundsException ex) {
            addAnotherRoom();
        }
    }
    public static void printMenu(){
        System.out.println("Welcome to admin menu\n"+
                "1. See all Customers\n" +
                "2. See all Rooms\n"+
                "3. See all Reservation\n" +
                "4. Add a new room\n" +
                "5. Back to Main Menu\n" +
                "Please select a number------------------------------\n");
    }
}
