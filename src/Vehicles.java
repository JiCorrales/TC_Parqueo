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

    public  static boolean validType(String vehicleType) {
        String[] validVehicleTypes = {"liviano", "mediano", "largo", "motocicleta", "bicicleta"};
        for (String validVehicleType : validVehicleTypes) {
            if (vehicleType.equals(validVehicleType)) {
                return true;
            }
        }
        return false;
    }
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
    // Método para validar la placa
    public static boolean validPlate(String plate) {
        return plate.matches("\\d{6}[a-zA-Z]");
    }
}