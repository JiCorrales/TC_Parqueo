import java.util.Date;
import java.util.Scanner;

public class VehicleFlow {
    private Vehicles vehicle;

    private Date entryTime;
    private Date exitTime;

    public VehicleFlow(Vehicles vehicle) {
        this.vehicle = vehicle;
        this.entryTime = new Date();
    }
    public void setExitTime() {
        if (exitTime == null)
            this.exitTime = new Date();
    }
}


