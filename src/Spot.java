import java.util.Date;
public class Spot {
    private Vehicles vehicle;
    private boolean isOccupied;

    // Constructor
    public Spot() {
        this.vehicle = null;
        this.isOccupied = false;
    }
    // Overridden toString method
    // This method should return a string with the spot status and the vehicle parked in it
    @Override
    public String toString() {
        String status = "Libre";
        if (isOccupied) status = "Ocupado";

        if (vehicle != null)
            return "Status: " + status + " " + vehicle;
        return "Status: " + status;
    }
    public Vehicles getVehicle() {
        return vehicle;
    } // Get the vehicle parked in the spot

    public void setVehicle(Vehicles vehicle) {
        this.vehicle = vehicle;
    } // Set the vehicle parked in the spot

    public boolean isOccupied() {
        return isOccupied;
    } // Get the status of the spot

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    } // Set the status of the spot

    public void occupySpot(Vehicles vehicle) { // Method to occupy the spot
        this.vehicle = vehicle;
        this.isOccupied = true;

    }
}
