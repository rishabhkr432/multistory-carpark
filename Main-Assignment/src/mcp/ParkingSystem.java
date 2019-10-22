package mcp;

import mcp.staff.mcp.Attendant;
import mcp.zones.Zone;

import java.lang.Math;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ParkingSystem {
    public ArrayList<Zone> zones;
    private String main;
    private Attendant staff;
    private ArrayList<Ticket> tickets;
    private ArrayList<Attendant> staffList;
    public Zone zone1;
    public Zone zone2;
    public Zone zone3;
    public Zone zone4;
    public Zone zone5;
    private Scanner scan;
    public Map<String, String> adminlog = new HashMap<>();

    /**
     * Initialisng all objects and arraylists.
     */
    public ParkingSystem() {
        scan = new Scanner(System.in);
        tickets = new ArrayList<>();
        zones = new ArrayList<>();
        staffList = new ArrayList<>();
        this.zone1 = new Zone("Zone1", 10);  // Allocating all mcp.zones
        this.zone2 = new Zone("Zone2", 10);
        this.zone3 = new Zone("Zone3", 10);
        this.zone4 = new Zone("Zone4", 10);
        this.zone5 = new Zone("Zone5", 10);
        zones.add(zone1); // adding each zone to an arraylist.
        zones.add(zone2);
        zones.add(zone3);
        zones.add(zone4);
        zones.add(zone5);

    }

    /**
     * adding attendant in the system.
     */
    public void addStaff() {
        this.staff = new Attendant();
        addStaffInfo(this.staff);
    }

    /**
     * adding staff information in the system
     *
     * @param staff
     */

    private void addStaffInfo(Attendant staff) {
        boolean addstaffloop = true;
        do {

            System.out.println("Ask staff for appropriate staff ID");
            String newstaff = scan.nextLine();
            if (checkValidStaffId(newstaff)) {
                staff.setAttentdantID(newstaff);
                staff.setAttendantavailability(true);
                System.out.println("added" + staff.toString());
                staffList.add(staff);
                addstaffloop = false;
            } else {
                System.out.println(newstaff + " is already in the system");

            }
        } while (addstaffloop);
    }

    /**
     * this method shuffles attendants which makes sure same attendant is not dealing with the customer requests.
     *
     * @param shuffle
     */
    public void shuffleStaff(Attendant shuffle) {
        removeStaff(shuffle.getAttentdantID());
        shuffle.setAttendantavailability(true);
        staffList.add(shuffle);
    }

    /**
     * removing staff using staffID from the arraylist.
     */
    public void removeStaff(String staffid) {
        Attendant removestaff = null;
        for (Attendant a : staffList) {
            if (staffid.equals(a.getAttentdantID())) {
                removestaff = a;
                break;
            }
        }
        if (removestaff != null) {
            staffList.remove(removestaff);

        } else {
            System.out.println("Staff with id " + removestaff + "not found");
        }

    }

    /**
     * Assigning space for the vehicle
     *
     * @param z helps the code to acknowledge the code for the space.
     * @return
     */
    public int assignSpace(Zone z) {
        return z.selectSpace();
    }

    /**
     * Generates a ticketnumber for the customer.
     * The ticket number can go up to 500 after that it needs a refill.
     *
     * @return
     */
    public int receiptGen() { //generates ticketnumber
        //int max = 100;
        int i = 0;
        int j = 0;
        int c = 0;
        Ticket tick = tickets.get(tickets.size() -1 );
        do{
               if(i <= tickets.get(c).getTicket_number()) {
                   i++;
                   c++;
               }
            }while(i < tick.getTicket_number());


        return j = i + 1;

    }

    /**
     * Searching for staff
     *
     * @param staffid
     * @return
     */
    public Attendant searchStaff(String staffid) {
        Attendant stafffound = null;
        for (Attendant searchworker : staffList) {
            if (searchworker.getAttentdantID().equals(staffid)) {
                stafffound = searchworker;
                break;
            }
        }
        if (stafffound != null) {
            return stafffound;
        } else {
            System.err.println("Attendant with ID " + staffid + (" is not in the system"));
            return stafffound;
        }
    }

    public Boolean checkValidStaffId(String staffid) {
        Attendant validNewStaff = null;
        for (Attendant validworker : staffList) {
            if (validworker.getAttentdantID().equals(staffid)) {
                validNewStaff = validworker;
                break;
            }
        }
        if (validNewStaff != null) {
            System.out.println(validNewStaff.getAttentdantID() + " is in the system");
            return false;
        } else {
            System.out.println(staffid + (" is valid"));
            return true;
        }
    }

    /**
     * Looking for available staff
     *
     * @return
     */
    public String checkAvailableStaff() {
        Attendant a = null;
        for (Attendant worker : staffList) {
            if (worker.getAttendantavailability()) {
                a = worker;
                break;
            }
        }
        if (a.getAttentdantID() != "-1") {
            a.getAttentdantID();
        } else {
            System.out.println("Staff is busy. Please park your own vehicle");
        }
        return a.getAttentdantID();
    }

    /**
     * this code generates a three letter code for the user.
     *
     * @param ticket helps starts the countdown for 15mins.
     * @return
     */
    private String codeGenerator(Ticket ticket) {
        long countdown = System.currentTimeMillis();
        ticket.setCountdown(countdown);
        String alphanumbers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();
        Random rand = new Random();
        while (code.length() < 3) {
            int index = (int) (rand.nextFloat() * alphanumbers.length());
            code.append(alphanumbers.charAt(index));
        }
        String randcode = code.toString();
        ticket.setToken(randcode);

        return randcode;


    }

    /**
     * checking the random generated code is valid.
     *
     * @param randgencode
     * @return
     */

    public Ticket verifyCode(String randgencode) {

        Ticket result = null;

        for (Ticket tickno : tickets) {
            if (randgencode.equals(tickno.getToken())) {
                result = tickno;
                break;
            }
        }
        if (result != null) {
            long currenttime = System.currentTimeMillis();
            long count = result.getCountdown() + (15 * 60 * 1000);

            if (currenttime <= count) {
                removeTicket(result.getTicket_number());
            } else {
                result = null;
                System.out.println("15 mins passed. You cannot leave using same token. Please seek assistance");
            }
        } else {
            System.err.println("Error " + randgencode + " not found. Please try again");
        }
        return result;

    }

    /**
     * adding ticket to the arraylist.
     *
     * @param ticket
     */
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    /**
     * searching for tickets in arraylist.
     *
     * @param ticket_no
     * @return
     */

    public String searchTicket(int ticket_no) {
        Ticket token = null;
        String code = null;
        for (Ticket tokenno : tickets) {
            if (ticket_no == (tokenno.getTicket_number())) {
                token = tokenno;
                break;
            }
        }
        if (token != null) {
            if (token.getToken().equals("null") ) {
                System.out.println("Ticket found, proceeding to payment");
                totalPrice(token);
                code = codeGenerator(token);
                System.out.println("please use code - " + code + " on the exit barrier");
                System.out.println("Please remember this code only works for 15mins");
                //System.out.println("If you can't leave in 15 mins please look for a member of staff");
            } else {
                System.out.println("the token for this vehicle is " + token.getToken());
            }
        } else {

            System.err.println(ticket_no + " is not valid");

        }


        return code;
    }

    /**
     * Calculating price for the car park.
     *
     * @param token
     */
    private void totalPrice(Ticket token) {
        long checkouttime = System.currentTimeMillis();
        String checkout = convertLongToString(checkouttime);
        System.out.println("Checkout time is " + checkout);      // checkout time shown to user.
        long totaltime = checkouttime - token.getEntry();
        String totaltimeformat = showHours(totaltime);
        System.out.println("total time you parked for " + totaltimeformat + " mins"); // total time user parked their vehicle.
        long totalhrs = TimeUnit.MILLISECONDS.toHours(totaltime) + 1;
        double totalprice = totalhrs * token.getZprice();       // calculating total price.
        System.out.println("Total amount to pay is £" + totalprice);
        transaction(totalprice);

    }

    /**
     * @param price amount user needs to pay to get code for the exit barrier.
     */
    private void transaction(double price) {
        double change;
        double inputmoney = 0;
        boolean inputmix;
        do {
            do {
                do{

                    try {
                        System.out.println("Please input money in the system now:");
                        String strinputmoney = scan.nextLine();
                        inputmoney = Double.parseDouble(strinputmoney);
                        inputmix = false;
                    } catch (NumberFormatException e) {

                        System.err.println("Enter a number input please");
                        inputmix = true;

                    }


            }while (inputmix);

             if(inputmoney < 0){
                 System.out.println("please enter a positive number");
             }
            }while (inputmoney < 0);

            change = price - inputmoney;
           double abschange = Math.abs(change);
            if (inputmoney > price) {
                System.out.println("Your change is £" + abschange);
                break;
            }
            System.out.println("£" + abschange + " left to pay");
            price = abschange;


        }
        while (change != 0.00);
        System.out.println("Payment processed");
    }

    /**
     * Converting time from long to String.
     *
     * @param time
     * @return
     */
    public String convertLongToString(long time) {
        String pattern = "E, yyyy-MM-dd HH:mm:ss";
        DateFormat stringdate = new SimpleDateFormat(pattern);
        String formattedtime = stringdate.format(time);

        return formattedtime;

    }

    /**
     * Displaying time in minutes so user can decide how much time he has left within an hour.
     *
     * @param totalhrs
     * @return
     */

    private String showHours(long totalhrs) {
        String pattern = "mm:ss";
        DateFormat stringhrs = new SimpleDateFormat(pattern);
        String formattedhrs = stringhrs.format(totalhrs);

        return formattedhrs;

    }

    /**
     * remove ticket from the arraylist
     *
     * @param ticketno
     * @return
     */
    private boolean removeTicket(int ticketno) {
        Ticket receiptno = null;
        for (Ticket tno : tickets) {
            if (ticketno == (tno.getTicket_number())) {
                receiptno = tno;
                break;

            }
        }
        if (receiptno != null) {
            tickets.remove(receiptno);

            System.out.println("removed Ticket - " + receiptno.getTicket_number());
            removeZoneNum(receiptno.getZtype(), receiptno.getParkingspace());
            return true;
        } else {
            System.err.println("cannot remove ticket - " + ticketno + " cannot find in the tickets");
            return false;
        }
    }

    /**
     * display all mcp.zones status with the system.
     */
    public void displayAllZones() {
        for (int i = 0; i < zones.size(); i++) {
            System.out.println(zones.get(i).toString());
        }
    }
    public void displayAllStaff() {
        for (int i = 0;i < staffList.size(); i++){
            System.out.println(staffList.get(i).toString());
        }
    }

    /**
     * display all tickets in the system.
     */
    public void displayAllTickets() {
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println(tickets.get(i).toString());
        }
    }

    /**
     * removing space within a zone once customer checked out.
     *
     * @param zonetype
     * @param zonespace
     * @return
     */
    private boolean removeZoneNum(String zonetype, int zonespace) {
        Zone zone = null;
        int space = 0;

        for (Zone zoneno : zones) {
            if (zonetype.equals(zoneno.getZonetype())) {
                zone = zoneno;
                break;
            }
        }
        if (zone != null) {
            zone.removeSpace(zonespace);
            System.out.println("removed space in - " + zone.getZonetype() + " - " + zone.getSpace().toString());
            return true;
        } else {
            System.err.println("Dedicated space is either obstruct or already free");
            return false;
        }
    }

    /**
     * this method helps me assign zonetypes.
     *
     * @param z
     * @return
     */
    private Zone checkZone(String z) {
        Zone num = new Zone();
        // int j = mcp.zones.;
        if (zones.get(0).getZonetype().equals(z)) {
            return zones.get(0);
        } else if (zones.get(1).getZonetype().equals(z)) {
            return zones.get(1);
        } else if (zones.get(2).getZonetype().equals(z)) {
            return zones.get(2);
        } else if (zones.get(3).getZonetype().equals(z)) {
            return zones.get(3);
        } else if (zones.get(4).getZonetype().equals(z)) {
            return zones.get(4);
        } else
            return num;
    }

    /**
     * Within the load method, there are 3 different files being loaded.
     *
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void load(String filename) throws FileNotFoundException, IOException {
//            String fname = null;
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {





            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a infile.nextInt or infile.nextBoolean and can use infile.next
            infile.useDelimiter("\r?\n|\r"); // End of line characters on Unix or DOS
            main = infile.nextLine();
            switch (filename) {
                case "carpark.txt":

                    while (infile.hasNext()) {
                        Ticket ticket = null;
                        ticket = new Ticket();
                        ticket.load(infile);
                        tickets.add(ticket);

                    }
                    break;
                case "zones.txt":

                    while (infile.hasNext()) {
                        String ztype = infile.next();
                        Zone zonetype = checkZone(ztype);
                        zonetype.load(infile);
                        double price = infile.nextDouble();
                        zonetype.setChargingprice(price);
                    }
                    break;
                case "staff.txt":
                    while (infile.hasNext()) {
                        Attendant a = null;
                        a = new Attendant();
                        a.load(infile);
                        staffList.add(a);
                    }
                    break;
                case "admin.txt":
                    while(infile.hasNext()){
                        String adminid = infile.next();
                        String password = infile.next();
                        adminlog.put(adminid, password);

                    }
//

            }
        }
    }

    /**
     * Saving three different files using PrintWriter.
     *
     * @param outfileName
     * @throws IOException
     */

    public void save(String outfileName) throws IOException {
        // Again using try-with-resource so that I don't need to close the file explicitly
        try (FileWriter fw = new FileWriter(outfileName, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {
            outfile.println(main);
            switch (outfileName) {
                case "carpark.txt":
                    for (Ticket ticket : tickets) {
                        ticket.save(outfile);
                    }
                    break;
                case "mcp.zones.txt":
                    for (Zone zone : zones) {
                        zone.save(outfile);
                    }
                    break;

                case "staff.txt":
                    for (Attendant worker : staffList) {
                        worker.save(outfile);
                    }
                    break;


            }
        }
    }
}
