public class Validations {
    // Method to validate the vehicle type
    public  static boolean validType(String vehicleType) {
        // The vehicle type must be one of the following
        String[] validVehicleTypes = {"liviano", "mediano", "largo", "microbus", "bus", "motocicleta", "bicicleta"};
        // Iterates through the valid vehicle types and returns true if the vehicle type is valid
        for (String validVehicleType : validVehicleTypes) {
            if (vehicleType.equals(validVehicleType)) {
                return true;
            }
        }
        return false;
    }
    // Method to validate the license plate
    // This could be modified in order to be more specific, like
    // checking that the first 3 characters are letters and the last 3 are digits
    public static boolean validPlate(String plate) {
        // The plate must have 6 characters
        if (plate.length() != 6) {
            return false;
        }
        // The first 6 characters must be digits
        for (char c : plate.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
