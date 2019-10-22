package mcp.vehicletypes;

public  class Vehicle {
    private String vehicletype;
    private String license_no;

    public boolean isDriverdisable() {
        return driverdisable;
    }

    public void setDriverdisable(boolean driverdisable) {
        this.driverdisable = driverdisable;
    }

    private boolean driverdisable;
    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public Vehicle(String vehicletype, String license_no, boolean driverdisable) {
        this.vehicletype = vehicletype;
        this.license_no = license_no;
        this.driverdisable = driverdisable;
    }
    public Vehicle()
    {}

}