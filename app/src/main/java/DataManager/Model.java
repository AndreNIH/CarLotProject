package DataManager;

import DataManager.Pool.*;

public class Model extends Component {
    private String modelName = new String();
    private int modelYear = 0;
    private Boolean hasSunroof = false;
    private Integer doorCount = 0;
    private Integer seatCount = 0;
    private Double fuelCapaity = 0.0;

    public Model(Brand brand) throws ComponentNotBoundToPool {
        if (brand == null)
            return;
        if (!BrandPool.get().componentIsRegisteredAtPool(brand)) {
            throw new ComponentNotBoundToPool(
                    "The 'brand' object passed to bindToBrand does not exist inside BrandPool");
        }
        brand.addChild(this);
    }

    public Brand getBrand() {
        return (Brand) getParent();
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getModelYear() {
        return this.modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public Boolean getHasSunroof() {
        return this.hasSunroof;
    }

    public void setHasSunroof(Boolean newSunroof) {
        this.hasSunroof = newSunroof;
    }

    public Integer getDoorCount() {
        return this.doorCount;
    }

    public void setDoorCount(Integer newDoorCount) {
        this.doorCount = newDoorCount;
    }

    public Integer getSeatCount() {
        return this.seatCount;
    }

    public void setSeatCount(Integer newSeatCount) {
        this.seatCount = newSeatCount;
    }

    public Double getFuelCapacity() {
        return this.fuelCapaity;
    }

    public void setFuelCapacity(Double newFuelCapacity) {
        this.fuelCapaity = newFuelCapacity;
    }
}