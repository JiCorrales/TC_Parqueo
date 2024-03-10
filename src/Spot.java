import java.util.Date;
public class Spot {
    private Vehicles vehicle;
    private boolean isOccupied;

    public Spot() {
        this.vehicle = null;
        this.isOccupied = false;
    }
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
    }

    public void setVehicle(Vehicles vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    public void occupySpot(Vehicles vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;

    }
}
