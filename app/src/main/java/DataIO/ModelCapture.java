package DataIO;

import java.util.Scanner;
import java.util.InputMismatchException;

import DataManager.Pool.BrandPool;
import DataManager.Pool.ModelPool;
import DataIO.BrandCapture;
import DataManager.*;
import Graphics.*;

public class ModelCapture implements ICapture {
    public Scanner sc = new Scanner(System.in);
    private static String errorMessage = new String("Error: No se ha registrado ningun modelo de autos en el sistema.");
    
    private boolean requestYesNoPrompt(String title) throws InputMismatchException{
        //No utilizen la instancia de Scanner "sc". Hay conflicto con este metodo
        //En su lugar utilizen "scs"
        var scs = new Scanner(System.in);
        for(;;){
            System.out.print(title);
            String response = scs.nextLine();
            if(response.toUpperCase().startsWith("S")) return true;
            else if (response.toUpperCase().startsWith("N")) return false;
        }
    }

    private Model createNewInstance() {
        if(BrandPool.get().countRegisterdComponents() == 0) return null;
        System.out.println("Seleccione la marca a la que asociara este modelo\n");

        Brand brand = (Brand) new BrandCapture().onBrowse(true);
        if (brand == null) return null;
        try{
            Model newModel = new Model(brand);
            System.out.print("Nombre del modelo a registrar: ");
            newModel.setModelName(sc.next());
            System.out.print("Año del carro a registrar: ");
            newModel.setModelYear(sc.nextInt());
            newModel.setHasSunroof(requestYesNoPrompt("Tiene quemacocos(S[i]/N[o]): "));
            System.out.print("Numero de puertas del carro: ");
            newModel.setDoorCount(sc.nextInt());
            System.out.print("Numero de asientos del carro: ");
            newModel.setSeatCount(sc.nextInt());
            System.out.print("Capacidad de gasolina del carro: ");
            newModel.setFuelCapacity(sc.nextDouble());
            return newModel;
        }catch(ComponentNotBoundToPool ex){
            System.out.println("Exception: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void onCreate() {

        Model modeloV = createNewInstance();
        if (modeloV == null) {
            System.out.println(errorMessage +  "Para registrar un modelo debe de existir una marca");
            return;
        }

        ModelPool.get().registerComponent(modeloV);

    }

    @Override
    public void onUpdate() {
        System.out.println("¿Que marca desea actualizar");
        Model model = (Model) onBrowse(true);

        if (model == null) {
            System.out.println(errorMessage);
            return;
        }

        Model newModel = createNewInstance();
        ModelPool.get().updateComponent(model, newModel);

    }

    @Override
    public void onDelete() {
        System.out.println("¿Que modelo desea eliminar?");
        Model model = (Model)onBrowse(true);
        if(model == null){
            System.out.println(errorMessage);
            return;
        }
        ModelPool.get().unregisterComponent(model);
    }

    @Override
    public Component onBrowse(boolean shouldReturnComponent) {
        TableBuilder tableBuilder = new TableBuilder(
                new TableCell("ID", 5),
                new TableCell("Nombre del Modelo", 20),
                new TableCell("Año del Modelo"),
                new TableCell("Quemacocos"),
                new TableCell("Numero de Puertas"),
                new TableCell("Numero de Asientos"),
                new TableCell("Capacidad del Tanque(L)"),
                new TableCell("Marca", 10));

        ModelPool pool = ModelPool.get();
        for (int i = 0; i < pool.countRegisterdComponents(); i++) {
            var model = (Model) ModelPool.get().getComponentAt(i);
            tableBuilder.bindColumnValue(0, i);
            tableBuilder.bindColumnValue(1, model.getModelName());
            tableBuilder.bindColumnValue(2, model.getModelYear());
            tableBuilder.bindColumnValue(3, model.getHasSunroof() ? "Si" : "No");
            tableBuilder.bindColumnValue(4, model.getDoorCount());
            tableBuilder.bindColumnValue(5, model.getSeatCount());
            tableBuilder.bindColumnValue(6, model.getFuelCapacity());
            tableBuilder.bindColumnValue(7, model.getBrand().getBrandName());
            tableBuilder.commitRow();
        }
        System.out.println(tableBuilder.getTable());

        if (shouldReturnComponent && pool.countRegisterdComponents() > 0) {
            var cScanner = new ConstrainedScanner("Seleccione un modelo por medio de su ID: ", 0,
                    pool.countRegisterdComponents() - 1);
            return pool.getComponentAt(cScanner.getValue());
        }

        return null;
    }

}