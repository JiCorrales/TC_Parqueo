import java.util.Date;
import java.util.Scanner;

public class VehicleFlow {
    private Vehicles vehicle;

    private Date entryTime;
    private Date exitTime;
    private int amountCharged;

    public void addVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el tipo de vehículo a ingresar (Liviano, Mediano, Largo, Motocicleta, Bicicleta): ");
        String vehicleType = scanner.nextLine();
        System.out.println("Ingrese la placa del vehículo: ");
        String plate = scanner.nextLine();
        System.out.println();
        Vehicles vehicle = new Vehicles(vehicleType, plate);

        //    public void duration() {
        //        long duration = exitTime.getTime() - entryTime.getTime();
        //        System.out.println("El vehículo estuvo " + duration + " milisegundos en el parqueo");
        //
        //    }
    }
}