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

    public double calculateHoursParked() {
        if (exitDateTime != null) {
            // Calcular la cantidad de horas estacionado utilizando la fecha y hora de entrada y salida
            // Aquí implementarías la lógica para calcular la diferencia de tiempo
            return 0; // Solo un marcador de posición
        } else {
            return -1; // Indica que el vehículo aún está estacionado
        }
    }

    public int calculateAmountToPay() {
        double hoursParked = calculateHoursParked();
        if (hoursParked >= 0) {
            // Calcular el monto a pagar en función de la cantidad de horas estacionado
            // Aquí implementarías la lógica para calcular el monto
            return 0; // Solo un marcador de posición
        } else {
            return -1; // El vehículo aún está estacionado, no se puede calcular el monto
        }
    }

    public double timePassed() {
        LocalDateTime entryTime = LocalDateTime.parse(entryDateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        LocalDateTime currentTime = LocalDateTime.now();

        // Calcula la duración entre la entrada y el tiempo actual
        Duration duration = Duration.between(entryTime, currentTime);

        // Obtiene el número total de minutos transcurridos
        long minutesPassed = duration.toMinutes();

        // Calcula el número de horas completas y la fracción de hora
        double hoursPassed = minutesPassed / 60.0;

        return hoursPassed;
    }
    @Override
    public String toString() {
        return "Vehicle: " + vehicle + ", Hora Entrada: " + entryDateTime + ", Horas transcurridas: " + timePassed() + ", Hora salida: " + exitDateTime;
    }

}
