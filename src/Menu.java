import java.util.Scanner;

public class Menu {
    public ParkingLot parkingLot;

    public Menu() { parkingLot = new ParkingLot(); }
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
        int option; // Variable to store the selected option
        do { // Loop to keep the menu running
            printMenu();
            // Check if the input is an integer
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        // Call the addVehicle method
                        parkingLot.addVehicle();
                        break;
                    case 2:
                        // Call the searchForVehicle methodparkingLot.searchForVehicle();
                        parkingLot.searchForVehicle();
                        break;
                    case 3:
                        parkingLot.exitVehicle();
                        break;
                    case 4:
                        System.out.println(parkingLot.toString());
                        break;
                    case 5:
                        System.out.println("Consultar historial");
                        parkingLot.printAllVehicleFlow();
                        break;
                    case 6:
                        System.out.println("Cierre del día");
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;
                }
            } else {
                // Cleans the buffer
                scanner.next();
                System.out.println("Por favor, ingrese un número válido.");
                option = 0; // Set the option to 0 to keep the loop running
            }
        } while (option != 7);
        scanner.close();
    }

}
