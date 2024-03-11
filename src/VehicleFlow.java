import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VehicleFlow {
    private Vehicles vehicle;
    private String entryDateTime; // String para almacenar la fecha y hora de entrada en formato deseado
    private String exitDateTime = "El vehículo no ha salido."; // String para almacenar la fecha y hora de salida en formato deseado

    public VehicleFlow(Vehicles vehicle, String entryDateTime) {
        this.vehicle = vehicle;
        this.entryDateTime = entryDateTime;
    }

    public void setExitDateTime(String exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    public String getEntryDate() {
        return entryDateTime;
    }

    public String getExitDateTime() {
        return exitDateTime;
    }

    public double calculateHoursPassed() {
        LocalDateTime entryTime = LocalDateTime.parse(entryDateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(entryTime, currentTime);
        long minutesPassed = duration.toMinutes();
        return minutesPassed / 60.0;
    }
    @Override
    public String toString() {
        double hoursPassed = calculateHoursPassed();
        String hoursPassedString = String.format("%.1f", hoursPassed);
        return "Vehículo: " + vehicle + ", Hora Entrada: " + entryDateTime + ", Horas transcurridas: " + hoursPassedString + ", Hora salida: " + exitDateTime;
    }
}

