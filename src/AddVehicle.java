import java.util.Scanner;

public class AddVehicle {
    private static final String[] VALID_VEHICLE_TYPES = {"car", "truck", "Dump Car", "motorcycle", "bicycle"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String vehicleType;
        String licensePlate;
        boolean isValidVehicleType;

        do {
            System.out.println("Enter the type of vehicle: ");
            vehicleType = scanner.nextLine().toLowerCase();
            isValidVehicleType = isValidVehicleType(vehicleType);

            if (!isValidVehicleType) {
                System.out.println("Invalid vehicle type. Please enter a valid vehicle type.");
            }
        } while (!isValidVehicleType);

        if (vehicleType.equals("bicycle")) {
            do {
                System.out.println("Enter the description of the bicycle: ");
                licensePlate = scanner.nextLine();
            } while (!isValidLicensePlate(licensePlate));
        } else {
            do {
                System.out.println("Enter the license plate of the vehicle: ");
                licensePlate = scanner.nextLine();
            } while (!isValidLicensePlate(licensePlate));
        }

        // Check if the vehicle is already on the parking lot
        // Assuming the parking lot is a list of vehicles
        // If the vehicle is already on the parking lot, print a message and exit
        // If the vehicle is not on the parking lot, add it to the parking lot
    }

    private static boolean isValidVehicleType(String vehicleType) {
        for (String validVehicleType : VALID_VEHICLE_TYPES) {
            if (vehicleType.equals(validVehicleType)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidLicensePlate(String licensePlate) {
        // Assuming a valid license plate is a 6-digit number followed by a letter
        return licensePlate.matches("\\d{6}[a-zA-Z]");
    }
}