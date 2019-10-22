package mcp.zones;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;


public class Zone {
    private String zonetype;
    private String space[];
    private double chargingprice;

    public Zone() {
    }

    private final static String occupied = "occupied";
    private final static String free = "free";


    public Zone(String zoneType, int sp) {

        this.zonetype = zoneType;
        this.space = new String[sp];

        settingSpace();
    }

    /**
     * setting up spaces in Zones
     */
    private void settingSpace() {
        for (int i = 0; i < this.space.length; i++) {
            if (this.space[i] == null) {
                this.space[i] = free;
            } else {
                this.space[i] = occupied;
            }
        }
    }

    /**
     * fing the empty space in the zone and
     *
     * @return spaceID
     */
    public int selectSpace() {


        int j;
        int s = -1;
        for (j = 0; j < this.space.length; j++) {
            if (this.space[j].equals(free)) {
                this.space[j] = occupied;

                s = j;
                break;
            }
        }
//

        return s;

    }

    /**
     * freeing an block of space and assigning it free
     *
     * @param space
     */
    public void removeSpace(int space) {

        if (this.space[space].equals(occupied)) {
            this.space[space] = free;
        }
    }

    @Override
    public String toString() {
        return "Zone{" +
                "zonetype='" + zonetype + '\'' +
                ", space=" + Arrays.toString(space) +
                '}';
    }

    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        for (int i = 0; i < space.length; i++) {
            (space[i]) = infile.next();
        }


    }

    public void save(PrintWriter outfile) {

        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");
        //outfile.println(type);
        outfile.println(zonetype);
//        outfile.println(space.toString());
        for (int i = 0; i < space.length; i++) {
            outfile.println(space[i]);
        }
        outfile.println(chargingprice);
//        outfile.close();
//        return zonetype;

//
//
//
    }

    public String getZonetype() {
        return zonetype;
    }

    public void setZonetype(String zonetype) {
        this.zonetype = zonetype;
    }

    public String[] getSpace() {
        return space;
    }

    public void setSpace(String[] space) {
        this.space = space;
    }

    public double getChargingprice() {
        return chargingprice;
    }

    public void setChargingprice(double chargingprice) {
        this.chargingprice = chargingprice;
    }


}



//    public void parkinggrid(boolean x) {
//        //for (int i = 0; i < capacity; i++) {
//        for(int row = 0; row<parkingZones.length ;row++){
//            for(int col = 0; col < parkingZones[row].length; col++){
//                parkingZones[row][col] = row * col;
////
//            }










