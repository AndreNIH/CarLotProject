package DataManager.Pool;

public class BrandPool extends ComponentPool{
    private static BrandPool instance = new BrandPool();
    
    private BrandPool(){
        super(ModelPool.get());
    }
    
    public static BrandPool get(){
        return instance;
    }

}
