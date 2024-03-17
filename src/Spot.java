public class Spot {
    private Vehicles vehicle;
    private boolean isOccupied;
private VehicleFlow latestFlow;

    // Constructor
    public Spot() {
        this.vehicle = null;
        this.isOccupied = false;
        this.latestFlow = getLatestFlow();
    }
    // Overridden toString method
    // This method should return a string with the spot status and the vehicle parked in it

//    public String toString() {
//        String spotInfo = ""; // String to hold spot information
//
//        if (isOccupied()) { // Check if the spot is occupied by a vehicle
//            spotInfo += "Status: Ocupado"; // Indicate that the spot is occupied
//
//            Vehicles vehicle = getVehicle(); // Get the vehicle parked in the spot
//            spotInfo += " Tipo: " + vehicle.getType(); // Append vehicle type to spot information
//            spotInfo += ", Placa: " + vehicle.getPlate(); // Append vehicle plate to spot information
//
//            // Get the latest vehicle flow associated with the vehicle
//            VehicleFlow latestFlow = getLatestFlow();
//            if (latestFlow != null) { // Check if there is a vehicle flow
//                spotInfo += ", Hora de ingreso: " + latestFlow.getEntryDate(); // Append entry time to spot information
//                spotInfo += ", Horas transcurridas: " + latestFlow.calculateHoursPassed(); // Append exit time to spot information
//            }
//        } else {
//            spotInfo += "Status: Libre"; // Indicate that the spot is free
//        }
//
//        return spotInfo;
    @Override
    public String toString() {
        VehicleFlow latestFlow = getLatestFlow();
        String status = "Libre";
        if (isOccupied) status = "Ocupado";
        if (vehicle != null)
            return "Status: " + status + " " + vehicle  +", Hora de ingreso: " + getLatestFlow().getEntryDate() + ", Horas transcurridas: " + String.format("%.1f", getLatestFlow().calculateHoursPassed());
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
    public VehicleFlow getLatestFlow() {
        return this.latestFlow;
    }
    public void setLatestFlow(VehicleFlow latestFlow) {
        this.latestFlow = latestFlow;
    }

}
