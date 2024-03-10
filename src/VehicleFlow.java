public class VehicleFlow {

    private Vehicles vehicle;

    private String entryDate;
    private String exitDate;

    public VehicleFlow(Vehicles vehicle) {
        this.vehicle = vehicle;
        this.entryDate = entryDate;
    }



    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getExitDate() {
        return exitDate;
    }

    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
    }



    public Vehicles getVehicle() {
        return vehicle;
    }



    @Override
    public String toString() {
        return "Data:" + vehicle + ", Entró:" + entryDate;
    }
}
