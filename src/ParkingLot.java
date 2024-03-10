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
            if (i <= 24) {
                strValue += (i + 1) + ". " + spots[i].toString() + "\n";
            } else {
                strValue += "M" + (i - 24) + ". " + spots[i].toString() + "\n";
            }
            if ((i + 1) % 35 == 0) {
                strValue += "-----------------\n"; // Separator between rows
            }
        }
        return strValue;
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
                String entryDate = askForEntryDate();
                if (entryDate != null) {
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
    public void searchForVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo a buscar: ");
        String plateOrDescription = scanner.nextLine();
        // Search for the vehicle with the plate or description
        Vehicles vehicle = searchVehiclePlateOrDescription(plateOrDescription);
        // We have to fix the search because we are missing the bike description search
        if (vehicle != null) {
            System.out.println("Vehículo encontrado: ");
            // Buscar el flujo del vehículo en el arreglo vehicleFlow
            for (VehicleFlow flow : vehicleFlow) {
                if (flow != null && flow.getVehicle() != null && flow.getVehicle().equals(vehicle)) {
                    System.out.println(flow);
                }
            }
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }
    public void exitVehicle(){
    }
    // Returns the next empty spot
    private int nextEmptySpot(int spotsNeeded, String type) {
        if (type.equals("motocicleta") || type.equals("bicicleta")) {
            // Search for an empty spot in the motorcycle and bicycle rows
            for (int i = 25; i < spots.length; i++) {
                if (!spots[i].isOccupied()) {
                    return i;
                }
            }
            return -1; // No empty spots for motorcycles or bicycles
        } else {
            // Search for an empty spot in the first and second rows, for all other vehicles
            for (int i = 0; i < 20; i++) {
                if (i % 10 + spotsNeeded <= 10 && !spots[i].isOccupied()) {
                    boolean enoughSpaces = true;
                    // Check if there are enough empty spots for the vehicle
                    for (int j = i + 1; j < i + spotsNeeded; j++) {
                        if (spots[j].isOccupied()) {
                            enoughSpaces = false;
                            break;
                        }
                    }
                    if (enoughSpaces) {
                        return i;
                    }
                }
            }
            // If there are no empty spots in the first and second rows, search for empty spots in the third row
            for (int i = 20; i < 24; i++) {
                if (!spots[i].isOccupied()) {
                    return i;
                }
            }
            return -1; // No empty spots for the vehicle
        }
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
            System.out.println("Tipo de vehículo inválido.\nVolviendo al menú...");
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
            // Verifica si la placa es válida
            if (!Vehicles.validPlate(plateOrDescription)) {
                System.out.println("Placa inválida. El vehículo no se agregará al parqueo.");
                return null;
            }
            // Verifica si la placa ya está en uso
            if (checkDuplicatePlate(plateOrDescription)) {
                System.out.println("¡Atención! Ya existe un vehículo con esta placa en el parqueo.");
                return null;
            }
        }
        return plateOrDescription; // If the plate or description is valid, it is returned
    }
    private Vehicles searchVehiclePlateOrDescription(String plateOrDescription) {
        // Iterates through the vehicle flow and searches for the vehicle with the plate or description
        for (VehicleFlow flow : this.vehicleFlow) {
            if (flow != null && flow.getVehicle().getPlateOrDescription().equals(plateOrDescription)) {
                return flow.getVehicle();
            }
        }
        return null;
    }
    private String askForEntryDate(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la fecha de entrada del vehículo: ");
        String entryDate = scanner.nextLine();
        if (entryDate != null) {
            return isValidDate(entryDate) ? entryDate : null;
        } else {
            System.out.println("Fecha inválida.\nVolviendo al menú...");
            return null;
        }
    }
    public static boolean isValidDate(String date){
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    // Creates a vehicle
    private Vehicles createVehicle(String vehicleType, String plateOrDescription) {
        return new Vehicles(vehicleType, plateOrDescription);
    }
    public boolean assignSpot(Vehicles vehicle) {
        // Gets the amount of spots needed for the vehicle
        int spotsNeeded = vehicle.getSpotsNeeded();
        // Gets the next empty spot
        String vehicleType = vehicle.getType();
        int startSpot = nextEmptySpot(spotsNeeded, vehicleType);
        // If there is an empty spot and the placement is valid, the vehicle is assigned to the spot
        if (startSpot != -1 && isValidPlacement(startSpot, spotsNeeded)) {
            for (int i = startSpot; i < startSpot + spotsNeeded; i++) {
                spots[i].occupySpot(vehicle);
            }
            // Adds the vehicle to the vehicle flow
            VehicleFlow vehicleFlow = new VehicleFlow(vehicle);
            for (int i = 0; i < this.vehicleFlow.length; i++) {
                if (this.vehicleFlow[i] == null) {
                    this.vehicleFlow[i] = vehicleFlow;
                    break;
                }
            }
            return true;
        } else {
            // Returns false if the vehicle was not assigned to a spot
            return false;
        }
    }
    // Method to check if the plate is duplicated
    public boolean checkDuplicatePlate(String plate) {
        for (Spot spot : spots) {
            Vehicles vehicle = spot.getVehicle();
            // If the vehicle has the same plate, it returns true
            if (vehicle != null && vehicle.getPlate() != null && vehicle.getPlate().equalsIgnoreCase(plate)) {
                return true;
            }
        }
        return false; // Returns false if the plate is not duplicated
    }
    public void printAllVehicleFlow(){
        // Iterates through the vehicle flow and prints the vehicles
        for (VehicleFlow flow : this.vehicleFlow) {
            if (flow != null) {
                System.out.println(flow.toString());
            }
        }
    }

}

