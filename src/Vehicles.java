
public class Vehicles {
    private String type; // The type of vehicle
    private String plate; // The license plate of the vehicle
    private String description; // The description of the vehicle
    private final int spotsNeeded; // The number of spots needed by the vehicle
    private final int amountCharged; // The base amount charged to the vehicle
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
            return "Tipo: " + type + ", Descripción: " + description + ", Espacios: " + spotsNeeded + ", Cobro Base:[$" + amountCharged + "]";
        } else {
            return "Tipo: " + type + ", Placa: " + plate + ", Espacios: " + spotsNeeded + ", Cobro Base:[$" + amountCharged + "]";
        }
    }
    public String getDescription() {
        return description;
    } // Get the description of the vehicle

    public String getType() {
        return type;
    } // Get the type of vehicle
    public String getPlate() {
        return plate;
    } // Get the license plate of the vehicle
    public int getSpotsNeeded() {
        return spotsNeeded;
    } // Get the number of spots needed by the vehicle
    /*
     * Method to get the amount charged to the vehicle
     */
    public int getAmountCharged() {
        return switch (type) {
            case "motocicleta", "bicicleta" -> 800;
            case "liviano" -> 1000;
            case "mediano" -> 2000;
            case "largo" -> 3000;
            case "microbus" -> 4000;
            case "bus" -> 5000;
            default -> -1;
        };
    }
    /*
     * Method to get the number of spots needed by the vehicle
     */
    public int spotsNeeded() {
        return switch (type) {
            case "liviano", "motocicleta", "bicicleta" -> 1;
            case "mediano" -> 2;
            case "largo" -> 3;
            case "microbus" -> 4;
            case "bus" -> 5;
            default -> -1;
        };
    }
    /*
     * Method to get the license plate or the description of the vehicle
     */
    public String getPlateOrDescription() {
        if (type.equals("bicicleta")) {
            return description;
        } else {
            return plate;
        }
    }
}