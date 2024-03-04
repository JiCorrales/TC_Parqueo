import java.util.Scanner;

public class ParkingLot {
    private Spot[] spots;
    private VehicleFlow[] vehicleFlow;


    public ParkingLot() {
        // Starts with 35 spots
        this.spots = new Spot[35];
        // Starts with 200 vehicle flows
        this.vehicleFlow = new VehicleFlow[200];
        // Initializes the spots
        for (int i = 0; i < spots.length; i++) {
            spots[i] = new Spot();
        }
    }

    @Override
    public String toString() {
        String strValue = "***** PARQUEO *****\n";
        int spotsPerRow = 10;
        // Iterates through the spots and adds them to the string
        for (int i = 0; i < 20; i++) {
            strValue += (i + 1) + ". " + spots[i].toString() + "\n";
            if ((i + 1) % spotsPerRow == 0 && i != spots.length - 1) {
                strValue += "-----------------\n"; // Separator between rows
            }
        }
        for (int i = 20; i < 35; i++) {
            if (i <=24) {
                strValue += (i + 1) + ". " + spots[i].toString() + "\n";
            }
            else {
                strValue += "M" + (i - 24) + ". " + spots[i].toString() + "\n";
            }
            if ((i + 1) % 35 == 0) {
                strValue += "-----------------\n"; // Separator between rows
            }
        }
        return strValue;
    }
    // Returns the next empty spot
    private int nextEmptySpot() {
        for (int i = 0; i < spots.length; i++) {
            if (!spots[i].isOccupied()) {
                return i;
            }
        }
        return -1;
    }
    // Checks if the placement is valid
    private boolean isValidPlacement(int startSpot, int spotsNeeded) {
        int spotsPerRow = 10;
        int endRow = (startSpot + spotsNeeded - 1) / spotsPerRow;
        // Checks if the vehicle is too long to fit in the parking lot
        if (endRow == 0 && startSpot % spotsPerRow == spotsPerRow - 1) {
            return false;
        }
        // Checks if the vehicle fits in the parking lot
        for (int i = startSpot; i < startSpot + spotsNeeded; i++) {
            if (i >= spots.length || spots[i].isOccupied()) {
                return false;
            }
        }
        // Checks if the vehicle fits in the same row
        return true;
    }

    public boolean assignSpot(Vehicles vehicle) {
        // Gets the amount of spots needed for the vehicle
        int spotsNeeded = vehicle.getSpotsNeeded();
        // Gets the next empty spot
        int startSpot = nextEmptySpot();
        // If there is an empty spot and the placement is valid, the vehicle is assigned to the spot
        if (startSpot != -1 && isValidPlacement(startSpot, spotsNeeded)) {
            for (int i = startSpot; i < startSpot + spotsNeeded; i++) {
                spots[i].occupySpot(vehicle);
            }
            // Returns true if the vehicle was assigned to a spot
            return true;
        } else {
            // Returns false if the vehicle was not assigned to a spot
            return false;
        }
    }


    public void addVehicle() {
        // Asks for the vehicle type
        String vehicleType = askForVehicleType();
        // If the vehicle type is valid
        if (vehicleType != null) {
            // Asks for the plate or description
            String plateOrDescription = askForPlateOrDescription(vehicleType);
            // If the plate or description is valid
            if (plateOrDescription != null) {
                // Creates the vehicle
                Vehicles newVehicle = createVehicle(vehicleType, plateOrDescription);
                // If the vehicle was created
                if (newVehicle != null) {
                    // Assigns the vehicle to a spot
                    if (assignSpot(newVehicle)) {
                        // Prints a message if the vehicle was assigned to a spot
                        System.out.println("Vehículo agregado al parqueo.");
                    } else {
                        // Prints a message if the vehicle was not assigned to a spot
                        System.out.println("No hay espacio disponible para este tipo de vehículo.");
                    }
                }
            }
        }
    }
    private String askForVehicleType() {
        // Asks for the vehicle type

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el tipo de vehículo a ingresar (Liviano, Mediano, Largo, Motocicleta, Bicicleta): ");
        // Reads the vehicle type
        String vehicleType = scanner.nextLine().toLowerCase();
        // If the vehicle type is valid, it is returned
        if (Vehicles.validType(vehicleType)) {
            return vehicleType;
        } else {
            // If the vehicle type is not valid, a message is printed and null is returned
            System.out.println("Tipo de vehículo inválido. El vehículo no se agregará al parqueo.");
            return null;
        }
    }

    private String askForPlateOrDescription(String vehicleType) {
        // Asks for the plate or description
        Scanner scanner = new Scanner(System.in);
        String plateOrDescription;
        // If the vehicle type is bicycle, it asks for the description
        if (vehicleType.equals("bicicleta")) {
            System.out.println("Ingrese la descripción de la bicicleta: ");
            plateOrDescription = scanner.nextLine();
        } else {
            // If the vehicle type is not bicycle, it asks for the plate
            System.out.println("Ingrese la placa del vehículo: ");
            plateOrDescription = scanner.nextLine();
            // If the plate is not valid, a message is printed and null is returned
            if (!Vehicles.validPlate(plateOrDescription)) {
                System.out.println("Placa inválida. El vehículo no se agregará al parqueo.");
                return null;
            }
        }
        return plateOrDescription;// If the plate or description is valid, it is returned

    }
    // Creates a vehicle
    private Vehicles createVehicle(String vehicleType, String plateOrDescription) {
        return new Vehicles(vehicleType, plateOrDescription);
    }
}
