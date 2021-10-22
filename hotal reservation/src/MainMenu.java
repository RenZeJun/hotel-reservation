import api.HotelResource;
import model.IRoom;
import model.Reservation;


import java.net.SocketOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private static HotelResource hotelResource = new HotelResource();


    private static String DATE_FORMAT = "MM/dd/yyyy";

    public static void main(String[] args) {
        String line = "";
        Scanner scanner = new Scanner(System.in);

        mainMenu();

        try{
            do{
                line = scanner.nextLine();

                if(line.length() == 1) {
                    switch (line.charAt(0)){
                        case '1':
                            findAndReserveRoom();
                            break;
                        case '2':
                            seeMyReservation();
                            break;
                        case '3':
                            createAnAccount();
                            break;
                        case '4':
                            AdminMenu.adminMenu();
                            break;
                        case '5':
                            System.out.println("Exiting application");
                            break;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                } else {
                    System.out.println("invalid input");
                }
            } while(line.charAt(0) != '5' || line.length() != 1);
        } catch(StringIndexOutOfBoundsException ex) {
            System.out.println("Error: invalid input.");
        }
    }

    public static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);
        Date checkIn = null, checkOut = null;

        System.out.println("Please enter check-in Date (mm/dd/yy)");

        try {
            checkIn = new SimpleDateFormat(DATE_FORMAT).parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error: invalid input.");
            findAndReserveRoom();
        }

        System.out.println("Please enter check-out Date (mm/dd/yy)");
        try {
            checkOut = new SimpleDateFormat(DATE_FORMAT).parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error: invalid input.");
            findAndReserveRoom();
        }


        if(checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = new ArrayList<>(hotelResource.findARoom(checkIn, checkOut));

            if(availableRooms.isEmpty()){
                System.out.println("No room found, do you want to push the date back for 7 days and search again? Y/N");
                String researchBool = scanner.nextLine();

                if("Y".equalsIgnoreCase(researchBool)){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(checkIn);
                    calendar.add(Calendar.DAY_OF_MONTH,7);
                    checkIn = calendar.getTime();

                    calendar.setTime(checkOut);
                    calendar.add(Calendar.DAY_OF_MONTH,7);
                    checkOut = calendar.getTime();

                    System.out.println("the new check in and check out date is: " + checkIn + " and " + checkOut);

                    availableRooms = new ArrayList<>(hotelResource.findARoom(checkIn, checkOut));
                    hotelResource.updateAvailableRooms(availableRooms);
                    System.out.println(availableRooms);

                    System.out.println("would you like to reserve a new room? Y/N");
                    String recheck = scanner.nextLine();

                    if("Y".equalsIgnoreCase(recheck)){
                        reserveARoom(scanner, checkIn, checkOut, availableRooms);
                    } else if ("N".equalsIgnoreCase(researchBool)){
                        System.out.println("going back to the main menu");
                        mainMenu();
                    }



                } else if ("N".equalsIgnoreCase(researchBool)){
                    System.out.println("going back to the main menu");
                    mainMenu();
                }


            } else {
                    System.out.println(availableRooms);
                    hotelResource.updateAvailableRooms(availableRooms);
                    reserveARoom(scanner, checkIn, checkOut,availableRooms);
                }
            }
        }

    private static void reserveARoom(Scanner scanner, Date checkIn, Date checkOut, Collection<IRoom> availableRooms){
        System.out.println("is there any room you want to reserve? Y/N" );
        String bookRoomBoolean = scanner.nextLine();

        if("Y".equalsIgnoreCase(bookRoomBoolean)){
            System.out.println("Do you have a account for our hotel? Y/N");
            String haveAccount = scanner.nextLine();

            if("Y".equalsIgnoreCase(haveAccount)){
                System.out.println("please enter you email address!");
                String customerEmail = scanner.nextLine();

                if(hotelResource.getCustomer(customerEmail) == null){
                    System.out.println("can't find email in our data base, please create a new account!");
                    mainMenu();
                } else {
                    System.out.println("what room number would you like to reserve");
                    String roomNumber = scanner.nextLine();
                    IRoom room = hotelResource.getRoom(roomNumber);

                    if(room == null){
                        System.out.println("Room not found");
                    } else {
                        Reservation reservation = hotelResource.bookARoom(customerEmail,room, checkIn, checkOut);
                        System.out.println(reservation);
                    }
                }
            } else
                System.out.println("Invalid Input");
        } else
            System.out.println("Invalid Input");

        mainMenu();
    }


    private static void seeMyReservation(){
        Scanner scanner = new Scanner((System.in));

        System.out.println("Please enter your Email.");
        String Email = scanner.nextLine();

        Collection<Reservation> customerEmail = hotelResource.getCustomersReservations(Email);

        if(customerEmail == null || customerEmail.isEmpty()){
            System.out.println("No reservation found.\n");
        } else {
            customerEmail.forEach(reservation -> System.out.println(reservation));
        }
    }


    private static void createAnAccount(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your email address");
        final String email = scanner.nextLine();

        System.out.println("please enter you first name");
        final String firstName = scanner.nextLine();

        System.out.println("please enter your last name");
        final String lastName = scanner.nextLine();

        try{
            hotelResource.createACustomer(email, firstName,lastName);
            System.out.println("Account created!");

            mainMenu();
        }catch (IllegalArgumentException ex) {
            System.out.println("Invalid input");
            createAnAccount();
        }
    }


    public static void mainMenu(){
        System.out.print("Welcome to the Hotel!\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "Please select a number------------------------------\n");
    }


}
