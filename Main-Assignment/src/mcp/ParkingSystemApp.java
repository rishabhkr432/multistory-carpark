package mcp;
import mcp.staff.mcp.Attendant;
import mcp.vehicletypes.*;
import mcp.zones.Zone;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParkingSystemApp {
    private String filename;
    private String zonesdata = "zones.txt";
    private String staffdata = "staff.txt";
    private String admindata = "admin.txt";
    private Scanner scan;
    private Vehicle vehicle;
    private ParkingSystem parkingsystem;
    private Zone zoneforparking;
    private Ticket eticket;
    private Standard s;
    private Higher h;
    private Longer l;
    private Coaches c;
    private Motorbikes m;



    public ParkingSystemApp() {

        vehicle = new Vehicle();
        zoneforparking = new Zone();
        eticket = new Ticket();
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename of car parking: ");
        filename = scan.next();
        parkingsystem = new ParkingSystem();
        scan = new Scanner(System.in);

    }


    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            parkingsystem.load(filename);
            parkingsystem.load(zonesdata);
            parkingsystem.load(staffdata);
            parkingsystem.load(admindata);

        } catch (FileNotFoundException e) {
            System.err.println("The file: " + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /**
     * This is Main Menu of the application.
     * Asks user to register themself as customer, staff or admin.
     */
    private void entryMenu() {
        Scanner in = new Scanner(System.in);
        boolean mainMenuLoop = true;
        do {

            System.out.println("Please register yourself");
            System.out.println("C - Customer");
            System.out.println("S - Staff");
            System.out.println("A - Admin");
            System.out.println("Q - Quit menu");
            String firstcheck = in.nextLine().toUpperCase();

            switch (firstcheck) {
                case "C":
                    customerMenu();
                    break;
                case "S":
                    staffMenu();
                    break;
                case "A":
                    adminMenu();
                    break;
                case "Q":
                    System.out.println("Closing menu");
                    mainMenuLoop = false;
                    break;
                default:
                    System.err.println("Please choose one of the given options");
                    break;
            }

        } while (mainMenuLoop);
    }

    /**
     * Admin Menu
     */
    private void adminMenu() {
        Scanner in = new Scanner(System.in);
        boolean adminpanelloop = false;
        if(!checkadminLogin()) {
            do {
                System.out.println("**********Admin Menu***********");
                System.out.println("1 - Add attendant");
                System.out.println("2 - Remove attendant");
                System.out.println("3 - Display all mcp.zones");
                System.out.println("4 - Display all tickets");
                System.out.println("5 - Open customer menu");
                System.out.println("6 - Open staff menu");
                System.out.println("7 - Go back");
                String adminpanel = in.nextLine().toUpperCase();
                switch (adminpanel) {
                    case "1":
                        parkingsystem.addStaff();
                        adminpanelloop = true;
                        break;
                    case "2":
                        System.out.println("please input the staff id to remove");
                        String removeid = in.nextLine();
                        parkingsystem.removeStaff(removeid);
                        adminpanelloop = true;
                        break;
                    case "3":
                        parkingsystem.displayAllZones();
                        adminpanelloop = true;
                        break;
                    case "4":
                        parkingsystem.displayAllTickets();
                        adminpanelloop = true;
                        break;
                    case "5":
                        customerMenu();
                        adminpanelloop = true;
                        break;
                    case "6":
                        staffMenu();
                        adminpanelloop = true;
                        break;
                    case "7":
                        adminpanelloop = false;
                        break;
                    default:
                        System.out.println("Please choose one of the valid options");
                        adminpanelloop = true;
                        break;
                }
            } while (adminpanelloop);
        }
        else {
            System.out.println("Go back to main menu");
        }
        save();
    }

    /**
     * Staff Menu
     */
    private void staffMenu() {
        boolean loop = true;
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("1 - Enter Staff menu");
            System.out.println("2 - Exit Staff Menu");
            String checker = in.nextLine().toUpperCase();

            switch (checker) {
                case "1":
                    System.out.println("**********Staff Menu***********");
                    System.out.println("Please enter your staffIdAdd");
                    String staffid = in.next();
                    Attendant checkvalidworker = null;
                    boolean staffMenuLoop = true;
                    do {
                        checkvalidworker = parkingsystem.searchStaff(staffid);

                        if (checkvalidworker != null) {
                            System.out.println("1 - to park vehicle for customer");
                            System.out.println("2 - collect vehicle for customer");
                            System.out.println("3 - Display all mcp.zones");
                            System.out.println("4 - register completion of task");
                            System.out.println("5 - check status of staff");
                            System.out.println("6 - Exit menu");

                            String staffchoice = scan.nextLine().toUpperCase();
                            switch (staffchoice) {
                                case "1":
                                    if (checkvalidworker.getAttendantavailability()) {
                                        System.out.println("Going to park vehicle");
                                        checkvalidworker.setAttendantavailability(false);
                                        System.out.println("Parked vehicle");
                                        System.out.println("Return to customer to provide them ticket");

                                        break;
                                    } else {
                                        System.out.println("Staff with ID " + checkvalidworker.getAttentdantID() + " is busy help customers");
                                        break;
                                    }

                                case "2":
                                    if (checkvalidworker.getAttendantavailability()) {
                                        System.out.println("collecting vehicle for customer");
                                        checkvalidworker.setAttendantavailability(false);
                                        exitMenu();

                                        break;
                                    } else {
                                        System.out.println("Staff with ID " + checkvalidworker.getAttentdantID() + " is busy help customers");
                                        break;
                                    }

                                case "3":
                                    System.out.println("Display all mcp.zones");
                                    parkingsystem.displayAllZones();
                                    break;
                                case "4":

                                    parkingsystem.shuffleStaff(checkvalidworker);
                                    System.out.println("Staff with " + checkvalidworker.getAttentdantID() + " is free now");
                                    break;
                                case "5":
                                    System.out.println("Printing status of all staff");
                                    parkingsystem.displayAllStaff();
                                    break;
                                case "6":
                                    System.out.println("Exiting staff menu");
                                    staffMenuLoop = false;
                                    break;
                                    default:
                                        System.out.println("please choose one of the given options");
                                        break;
                            }
                        } else {
                            //System.out.println("Staff with ID " + checkvalidworker.getAttentdantID() + " is not busy");
                            System.out.println("Please try again");
                            break;

                        }
                    } while (staffMenuLoop);
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("Please choose one of the given options");
                    loop = true;
                    break;
            }

        } while (loop);
        save();
    }

    /**
     * check if the input is invalid ask user again for input
     * @param space
     * @return
     */
    private String spacecheck(String space){
        boolean spacecheck = true;
        space.trim();
        do {

            if (space.charAt(0) == ' '){
                System.err.println("please a valid input");
                String space2 = scan.nextLine();
                space = space2;
                space.trim();


            }
            else {

                spacecheck = false;
            }
        }while(spacecheck);

        return space;
    }
    /**
     * Customer Entry Menu
     */
    private void customerEntryMenu() {

        askForDisability();

        String answer;
        int space = 0;
        int space2 = 0;

        System.out.println("Please provide your car's register number: ");
        String vehiclenumber = scan.nextLine().toUpperCase();
        String vehiclenum = spacecheck(vehiclenumber);
        boolean firstchoice = true;
        String staffID = "-1";
        String choice = null;
        do {
            System.out.println("Please select your vehicle type: ");
            System.out.println("S - Standard cars and small vans");
            System.out.println("H - Higher short wheel-based vans");
            System.out.println("L - Longer  wheel-based vans");
            System.out.println("M - Motobikes");
            System.out.println("C - Coaches");

            answer = scan.nextLine().toUpperCase();


            switch (answer) {

                case "S":
                    s = new Standard(vehiclenum, vehicle.isDriverdisable());
                    vehicle = s;
                    staffID = getStaffId(vehicle);
                    System.out.println("Please choose either Zone1 or Zone4.");
                    choice = scan.nextLine();

                    switch (choice) {
                        case "Zone1":

                            space = parkingsystem.assignSpace(parkingsystem.zone1);
                            zoneforparking = parkingsystem.zones.get(0);
                            firstchoice = false;
                            break;
                        case "Zone4":
                            space = parkingsystem.assignSpace(parkingsystem.zone4);
                            zoneforparking = parkingsystem.zones.get(3);
                            firstchoice = false;
                            break;
                        default:
                            System.out.println("Please choose one of the given mcp.zones.");
                    }
                    break;
                case "L":

                    l = new Longer(vehiclenum, vehicle.isDriverdisable());
                    vehicle = l;
                    staffID = getStaffId(vehicle);
                    System.out.println("You can park in Zone 2");
                    space = parkingsystem.assignSpace(parkingsystem.zone2);
                    zoneforparking = parkingsystem.zones.get(1);
                    firstchoice = false;

                    break;
                case "H":
                    h = new Higher(vehiclenum, vehicle.isDriverdisable());
                    vehicle = h;
                    staffID = getStaffId(vehicle);
                    System.out.println("Please choose either Zone1 or Zone4.");
                    String choice2 = scan.nextLine();

                    switch (choice2) {
                        case "Zone1":
                            space2 = parkingsystem.assignSpace(parkingsystem.zone1);
                            zoneforparking = parkingsystem.zones.get(0);
                            firstchoice = false;
                            break;
                        case "Zone4":
                            space2 = parkingsystem.assignSpace(parkingsystem.zone4);
                            zoneforparking = parkingsystem.zones.get(3);
                            break;

                    }
                    space = space2;
                    choice = choice2;
                    firstchoice = false;
                    break;
                case "C":
                    c = new Coaches(vehiclenum, vehicle.isDriverdisable());
                    vehicle = c;
                    staffID = getStaffId(vehicle);
                    System.out.println("you can park in Zone 3");
                    space = parkingsystem.assignSpace(parkingsystem.zone3);
                    zoneforparking = parkingsystem.zones.get(2);
                    firstchoice = false;
                    break;
                case "M":

                    m = new Motorbikes(vehiclenum, vehicle.isDriverdisable());
                    vehicle = m;
                    staffID = getStaffId(vehicle);
                    System.out.println("you can park in Zone 5");
                    space = parkingsystem.assignSpace(parkingsystem.zone5);
                    zoneforparking = parkingsystem.zones.get(4);
                    firstchoice = false;
                    break;
                default:
                    System.err.println("please check one of the given options");
                    break;
            }
        } while (firstchoice);
        int invalidData = -1;
        String invalidString = "-1";
        if (space != invalidData) {
            int place = space;
            eticket.setParkingspace(place);


            if (staffID != invalidString) {
                staffMenu();

            }
            entry();
        } else {
            System.err.println(choice + " is full. Please try another zone if applicable");

        }
        save();
    }

    /**
     * This method allow application to acknowledge if a customer has disability.
     */
    private boolean checkadminLogin(){
        boolean success = false;
        boolean loginsuccess = false;
        do {


            System.out.println("Enter the ID to log in");
            System.out.println("Enter return to go back");
            String adminID = scan.next();
            if (parkingsystem.adminlog.containsKey(adminID)) {
                do {
                    System.out.println("Enter your password");
                    String password = scan.next();
                    if (password.equals(parkingsystem.adminlog.get(adminID))) {
                        loginsuccess = true;
                    } else {
                        System.out.println("Wrong password, try again");
                    }
                } while (!loginsuccess);

                success = false;
            }
            else if ((!adminID.equals("return") && !parkingsystem.adminlog.containsKey(adminID))) {
                System.err.println("Admin ID not found");
                success = true;
            }
        }while(success);
        return success;
    }
    private void askForDisability() {
        boolean loop = true;
        do {
            System.out.println("Do you have a disability ?(Y/N)");
            String disabled = scan.nextLine().toUpperCase();
            if (disabled.equals("Y")) {
                vehicle.setDriverdisable(true);
                loop = false;

            } else if (disabled.equals("N")) {
                vehicle.setDriverdisable(false);
                loop = false;

            } else {
                System.out.println("please choose one of the given options(Y - Yes/ N - NO)");
                loop = true;
            }
        } while (loop);
    }

    /**
     * @param transport checking vehicle type if condition doesnt match then staff assistance is not provided.
     * @return
     */
    private String getStaffId(Vehicle transport) {
        boolean wloop = true;
        String workerid = "-1";
        do {
            if (transport.getVehicletype().equals("Coaches")) {
                System.out.println("Assistance is not provided for Coaches");
                workerid = "-1";
                wloop = false;

            } else if (transport.getVehicletype().equals("Motorbikes")) {
                System.out.println("Assistance is not provided for Motorbikes");
                workerid = "-1";
                wloop = false;
            } else {
                workerid = staffIdAdd();
                wloop = false;
            }
        } while (wloop);
        return workerid;
    }

    private String staffIdAdd() {
        boolean loop;
        String worker = "-1";
        do {

            System.out.println("Ask staff member for help?");
            System.out.println("Y - Yes");
            System.out.println("N - No");
            String help = scan.nextLine().toLowerCase();
            if (help.equals("y")) {
                System.out.println("Looking for worker");
                worker = parkingsystem.checkAvailableStaff();
                System.out.println("Staff member with ID " + worker + " is available ");
                System.out.println("please leave your vehicle with our staff. Thanks");
                loop = false;
            } else if (help.equals("n")) {
                worker = "-1";
                System.out.println("Thank you for helping our staff today.");
                loop = false;
            } else {
                System.err.println("please enter a valid input");
                loop = true;
            }
        }while(loop);
        return worker;
    }


    /**
     * Customer Menu
     */
    private void customerMenu() {
        String option;
        do {
            System.out.println("**********Customer Menu***********");
            System.out.println("Please select one of the following options");
            System.out.println("1 - Enter car park");
            System.out.println("2 - Collect your vehicle");
            System.out.println("3 - Exit car park");
            System.out.println("Q - to exit");

            scan = new Scanner(System.in);
            option = scan.nextLine().toUpperCase();
            switch (option) {
                case "1":
                    customerEntryMenu();
                    break;
                case "2":
                    collectMenu();
                    break;
                case "3":
                    exitMenu();
                    break;
                case "Q":
                    option = "Q";
                    break;
                default:
                    System.err.println("Please choose one of the given options");
                    break;
            }
        } while (!(option.equals("Q")));


    }

    /**
     * This is Customer vehicle collection menu.
     */

    private void collectMenu() {
        boolean searchticketloop = true;

        do {

            System.out.println("**********Collection Menu***********");
            System.out.println("1 - to enter ticket number and collect exit barrier code");
            System.out.println("2 - to go back to customer menu");
            Scanner in = new Scanner(System.in);
            String collectCode = scan.nextLine().toUpperCase();
            switch (collectCode) {
                case "1":

                    System.out.println("Please enter the ticket number provided");
                    int ticket = in.nextInt();
                    String barrierCode = parkingsystem.searchTicket(ticket);
                    if (barrierCode != null) {
                               String staff = staffIdAdd();
                               if(staff.equals("-1")) {
                                   System.out.println("Thanks for helping our staff");

                               }

                                else {
                                   System.out.println(staff + " will get your vehicle for you");
                                   staffMenu();
                               }

                        searchticketloop = false;


                    } else {
                        searchticketloop = true;
                        //System.out.println(barrierCode + " is not valid ticket");
                    }
                    break;
                case "2":
                    System.out.println("Exiting collection menu");
                    searchticketloop = false;
                    break;
                default:
                    System.err.println("Please enter one of the provided options");
            }
            save();
        }

        while (searchticketloop);

    }


    private void exitMenu() {
        System.out.println("**********Exit Menu***********");
        System.out.println("Enter the token number to lift barrier");
        String check = scan.nextLine().toUpperCase();
        Ticket verified = parkingsystem.verifyCode(check);
        if (verified != null) {
            System.out.println(verified + " is valid");
            System.out.println("Lifting barrier");
            System.out.println("please provide the exit barrier code the user");
        } else {
            System.out.println("Ask staff to help you with your token");
        }
        save();


    }

    /**
     * @param weekend checking for disabled driver if condition match ticket price is halved.
     * @return
     */
    private double pricefordisabledriver(long weekend) {
        double pricefordisablepeople;
        if (vehicle.isDriverdisable()) {
            if (checkweekend(weekend)) {
                pricefordisablepeople = zoneforparking.getChargingprice() * 0;
            } else {
                pricefordisablepeople = zoneforparking.getChargingprice() / 2;
            }

        } else if (vehicle.getVehicletype().equals("Coaches")) {
            pricefordisablepeople = zoneforparking.getChargingprice();
        } else {
            pricefordisablepeople = zoneforparking.getChargingprice();
        }
        return pricefordisablepeople;

    }

    /**
     * @param weekend checking the day is "Sunday" then disabled people gets free parking.
     * @return
     */
    private boolean checkweekend(long weekend) {

        DateFormat stringday = new SimpleDateFormat("E");
        String day = stringday.format(weekend);
        if (day.equals("Sun")) {

            return true;

        } else
            return false;
    }

    /**
     * Generatiing Ticket for the car park.
     *
     * @return
     */
    private Ticket entry() {
        Ticket result = null;
        int receipt = parkingsystem.receiptGen();
        String veh_number = vehicle.getLicense_no();
        String ztype = zoneforparking.getZonetype();
        double price = pricefordisabledriver(receipt);
        int zoneID = eticket.getParkingspace();
        long timeofentry = System.currentTimeMillis();
        String formattedtime = parkingsystem.convertLongToString(timeofentry);
        Ticket newticket = new Ticket(veh_number, ztype, receipt, zoneID, timeofentry, price);
        System.out.println("Generating Parking Ticket");
        System.out.println(newticket.toString());
        System.out.println("Time of entry");
        System.out.println(formattedtime);
        System.out.println("Keep this ticket safe, this will be needed while collecting vehicle");
        parkingsystem.addTicket(newticket);
        result = newticket;

        return result;
    }

    /**
     * Save
     */

    private void save() {
        try {
            parkingsystem.save(filename);
            parkingsystem.save(zonesdata);
            parkingsystem.save(staffdata);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + filename);
        }

    }

//    public Vehicle getV() {
//        return vehicle;
//    }
//
//    public void setV(Vehicle vehicle) {
//        this.vehicle = vehicle;
//    }


    public static void main(String args[]) {
        System.out.println("**********HELLO***********");

        ParkingSystemApp simulation = new ParkingSystemApp();
        simulation.initialise();
        simulation.entryMenu();
    }
}
