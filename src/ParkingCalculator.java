import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;




public class ParkingCalculator {
    private static final double PARKING_FEE_PER_HOUR = 2.0;
    private static final double PARKING_FEE_PER_MINUTE = PARKING_FEE_PER_HOUR / 60;

    public static void main(String[] args) {
        int entryTimeInSeconds = 12345; //Here im trying an example of an entry time to the parking
        LocalDateTime entryTime = LocalDateTime.now().minusSeconds(entryTimeInSeconds);
        LocalDateTime exitTime = LocalDateTime.now();

        ParkingCalculator calculator = new ParkingCalculator(entryTime, exitTime);
        System.out.println("Parking duration: " + calculator.getParkingDuration());
        System.out.println("Parking fee: " + calculator.getParkingFee());
        System.out.println("Total amount due: " + calculator.getTotalAmountDue());
    }

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingCalculator(LocalDateTime entryTime, LocalDateTime exitTime) {
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
//Here we use the method ChronoUnit that gets the time in seconds
    public long getParkingDuration() {
        return ChronoUnit.SECONDS.between(entryTime, exitTime);
    }

    public double getParkingFee() {
        long parkingDurationInMinutes = getParkingDuration() / 60; //We do this so we can get the time in hours
        return PARKING_FEE_PER_MINUTE * parkingDurationInMinutes;
    }

    public double getTotalAmountDue() {
        return getParkingFee();
    }
}
