package DataManager.Pool;

public class VehiclePool extends ComponentPool{
    private static VehiclePool instance = new VehiclePool();
    
    private VehiclePool(){
        super(null);
    }
    
    public static VehiclePool get(){
        return instance;
    }
}
