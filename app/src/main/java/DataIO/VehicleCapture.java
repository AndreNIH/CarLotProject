package DataIO;

import java.util.Scanner;

import DataManager.Pool.ModelPool;
import DataManager.Pool.VehiclePool;
import Graphics.TableBuilder;
import Graphics.TableCell;
import DataManager.Component;
import DataManager.ComponentNotBoundToPool;
import DataManager.Model;
import DataManager.Vehicle;
import DataIO.ConstrainedScanner;

public class VehicleCapture implements ICapture {
    public static Scanner sc = new Scanner(System.in);
    private static String errorMessage = new String("Error: No se ha registrado ningun vehiculo en el sistema");

    private Vehicle createNewInstance() {
        Model model = (Model) new ModelCapture().onBrowse(true);
        if (model == null)
            return null;

        try {
            Vehicle newVehicle = new Vehicle(model);
            System.out.print("VIN del carro: ");
            newVehicle.setVIN(sc.next());
            System.out.print("Placas del carro: ");
            newVehicle.setlicensePlate(sc.next());
            System.out.print("Color del carro: ");
            newVehicle.setColor(sc.next());
            System.out.print("Kilometraje del carro: ");
            newVehicle.setMileage(sc.nextDouble());

            return newVehicle;
        } catch (ComponentNotBoundToPool ex) {
            System.out.println("Exception: " + ex.getMessage());
            return null;
        }

    }

    @Override
    public void onCreate() {

        Vehicle newVehicle = createNewInstance();
        VehiclePool.get().registerComponent(newVehicle);

    }

    @Override
    public void onUpdate() {

        System.out.println("¿Que marca desea actualizar");
        Vehicle vehicle = (Vehicle) onBrowse(true);

        if (vehicle == null) {
            System.out.println(errorMessage);
            return;
        }

        Vehicle newVehicle = createNewInstance();
        VehiclePool.get().updateComponent(vehicle, newVehicle);

    }

    @Override
    public void onDelete() {
        System.out.println("¿Que vehiculo desea eliminar? ");
        Vehicle vehicle = (Vehicle) onBrowse(true);
        if (vehicle == null) {
            System.out.println(errorMessage);
            return;
        }
        VehiclePool.get().unregisterComponent(vehicle);
    }

    @Override
    public Component onBrowse(boolean shouldReturnComponent) {
        TableBuilder tableBuilder = new TableBuilder(
                new TableCell("VIN", 20),
                new TableCell("Placas del carro"),
                new TableCell("Color del carro"),
                new TableCell("Kilometraje del carro"));

        VehiclePool pool = VehiclePool.get();
        for (int i = 0; i < pool.countRegisterdComponents(); i++) {
            var vehicle = (Vehicle) VehiclePool.get().getComponentAt(i);
            tableBuilder.bindColumnValue(0, i);
            tableBuilder.bindColumnValue(1, vehicle.getVIN());
            tableBuilder.bindColumnValue(2, vehicle.getLicensePlate());
            tableBuilder.bindColumnValue(3, vehicle.getMileage());
            tableBuilder.commitRow();
        }
        System.out.println(tableBuilder.getTable());

        if (shouldReturnComponent && pool.countRegisterdComponents() > 0) {
            var cScanner = new ConstrainedScanner("Seleccione un vehiculo por medio de su ID: ", 0,
                    pool.countRegisterdComponents() - 1);
            return pool.getComponentAt(cScanner.getValue());
        }

        return null;
    }
}
