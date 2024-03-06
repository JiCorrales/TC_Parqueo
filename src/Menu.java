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
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    // parkingLot.getVehicleFlow().addVehicle(); // This line is commented out because the method addVehicle() is not implemented yet,
                    // and it would cause a compilation error, also the method gotta be static to be called from a static context, but it's not
                    // what we want to do here, so we will fix this later, because I think we don't need it on a static context
                    parkingLot.addVehicle();
                    break;
                case 2:
                    parkingLot.searchForVehicle();
                    break;
                case 3:
                    System.out.println("Escriba el tipo de vehículo a sacar (Liviano, Mediano, Largo, Motocicleta, Bicicleta): ");
                    break;
                case 4:
                    // parkingLot.printParkingLotStatus(); // This line is commented out because the method printParkingLotStatus() is not implemented yet,
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

        } while (option != 7);
        scanner.close();
    }
}
