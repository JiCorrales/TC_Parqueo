import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VehicleFlow {
    private final Vehicles vehicle;
    private final String entryDateTime; // String for storing the date and time of entry in the desired format
    private String exitDateTime = "El vehículo no ha salido"; // String for storing the date and time of exit in the desired format

    // Constructor
    public VehicleFlow(Vehicles vehicle, String entryDateTime) {
        this.vehicle = vehicle;
        this.entryDateTime = entryDateTime;
    }

    public VehicleFlow(Vehicles vehicle) {
        this.vehicle = vehicle;
        this.entryDateTime = getEntryDate();

    }

    // Overridden toString method
    // This method should return a string with the vehicle flow information
    @Override
    public String toString() {
        double hoursPassed = calculateHoursPassed();
        String hoursPassedString = String.format("%.1f", hoursPassed);
        return "Vehículo: " + vehicle + ", Hora Entrada: " + entryDateTime + ", Horas transcurridas: " +
                hoursPassedString + ", Hora salida: " + exitDateTime + ", Monto a pagar: $";
    }

    public void setExitDateTime(String exitDateTime) {
        this.exitDateTime = exitDateTime;
    } // Set the exit date and time

    public Vehicles getVehicle() {
        return vehicle;
    } // Get the vehicle

    public String getEntryDate() {
        return entryDateTime;
    } // Get the entry date and time

    public String getExitDateTime() {
        return exitDateTime;
    } // Get the exit date and time

    // Method to calculate the hours passed since the vehicle entered the parking lot
    public double calculateHoursPassed() {
        // Parse the entry date and time to a LocalDateTime object
        LocalDateTime entryTime = LocalDateTime.parse(entryDateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        LocalDateTime currentTime = LocalDateTime.now(); // Get the current date and time
        Duration duration = Duration.between(entryTime, currentTime); // Calculate the duration between the entry time and the current time
        long minutesPassed = duration.toMinutes(); // Get the minutes passed
        return minutesPassed / 60.0; // Return the hours passed
    }

}

