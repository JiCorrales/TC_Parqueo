import java.util.Scanner;

public class Menu {
    public ParkingLot parkingLot; // Create a ParkingLot object

    public Menu() { parkingLot = new ParkingLot(); } // Constructor

    // Method to print the menu options
    private void printMenu() {
        System.out.println("---- Menú ----");
        System.out.println("1. Ingresar vehículo");
        System.out.println("2. Consultar vehículo");
        System.out.println("3. Sacar vehículo");
        System.out.println("4. Consultar parqueo");
        System.out.println("5. Consultar historial");
        System.out.println("6. Cierre del día");
        System.out.println("7. Salir");
        System.out.print("Ingrese su opción: ");
    }
    public void run() {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        int option; // Variable to store the user's option
        do { // Start a do-while loop
            printMenu(); // Print the menu options1
            if (scanner.hasNextInt()) { // Check if the user's input is an integer
                option = scanner.nextInt(); // Read the user's input
                switch (option) { // Check the user's input
                    case 1 -> parkingLot.addVehicle(); // Add a vehicle
                    case 2 -> parkingLot.searchForVehicle(); // Search for a vehicle
                    case 3 -> parkingLot.exitVehicle(); // Remove a vehicle
                    case 4 -> System.out.println(parkingLot.toString()); // Print the parking lot status
                    case 5 -> parkingLot.seeFlows(); // Let the user see the vehicle flows, by vehicle and all the flows
                    case 6 -> parkingLot.closeParking(); // Close the parking lot
                    case 7 -> parkingLot.exitSystem(); // Exit the system
                    default -> System.out.println("Opción inválida"); // Print an error message
                }
            } else {
                scanner.next(); // Clear the scanner buffer
                System.out.println("Por favor, ingrese un número válido."); // Print an error message
                option = 0; // Set the option to 0
            }
        } while (option != 7); // Repeat the loop until the user selects the option to exit the system
        scanner.close(); // Close the scanner
    }
}
