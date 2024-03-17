import java.time.LocalDateTime;
import java.util.Objects;
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
     * 03/17/2024 - 05:17 AM
     * Here I realized we are doing some dumb things
     * Why are we creating a new vehicle with the values we are asking for? We should be looking for an existing vehicle.
     * If there is a vehicle with the same plate and type, we should be looking for it in the parking lot
     * and if it's not there, then we add it. So it would be better to create a method to find an existing vehicle.
     * There is no time to do it now, but I will leave a comment in the code to remember my mistakes a realize this on
     * future projects.
     *
     */
     private Vehicles findExistingVehicle(String vehicleType, String plateOrDescription) {

        if (!vehicleType.equalsIgnoreCase("bicicleta")) { // If the vehicle type is not bicycle
            for (VehicleFlow flow : vehicleFlow) { // Iterates through the vehicle flow
            // If the vehicle is found in the vehicle flow
                if (flow != null && flow.getVehicle() != null &&
                        flow.getVehicle().getType().equalsIgnoreCase(vehicleType) // If the vehicle type matches
                        && flow.getVehicle().getPlate().equalsIgnoreCase(plateOrDescription) && flow.getExitDateTime() != null) { // If the plate matches and the vehicle has exited
                    return flow.getVehicle(); // Returns the vehicle if it is found in the vehicle flow
                }
            }
        }
        return null;
    }
     /* Something like this, but I don't have time to implement it now. Also, we can't look for bicycles with this method.
     * Because there is no personal information about bicycles, so we can't look for them in the vehicle flow.
     * This means that is none sense to ask for the description of the bicycle, because there could be many bicycles
     * with the same description.
     */

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
        boolean foundDuplicatePlate = checkDuplicatePlateInParkingLot(plate, vehicleType);

        if (!foundDuplicatePlate) {
            foundDuplicatePlate = checkDuplicatePlateWithDifferentType(plate, vehicleType);
        }

        return foundDuplicatePlate;
    }

    private boolean checkDuplicatePlateInParkingLot(String plate, String vehicleType) {
        if (!vehicleType.equalsIgnoreCase("bicicleta")) {
            for (Spot spot : spots) {
                if (spot != null) {
                    Vehicles vehicle = spot.getVehicle();
                    if (vehicle != null && vehicle.getPlate().equalsIgnoreCase(plate)) {
                        System.out.println("¡Atención! Ya existe un vehículo con esta placa en el parqueo. Por favor, ingrese una placa diferente.");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean checkDuplicatePlateWithDifferentType(String plate, String vehicleType) {
        if (!vehicleType.equalsIgnoreCase("bicicleta")) {
            for (VehicleFlow flow : vehicleFlow) {
                if (flow != null && flow.getVehicle() != null && flow.getVehicle().getPlate().equalsIgnoreCase(plate)
                        && flow.getVehicle().getType().equalsIgnoreCase(vehicleType) && flow.getExitDateTime() != null) {
                    System.out.println("¡Atención! Ya existe un vehículo con la misma placa pero de diferente tipo en el parqueo. No se puede agregar el nuevo vehículo.");
                    return true;
                }
            }
        }
        return false;
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
        String type = vehicle.getType();  // Gets the next empty spot


        int startSpot = nextEmptySpot(spotsNeeded, type); // Gets the next empty spot
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
            for (int i = startSpot; i < startSpot + spotsNeeded; i++) {
                VehicleFlow latestFlow = getVehicleFlowForVehicle(spots[i].getVehicle());
                spots[i].setLatestFlow(latestFlow);
            }
            return true; // Returns true if the vehicle was assigned to a spot
        } else {
            return false; // Returns false if the vehicle was not assigned to a spot

        }
    }
    private int nextEmptySpot(int spotsNeeded, String type) {
        if (type.equals("motocicleta") || type.equals("bicicleta")) {
            for (int i = 25; i < spots.length; i++) { // Iterate over motorcycle/bicycle spots
                if (!spots[i].isOccupied()) {
                    return i; // Return the first empty spot found
                }
            }
        } else {
            int spotsPerRow = 10;
            int numRows = 3;
            // Iterate over each row in the parking lot
            for (int row = 0; row < numRows; row++) {
                int startSpot = row * spotsPerRow; // Start spot for the current row
                int endSpot = startSpot + spotsPerRow; // End spot for the current row
                // Check if it's the last row and adjust the end spot
                if (row == numRows - 1) {
                    endSpot = startSpot + 5; // The last row only has 5 spots
                }
                // Check if the current row has enough consecutive empty spots for the vehicle
                for (int i = startSpot; i <= endSpot - spotsNeeded; i++) {
                    boolean consecutiveSpotsEmpty = true;

                    // Check if spotsNeeded consecutive spots are empty
                    for (int j = i; j < i + spotsNeeded; j++) { // Iterate over the spots needed
                        if (spots[j].isOccupied()) { // If the spot is occupied
                            consecutiveSpotsEmpty = false; // Set consecutiveSpotsEmpty to false
                            break;
                        }
                    }
                    // If spotsNeeded consecutive spots are empty, return the start spot
                    if (consecutiveSpotsEmpty) {
                        return i;
                    }
                }
            }
        }
        return -1; // No empty spots found
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
        boolean foundInParking = false; // Variable to check if the vehicle is found in the parking lot
        for (Vehicles vehicle : matchingVehicles) {
            VehicleFlow flow = getVehicleFlowForVehicle(vehicle);
            if (flow != null && flow.getExitDateTime().equals("El vehículo no ha salido")) { // If the vehicle has not exited
                foundInParking = true;
                System.out.println("Información del vehículo:");
                System.out.println("Hora de entrada: " + flow.getEntryDate());
                System.out.println("Placa o Descripción: " + vehicle.getPlateOrDescription());
                System.out.println("Cantidad de horas al momento: " + String.format("%.1f", flow.calculateHoursPassed()));
                double amountCharged = amountToCharge(flow.calculateHoursPassed(), flow);
                if (amountCharged != -1) {
                    System.out.println("Monto hasta el momento: $" + amountCharged);
                } else {
                    System.out.println("Tipo de vehículo no válido. No se puede calcular el monto.");
                }
                System.out.println();
            }
        }
        if (!foundInParking) {
            System.out.println("Vehículo no encontrado en el estacionamiento o ya ha sido sacado.");
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
    public void seeFlows() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Desea consultar todo el historial o el de un vehiculo en específico (bicicletas no se aceptan)?");
        System.out.println("1. Todo el historial");
        System.out.println("2. Historial de un vehículo en específico");
        int option = scanner.nextInt();
        scanner.nextLine();
        if (option == 1) {
            printAllVehicleFlow(); // Exit vehicle by plate
        } else if (option == 2) {
            printVehicleFlowByPlate(); // Exit vehicle by spot
        } else {
            System.out.println("Opción inválida. Volviendo al menú principal.");
        }

    }
    public void printAllVehicleFlow() {
        System.out.println("Historial de vehículos:\n");
        for (VehicleFlow flow : this.vehicleFlow) { // Iterates through the vehicle flow
            if (flow != null) {
                System.out.println(flow + " " + amountToCharge(flow.calculateHoursPassed(), flow)); // Prints the vehicle flow
            }
        }
        if (this.vehicleFlow[0] == null) { // If there are no vehicle flows
            System.out.println("No hay movimientos en el parqueo.");
        }
    }
    public void printVehicleFlowByPlate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo a buscar: ");
        String plate = scanner.nextLine();
        System.out.println("Historial del vehículo con placa: " + plate);
        VehicleFlow[] plateFlows = new VehicleFlow[this.vehicleFlow.length];
        int count = 0;
        for (VehicleFlow flow : this.vehicleFlow) { // Searches for the vehicle flow with the specified plate
            if (flow != null && flow.getVehicle().getPlate().equalsIgnoreCase(plate)) {
                plateFlows[count++] = flow;
            }
        }
        if (count == 0) { // If no vehicle flow is found
            System.out.println("No se encontraron movimientos para el vehículo con placa: " + plate);
        } else {
            for (int i = 0; i < count; i++) { // Prints the vehicle flow
                System.out.println(plateFlows[i] + " " + amountToCharge(plateFlows[i].calculateHoursPassed(), plateFlows[i]));
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
        System.out.println("Todos los vehículos que estuvieron hoy:\n");
        printAllVehicleFlow(); // Prints all the vehicle flow
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
    public static int amountToCharge(double hours, VehicleFlow vehicleFlow) {
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

