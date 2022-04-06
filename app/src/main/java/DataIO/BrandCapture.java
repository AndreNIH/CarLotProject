package DataIO;

import java.util.Scanner;
import DataManager.Pool.BrandPool;
import DataManager.Brand;
import DataManager.Component;
import Graphics.*;
import DataIO.ConstrainedScanner;

public class BrandCapture implements ICapture {
    public Scanner sc = new Scanner(System.in);
    private static String errorMessage = new String("Error: No se ha registrado ninguna marca en el sistema");

    private Brand createNewInstance(){
        Brand newBrand = new Brand();
        System.out.print("Nombre de la marca a registrar: ");
        newBrand.setBrandName(sc.nextLine());
        return newBrand;
    }


    @Override
    public void onCreate() {
        Brand newBrand = createNewInstance();
        BrandPool.get().registerComponent(newBrand);
    }

    @Override
    public void onUpdate() {
        System.out.println("¿Que marca desea actualizar");
        Brand brand = (Brand)onBrowse(true);
        
        if(brand == null){
            System.out.println("\nNo se ha registrado ninguna marca en el sistema");
            return;
        }

        Brand newBrand = createNewInstance();
        BrandPool.get().updateComponent(brand, newBrand);
    }

    @Override
    public void onDelete() {
        System.out.println("¿Que marca desea eliminar? ");
        Brand brand = (Brand)onBrowse(true);
        if(brand == null){
            System.out.println("\nNo se ha registrado ninguna marca en el sistema");
            return;
        }

        BrandPool.get().unregisterComponent(brand);
    }

    @Override
    public Component onBrowse(boolean shouldReturnComponent) {
        TableBuilder tableBuilder = new TableBuilder(
            new TableCell("ID", 5),
            new TableCell("Nombre de la Marca", 20)
        );

        
        BrandPool pool = BrandPool.get();
        for(int i=0; i < pool.countRegisterdComponents(); i++){
            var item = (Brand)pool.getComponentAt(i);
            tableBuilder.bindColumnValue(0, i);
            tableBuilder.bindColumnValue(1, item.getBrandName());
            tableBuilder.commitRow();
        }
        System.out.println(tableBuilder.getTable());

        if(shouldReturnComponent && pool.countRegisterdComponents() > 0){
            var cScanner = new ConstrainedScanner("Seleccione una marca por medio de su ID: ", 0, pool.countRegisterdComponents()-1);
            return pool.getComponentAt(cScanner.getValue());
        }
        
        return null;
    }
}
