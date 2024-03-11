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
        String vehicleType = askForVehicleType(); // Asks for the vehicle type
        if (vehicleType != null) {   // If the vehicle type is valid
            String plateOrDescription = askForPlateOrDescription(vehicleType);// Asks for the plate or description
            if (plateOrDescription != null) { // If the plate or description is valid
                Vehicles newVehicle = createVehicle(vehicleType, plateOrDescription);// Creates the vehicle
                if (askAndAssignEntryDate(newVehicle)) {
                    System.out.println("Vehículo agregado al parqueo."); // Prints a message if the vehicle was assigned to a spot
                } else {
                    System.out.println("No hay espacio disponible para este tipo de vehículo."); // Prints a message if the vehicle was not assigned to a spot
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
            else if (!Vehicles.validType(vehicleType)) System.out.println("Tipo de vehículo inválido. Por favor, ingrese un tipo válido.");
        } while (vehicleType.isEmpty() || !Vehicles.validType(vehicleType));

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
                if (!Vehicles.validPlate(plateOrDescription)) {
                    System.out.println("Placa inválida. Por favor, ingrese una placa válida.");
                    plateOrDescription = null; // Reiniciamos la variable para evitar problemas
                } else { //It checks if the plate is duplicated
                    if (checkDuplicatePlate(plateOrDescription)) {
                        System.out.println("¡Atención! Ya existe un vehículo con esta placa en el parqueo. Por favor, ingrese una placa diferente.");
                        plateOrDescription = null; // Reiniciamos la variable para evitar problemas
                    }
                }
            }
        } while (plateOrDescription == null);

        return plateOrDescription; // Si la placa o la descripción son válidas, se retorna
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
    public boolean checkDuplicatePlate(String plate) {
        // Method to check if the plate is duplicated
        for (Spot spot : spots) {
            Vehicles vehicle = spot.getVehicle();
            // If the vehicle has the same plate, it returns true
            if (vehicle != null && vehicle.getPlate() != null && vehicle.getPlate().equalsIgnoreCase(plate)) {
                return true;
            }
        }
        return false; // Returns false if the plate is not duplicated
    }

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

                    // Calcular el monto cobrado hasta el momento utilizando amountToCharge
                    double amountCharged = amountToCharge(flow.calculateHoursPassed(), flow);
                    if (amountCharged != -1) {
                        System.out.println("Monto hasta el momento: $" + amountCharged);
                    } else {
                        System.out.println("Tipo de vehículo no válido. No se puede calcular el monto.");
                    }
                } else {
                    System.out.println("No se pudo obtener el flujo de vehículos para el vehículo.");
                }
            }
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    // Método para obtener el flujo de vehículos correspondiente a un vehículo
    private VehicleFlow getVehicleFlowForVehicle(Vehicles vehicle) {
        for (VehicleFlow flow : vehicleFlow) {
            if (flow != null && flow.getVehicle().equals(vehicle)) {
                return flow;
            }
        }
        return null;
    }
    private Vehicles[] searchVehiclesByPlateOrDescription(String plateOrDescription) {
        // Creamos un array para almacenar los vehículos coincidentes
        Vehicles[] matchingVehicles = new Vehicles[vehicleFlow.length];
        int count = 0;

        // Itera a través del flujo de vehículos y busca vehículos con placa o descripción coincidentes
        for (VehicleFlow flow : this.vehicleFlow) {
            if (flow != null && flow.getVehicle() != null) {
                Vehicles vehicle = flow.getVehicle();
                if (vehicle.getPlateOrDescription().equalsIgnoreCase(plateOrDescription)) {
                    matchingVehicles[count++] = vehicle;
                } else if (vehicle.getType().equalsIgnoreCase("bicicleta") && vehicle.getDescription().contains(plateOrDescription)) {
                    matchingVehicles[count++] = vehicle;
                }
            }
        }
        // Creamos un nuevo array con el tamaño exacto para almacenar solo los vehículos coincidentes
        Vehicles[] result = new Vehicles[count];
        System.arraycopy(matchingVehicles, 0, result, 0, count);
        return result;
    }


    // exitVehicle method needs work
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
        Vehicles vehicle = searchVehicleByPlate(plate);
        if (vehicle != null) {
            removeVehicle(vehicle);
            System.out.println("Vehículo sacado del parqueo.");
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }
    public void exitVehicleBySpot() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número de parqueo del vehículo a sacar: ");
        int spotNumber = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea pendiente después de nextInt()
        if (spotNumber < 1 || spotNumber > 35) {
            System.out.println("Número de parqueo inválido.");
            return;
        }
        Spot spot = spots[spotNumber - 1];
        if (spot.isOccupied()) {
            Vehicles vehicle = spot.getVehicle();
            removeVehicle(vehicle);

            System.out.println("Vehículo sacado del parqueo.");
        } else {
            System.out.println("El campo está vacío.");
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

    private boolean askAndAssignEntryDate(Vehicles vehicle) {
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
    public static boolean isValidDateTime(String dateTime) {
        try { // Tries to parse the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Format for the date and time
            LocalDateTime.parse(dateTime, formatter); // Parses the date and time
            return true;
        } catch (DateTimeParseException e) { // Catches the exception if the date and time are not valid
            System.out.println("Fecha y hora inválidas. Por favor, ingrese la fecha y hora en el formato correcto.");
            return false;
        }
    }
    private Vehicles createVehicle(String vehicleType, String plateOrDescription) {
        return new Vehicles(vehicleType, plateOrDescription);
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
    public void removeVehicle(Vehicles vehicle) {
        for (VehicleFlow flow : vehicleFlow) {
            if (flow != null && flow.getVehicle().equals(vehicle)) {
                flow.setExitDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                System.out.println("Vehículo: " + vehicle + ", Hora Entrada: " + flow.getEntryDate() + ", Hora Salida: " + flow.getExitDateTime() + ", Horas transcurridas: " + String.format("%.1f", flow.calculateHoursPassed()) + ", Monto a cobrar: $" + amountToCharge(flow.calculateHoursPassed(), flow));
                break;
            }
        }
        for (Spot spot : spots) {
            if (spot.getVehicle() != null && spot.getVehicle().equals(vehicle)) {
                spot.setOccupied(false);
                spot.setVehicle(null);
            }
        }
    }
    public int amountToCharge(double hours, VehicleFlow vehicleFlow) {
        Vehicles vehicle = vehicleFlow.getVehicle(); // Obtener el vehículo asociado a este flujo
        int hourlyRate = vehicle.getAmountCharged(); // Obtener la tarifa por hora del vehículo
        System.out.println("Tarifa por hora: " + hourlyRate);
        if (hourlyRate == -1) {
            // Tipo de vehículo no válido
            return -1;
        }

        // Calcular las horas completas y la fracción de horas
        int fullHours = (int) Math.floor(hours);
        double fractionOfHour = hours - fullHours;
        System.out.println("Horas completas: " + fullHours);
        System.out.println("Fracción de hora: " + fractionOfHour);
        // Calcular el monto a cobrar
        int totalAmount = 0;

        // Si la fracción de horas es menor o igual a 0.5, se cobrará la mitad de la tarifa por hora
        if (fractionOfHour <= 0.5) {
            totalAmount = (int) Math.ceil(hours) * (hourlyRate / 2);
            System.out.println("Monto a cobrar: " + totalAmount);
        } else {
            // Si la fracción de horas es mayor que 0.5, se redondea al próximo número entero y se cobra la tarifa completa
            totalAmount = (int) Math.ceil(hours) * hourlyRate;

        }

        return totalAmount;
    }
    public void printAllVehicleFlow() {
        for (VehicleFlow flow : this.vehicleFlow) {
            if (flow != null) {
                System.out.println(flow);
            }
        }
    }

}

