import java.util.Date;

public class VehicleFlow {
    private Vehicles vehicle;
    private Date entryTime;
    private Date exitTime;

    public VehicleFlow(Vehicles vehicle) {
        this.vehicle = vehicle;
        this.entryTime = new Date();
    }

    public void setExitTime() {
        if (exitTime == null) {
            exitTime = new Date();
        }
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }
    @Override
    public String toString() {
        return "Data:" + vehicle + ", Entró:" + entryTime;
    }
}
