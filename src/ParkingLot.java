import java.util.Scanner;

public class ParkingLot {
    private Spot[] spots;
    private VehicleFlow[] vehicleFlow;

    public ParkingLot() {
        this.spots = new Spot[35];
        this.vehicleFlow = new VehicleFlow[200];
    }

    public VehicleFlow[] getVehicleFlow() {
        return vehicleFlow;
    }
}
