package mcp.staff.mcp;

import java.io.PrintWriter;
import java.util.Scanner;

public class Attendant {
    public Attendant(String attentdentID) {
        this.attentdantID = attentdentID;
    }

    public Attendant() {
    }

    private String attentdantID;
    private boolean attendantavailability;

    public String getAttentdantID() {
        return attentdantID;
    }

    public void setAttentdantID(String attentdantID) {
        this.attentdantID = attentdantID;
    }

    public boolean getAttendantavailability() {
        return attendantavailability;
    }

    public void setAttendantavailability(boolean attendantavailability) {
        this.attendantavailability = attendantavailability;
    }
    @Override
    public String toString() {
        return "Attendant{" +
                "attentdantID=" + attentdantID +
                ", attendantavailability=" + attendantavailability +
                '}';
    }

    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        String staffID = infile.next();
        attentdantID = infile.next();
        String availability = infile.next();
        attendantavailability = infile.nextBoolean();
    }

    public void save(PrintWriter outfile) {
        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");
        outfile.println("Staff ID");
        outfile.println(attentdantID);
        outfile.println("Availability");
        outfile.println(attendantavailability);
    }


}

