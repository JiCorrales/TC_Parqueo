public class Vehicles {
    private String type;
    private String plate;
    private String description;
    private int spotsNeeded;
    private int amountCharged;

    public Vehicles(String type, String plateOrDescription) {
        this.type = type;
        if (type.equals("bicicleta")){
            this.description = plateOrDescription;
        } else {
            this.plate = plateOrDescription;
        }
        this.spotsNeeded = spotsNeeded();
        this.amountCharged = getAmountCharged();

    }
    @Override
    public String toString() {
        if (type.equals("bicicleta")) {
            return type + ": " + description + "      espacios: " + spotsNeeded + "  [$" + amountCharged + "]";
        } else {
            return type + ": " + plate + "      espacios: " + spotsNeeded + "  [$" + amountCharged + "]";
        }
    }


    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getPlate() {
        return plate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpotsNeeded() {
        return spotsNeeded;
    }

    public void setSpotsNeeded(int spotsNeeded) {
        this.spotsNeeded = spotsNeeded;
    }

    public void setAmountCharged(int amountCharged) {
        this.amountCharged = amountCharged;
    }

    // Method to validate the vehicle type
    public  static boolean validType(String vehicleType) {
        // The vehicle type must be one of the following
        String[] validVehicleTypes = {"liviano", "mediano", "largo", "motocicleta", "bicicleta"};
        // Iterates through the valid vehicle types and returns true if the vehicle type is valid
        for (String validVehicleType : validVehicleTypes) {
            if (vehicleType.equals(validVehicleType)) {
                return true;
            }
        }
        return false;
    }
    // Method to get the amount charged
    // This should be modified to return the amount charged based on the vehicle type and
    // the time the vehicle was parked
    public int getAmountCharged() {
        switch (type) {
            case "liviano":
                return 1000;
            case "mediano":
                return 2000;
            case "largo":
                return 3000;
            case "motocicleta":
            case "bicicleta":
                return 800;
            default:
                return -1;
        }
    }
    public int spotsNeeded() {
        switch (type) {
            case "liviano":
            case "motocicleta":
            case "bicicleta":
                return 1;
            case "mediano":
                return 2;
            case "largo":
                return 3;

            default:
                return -1;
        }
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

    public Object getPlateOrDescription() {
        if (type.equals("bicicleta")) {
            return description;
        } else {
            return plate;
        }
    }
}