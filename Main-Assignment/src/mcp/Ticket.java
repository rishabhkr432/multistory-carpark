package mcp;

import java.io.PrintWriter;
import java.util.Scanner;


public class Ticket{
    private int ticket_number;
    private int parkingspace;
    private String license_no;
    private String ztype;
    private long entry;
    private String token;
    private double zprice;
    private long countdown;

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }

    public Ticket(String license_no, String ztype, int ticket_number, int parkingspace, long entrytime, double zprice) {
        this.license_no = license_no;
        this.ztype = ztype;
        this.ticket_number = ticket_number;
        this.parkingspace = parkingspace;
        this.entry = entrytime;
        this.zprice = zprice;

    }
    public Ticket(){}


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }

            String licensenumber = infile.next();
            license_no = infile.next();
            String zonetype = infile.next();
            ztype = infile.next();
            String zoneprice = infile.next();
            zprice = infile.nextDouble();
            String ticketno = infile.next();
            ticket_number = infile.nextInt();
            String packingspace = infile.next();
            parkingspace = infile.nextInt();
            String timeofentry = infile.next();
            entry = infile.nextLong();
            String counter = infile.next();
            countdown = infile.nextLong();
            String code = infile.next();
            token = infile.next();
            String nextticket = infile.next();


    }

    public void save(PrintWriter outfile) {

        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");

        outfile.println("License_no");
        outfile.println(license_no);
        outfile.println("Zone type");
        outfile.println(ztype);
        outfile.println("Zone price");
        outfile.println(zprice);
        outfile.println("Ticket number");
        outfile.println(ticket_number);
        outfile.println("Space ID");
        outfile.println(parkingspace);
        outfile.println("Time of entry");
        outfile.println(entry);
        outfile.println("Counter");
        outfile.println(countdown);
        outfile.println("Exit barrier code");
        outfile.println(token);
        outfile.println("NextTicket");

//
//
    }
    public int getParkingspace() {
        return parkingspace;
    }

    public void setParkingspace(int parkingspace) {
        this.parkingspace = parkingspace;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_number=" + ticket_number +
                ", parkingspace='" + parkingspace + '\'' +
                ", license_no='" + license_no + '\'' +
                ", ztype='" + ztype + '\'' +
                '}';
    }


//    public Zone getZone() {
//        return ztype;
//    }
//
//    public void setZone(Zone ztype) {
//        this.ztype = ztype;
//    }

    public int getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(int ticket_number) {
        this.ticket_number = ticket_number;
    }




    public long getEntry() {
        return entry;
    }

    public void setEntry(long entry) {
        this.entry = entry;
    }
    public double getZprice() {
        return zprice;
    }

    public void setZprice(double zprice) {
        this.zprice = zprice;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getZtype() {
        return ztype;
    }

    public void setZtype(String ztype) {
        this.ztype = ztype;
    }
}

