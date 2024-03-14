import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParkingLot {
    private final Spot[] spots;
    private final VehicleFlow[] vehicleFlow;

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



    /*
     * First option in the menu.
     * Method to add a vehicle to the parking lot.
     * It asks for the vehicle type, the plate or description, and the entry date.
     * It creates the vehicle and adds it to the parking lot.
     */
    public void addVehicle() {
        String vehicleType = askForVehicleType(); // Asks for the vehicle type
        if (vehicleType != null) {   // If the vehicle type is valid
            String plateOrDescription = askForPlateOrDescription(vehicleType);// Asks for the plate or description
            if (plateOrDescription != null) { // If the plate or description is valid
                Vehicles newVehicle = createVehicle(vehicleType, plateOrDescription);// Creates the vehicle
                if (!checkDuplicatePlate(newVehicle.getPlate(), vehicleType)) {
                    if (askAndAssignEntryDateSpot(newVehicle)) {
                        System.out.println("Vehículo agregado al parqueo."); // Prints a message if the vehicle was assigned to a spot
                    } else {
                        System.out.println("No hay espacio disponible para este tipo de vehículo."); // Prints a message if the vehicle was not assigned to a spot
                    }
                }
            }
        }
    }

    private String askForVehicleType() {
        Scanner scanner = new Scanner(System.in);
        String vehicleType;
        do {
            System.out.println("Ingrese el tipo de vehículo a ingresar (Liviano (1), Mediano (2), Largo (3), Microbus (4), Bus (5), Motocicleta, Bicicleta): ");
            vehicleType = scanner.nextLine().toLowerCase();
            if (vehicleType.isEmpty()) System.out.println("Debe ingresar algo para continuar.");
            else if (!Validations.validType(vehicleType))
                System.out.println("Tipo de vehículo inválido. Por favor, ingrese un tipo válido.");
        } while (vehicleType.isEmpty() || !Validations.validType(vehicleType));

        return vehicleType;
    }

    private String askForPlateOrDescription(String vehicleType) { // Asks for the plate or description
        Scanner scanner = new Scanner(System.in);
        String plateOrDescription;
        // If the vehicle type is bicycle, it asks for the description
        do { // Loop to keep asking for the plate or description
            if (vehicleType.equals("bicicleta")) { // If the vehicle type is bicycle, it asks for the description
                System.out.println("Ingrese la descripción de la bicicleta: ");
                plateOrDescription = scanner.nextLine();
            } else { // If the vehicle type is not bicycle, it asks for the plate
                System.out.println("Recuerde que la placa debe tener 6 dígitos, pueden ser números o letras.\n" +
                        "Ingrese la placa del vehículo: ");
                plateOrDescription = scanner.nextLine();
                if (!Validations.validPlate(plateOrDescription)) {
                    System.out.println("Placa inválida. Por favor, ingrese una placa válida.");
                    plateOrDescription = null;
                } else { //It checks if the plate is duplicated
                    if (checkDuplicatePlate(plateOrDescription, vehicleType)) {
                        plateOrDescription = null;
                    }
                }
            }
        } while (plateOrDescription == null);

        return plateOrDescription; // Returns the plate or description
    }
    public boolean checkDuplicatePlate(String plate, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("bicicleta")) {
            return false; // If the vehicle is a bicycle, it does not check for duplicate plates
        }
        // Verifies if the vehicle is already in the parking lot
        for (Spot spot : spots) {
            if (spot != null) { // Check if the spot is not null
                Vehicles vehicle = spot.getVehicle(); // Gets the vehicle from the spot
                if (vehicle != null) { // Check if the vehicle is not null
                    if (vehicle.getPlate().equalsIgnoreCase(plate)) { // Compare plates to check if the vehicle is already in the parking lot
                        if (!vehicle.getType().equalsIgnoreCase(vehicleType)) { // If the vehicle is not the same type
                            System.out.println("¡Atención! Ya existe un vehículo con la misma placa pero de diferente tipo en el parqueo. No se puede agregar el nuevo vehículo.");
                        } else {
                            System.out.println("¡Atención! Ya existe un vehículo con esta placa en el parqueo. Por favor, ingrese una placa diferente.");
                        }
                        return true;
                    }
                }
            }
        }

        // Si el vehículo no está en el parqueo, se verifica si ya salió
        for (VehicleFlow flow : vehicleFlow) {
            if (flow != null && flow.getVehicle() != null && flow.getVehicle().getPlate().equalsIgnoreCase(plate)  && flow.getVehicle().getType().equalsIgnoreCase(vehicleType) && flow.getExitDateTime() != null) {return false;
            }
        }
        return false; // Si no se encontró ningún vehículo con la misma placa, se permite ingresar
    }
    private Vehicles createVehicle(String vehicleType, String plateOrDescription) {
        return new Vehicles(vehicleType, plateOrDescription);
    }
    private boolean askAndAssignEntryDateSpot(Vehicles vehicle) {
        String entryDate; // Variable to store the entry date
        do entryDate = askForEntryDate(); // Asks for the entry date
        while (!isValidDateTime(entryDate)); // While the entry date is not valid, it keeps asking for the entry date
        return assignSpot(vehicle, entryDate); // Assigns the vehicle to a spot
    }
    private String askForEntryDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Le recordamos que la fecha y hora de entrada necesita el siguiente formato:'dd/MM/yyyy HH:mm'\n" +
                "Ingrese la fecha de entrada del vehículo: ");
        return scanner.nextLine();
    }
    public boolean isValidDateTime(String dateTime) {
        try { // Tries to parse the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Format for the date and time
            LocalDateTime.parse(dateTime, formatter); // Parses the date and time
            return true;
        } catch (DateTimeParseException e) { // Catches the exception if the date and time are not valid
            System.out.println("Fecha y hora inválidas. Por favor, ingrese la fecha y hora en el formato correcto.");
            return false;
        }
    }


    public boolean assignSpot(Vehicles vehicle, String entryDate) {
        int spotsNeeded = vehicle.getSpotsNeeded(); // Gets the amount of spots needed for the vehicle
        String vehicleType = vehicle.getType();  // Gets the next empty spot

        int startSpot = nextEmptySpot(spotsNeeded, vehicleType); // Gets the next empty spot
        if (startSpot != -1 && isValidPlacement(startSpot, spotsNeeded)) { // If there are empty spots and the placement is valid, the vehicle is assigned to the spot
            for (int i = startSpot; i < startSpot + spotsNeeded; i++) {
                spots[i].occupySpot(vehicle);
            }
            VehicleFlow vehicleFlow = new VehicleFlow(vehicle, entryDate); // Adds the vehicle to the vehicle flow
            for (int i = 0; i < this.vehicleFlow.length; i++) {
                if (this.vehicleFlow[i] == null) {
                    this.vehicleFlow[i] = vehicleFlow;
                    break; // Breaks the loop after adding the vehicle to the vehicle flow
                }
            }
            return true; // Returns true if the vehicle was assigned to a spot
        } else {
            return false; // Returns false if the vehicle was not assigned to a spot

        }
    }
    private int nextEmptySpot(int spotsNeeded, String type) {
        if (type.equals("motocicleta") || type.equals("bicicleta")) { // If the vehicle is a motorcycle or bicycle
            for (int i = 25; i < spots.length; i++) { // Search for an empty spot in i = 25 to i = 34
                if (!spots[i].isOccupied()) return i;
            }
            return -1; // No empty spots for motorcycles or bicycles
        } else {
            // Search for an empty spot in the first and second rows, for all other vehicles
            for (int i = 0; i < 20; i++) { // Search for an empty spot in i = 0 to i = 19
                if (!spots[i].isOccupied()) { // If the spot is not occupied
                    if (i % 10 + spotsNeeded <= 10) { // If the vehicle fits in the same row
                        return i; // Returns the first empty spot
                    }
                }
            }

            // If there are no empty spots in the first and second rows, search for empty spots in the third row
            for (int i = 20; i < 25; i++) { // Search for an empty spot in i = 20 to i = 24
                if (!spots[i].isOccupied()) { // If the spot is not occupied
                    if (spotsNeeded <= 5) { // If i + spotsNeeded fits in the same row
                        return i; // Returns the first empty spot
                    }

                }
            }
            return -1; // No empty spots for the vehicle
        }
    }
    private boolean isValidPlacement(int startSpot, int spotsNeeded) {
        // Checks if the placement is valid
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
    /*
     * Second option in the menu.
     * Method to search for a vehicle in the parking lot.
     */
    public void searchForVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa o descripción del vehículo a buscar: ");
        String plateOrDescription = scanner.nextLine();
        Vehicles[] matchingVehicles = searchVehiclesByPlateOrDescription(plateOrDescription);
        if (matchingVehicles.length > 0) {
            System.out.println("Vehículos encontrados: ");
            for (Vehicles vehicle : matchingVehicles) {
                System.out.println("Información del vehículo:");
                System.out.println(vehicle);
                VehicleFlow flow = getVehicleFlowForVehicle(vehicle);
                if (flow != null) {
                    System.out.println("Cantidad de horas al momento: " + String.format("%.1f", flow.calculateHoursPassed()));
                    double amountCharged = amountToCharge(flow.calculateHoursPassed(), flow);
                    if (amountCharged != -1) {
                        System.out.println("Monto hasta el momento: $" + amountCharged);
                    } else {
                        System.out.println("Tipo de vehículo no válido. No se puede calcular el monto.");
                    }
                } else {
                    System.out.println("No se pudo obtener los movimientos para el vehículo.");
                }
            }
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }
    private Vehicles[] searchVehiclesByPlateOrDescription(String plateOrDescription) {
        Vehicles[] matchingVehicles = new Vehicles[vehicleFlow.length]; // Array for the matching vehicles
        int count = 0; // Counter for the matching vehicles
        for (VehicleFlow flow : this.vehicleFlow) { // Iterates through the vehicle flow
            if (flow != null && flow.getVehicle() != null) {
                Vehicles vehicle = flow.getVehicle(); // Gets the vehicle from the vehicle flow
                if (vehicle.getPlateOrDescription().equalsIgnoreCase(plateOrDescription)) { // If the plate or description matches
                    matchingVehicles[count++] = vehicle; // Adds the vehicle to the matching vehicles array
                } else if (vehicle.getType().equalsIgnoreCase("bicicleta") && vehicle.getDescription().contains(plateOrDescription)) {
                    matchingVehicles[count++] = vehicle; // If the vehicle is a bicycle and the description contains the input, it adds the vehicle to the matching vehicles array
                }
            }
        }
        Vehicles[] result = new Vehicles[count]; // Array for the result
        System.arraycopy(matchingVehicles, 0, result, 0, count); // Copies the matching vehicles to the result array
        return result; // Returns the result array
    }
    private VehicleFlow getVehicleFlowForVehicle(Vehicles vehicle) {
        for (VehicleFlow flow : vehicleFlow) {
            if (flow != null && flow.getVehicle().equals(vehicle)) {
                return flow;
            }
        }
        return null;
    }
    /*
     * Third option in the menu.
     * Method to exit a vehicle from the parking lot.
     */
    public void exitVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Desea sacar un vehículo por placa o por campo?");
        System.out.println("1. Por placa");
        System.out.println("2. Por campo");
        int option = scanner.nextInt();
        scanner.nextLine();
        if (option == 1) {
            exitVehicleByPlate(); // Exit vehicle by plate
        } else if (option == 2) {
            exitVehicleBySpot(); // Exit vehicle by spot
        } else {
            System.out.println("Opción inválida. Volviendo al menú principal.");
        }
    }
    public void exitVehicleByPlate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo a sacar: ");
        String plate = scanner.nextLine();
        if (Validations.validPlate(plate)){
                Vehicles vehicle = searchVehicleByPlate(plate);
            if (vehicle != null) {
                removeVehicle(vehicle);
                System.out.println("Vehículo sacado del parqueo.");
            } else {
                System.out.println("Vehículo no encontrado.");
            }
        } else {
            System.out.println("Placa inválida. Volviendo al menú principal.");

        }
    }
    private Vehicles searchVehicleByPlate(String plate) {
        for (Spot spot : spots) {
            Vehicles vehicle = spot.getVehicle();
            if (vehicle != null && vehicle.getPlate().equalsIgnoreCase(plate)) {
                return vehicle;
            }
        }
        return null;
    }
    public void exitVehicleBySpot() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número de parqueo del vehículo a sacar: ");
        int spotNumber = scanner.nextInt();
        scanner.nextLine();
        if (spotNumber < 1 || spotNumber > 35) { // If the spot number is invalid
            System.out.println("Número de parqueo inválido.");
            return;
        }
        Spot spot = spots[spotNumber - 1]; // Gets the spot
        if (spot.isOccupied()) { // If the spot is occupied
            Vehicles vehicle = spot.getVehicle(); // Gets the vehicle from the spot
            removeVehicle(vehicle); // Removes the vehicle

            System.out.println("Vehículo sacado del parqueo.");
        } else {
            System.out.println("El campo está vacío.");
        }
    }
    public void removeVehicle(Vehicles vehicle) {
        int index = 1;
        for (VehicleFlow flow : vehicleFlow) { // Iterates through the vehicle flow
            if (flow != null && flow.getVehicle().equals(vehicle)) { // If the vehicle is found
                flow.setExitDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))); // Sets the exit date and time
                System.out.println(index + "."+ " Sacando el Vehículo:" + "\n" + vehicle); // Prints the vehicle
                // Prints the entry date, exit date, hours passed, and amount to charge
                System.out.println("Datos: Hora Entrada: " + flow.getEntryDate() + ", Hora Salida: " + flow.getExitDateTime()
                        + ", Horas transcurridas: " + String.format("%.1f", flow.calculateHoursPassed())
                        + ", Monto a cobrar: $" + amountToCharge(flow.calculateHoursPassed(), flow));
                System.out.println("Vehículo sacado del parqueo." + "\n");
                break;
            }
            index++;
        }
        for (Spot spot : spots) { // Iterates through the spots
            if (spot.getVehicle() != null && spot.getVehicle().equals(vehicle)) { // If the vehicle is found
                spot.setOccupied(false); // Sets the spot as unoccupied
                spot.setVehicle(null); // Removes the vehicle from the spot
            }
        }
    }

    /*
     * Fourth option in the menu.
     * Method to print the parking lot.
     * It overrides the toString method to print the parking lot.
     */
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
    /*
     * Fifth option in the menu.
     * Method to print the vehicle flow.
     */
    public void printAllVehicleFlow() {
        System.out.println("Historial de vehículos:");
        for (VehicleFlow flow : this.vehicleFlow) { // Iterates through the vehicle flow
            if (flow != null) {
                System.out.println(flow); // Prints the vehicle flow
            }
        }
    }

    /*
     * Sixth option in the menu.
     * Method to close the parking lot.
     */
    public void closeParking() {
        System.out.println("Cerrando el parqueo.");
        exitAllVehicles(); // Exits all the vehicles
        System.out.println("El monto total cobrado es: $" + calculateTotalAmountCharged());
        System.out.println("El parqueo ha sido cerrado. ¡Hasta luego!");
    }
    public void exitAllVehicles() { // Iterates through the spots and removes the vehicles
        for (Spot spot : spots)
            if (spot.getVehicle() != null)
                removeVehicle(spot.getVehicle()); // If the spot is occupied, it removes the vehicle
    }


    /*
     * Start of money related methods
     *
     */
    public int amountToCharge(double hours, VehicleFlow vehicleFlow) {
        Vehicles vehicle = vehicleFlow.getVehicle(); // Gets the vehicle from the vehicle flow
        int hourlyRate = vehicle.getAmountCharged(); // Gets the amount charged for the vehicle
        if (hourlyRate == -1) {
            return -1;
        }
        int totalAmount;
        // If the hours passed is a whole number, it multiplies the hours by the hourly rate
        if (hours - Math.floor(hours) >= 0.5) totalAmount = (int) Math.ceil(hours) * hourlyRate;
        // Else, it multiplies the hours by the hourly rate and adds half of the hourly rate
        else totalAmount = (int) Math.floor(hours) * hourlyRate + hourlyRate / 2;
        return totalAmount;
    }

    public double calculateTotalAmountCharged() {
        double totalAmount = 0;
        for (VehicleFlow flow : vehicleFlow) {
            if (flow != null) {
                double hours = flow.calculateHoursPassed();
                int amount = amountToCharge(hours, flow);
                totalAmount += amount;
            }
        }
        return totalAmount;
    }

    /*
     * Seventh option in the menu.
     * Method to exit the system.
     */
    public void exitSystem() {
        // Messages to show when the system is exiting
        String[] messages = {
                "Saliendo del sistema...",
                "3...",
                "2...",
                "1...",
                "Has salido del sistema.",
                "¡Hasta luego!"
        };
        // Iterates through the messages and prints them
        for (String message : messages) {
            System.out.println(message);
            try {
                Thread.sleep(750); // Sleeps for 750 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

