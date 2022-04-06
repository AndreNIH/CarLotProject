package DataManager;

import DataManager.Pool.*;

public class Vehicle extends Component {
    private String VIN = new String();
    private String licensePlate = new String();
    private String color = new String();
    private Double mileage = 0.0;

    public Vehicle(Model model) throws ComponentNotBoundToPool {
        if (model == null)
            ;
        if (!ModelPool.get().componentIsRegisteredAtPool(model)) {
            throw new ComponentNotBoundToPool(
                    "The 'model' object passed to bindToModel does not exist inside ModelPool");
        }
        model.addChild(this);
    }

    public Model getModel() {
        return (Model) getParent();
    };

    public String getVIN() {
        return this.VIN;
    }

    public void setVIN(String numVIN) {
        this.VIN = numVIN;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setlicensePlate(String newLicensePlate) {
        this.licensePlate = newLicensePlate;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String newColor) {
        this.color = newColor;
    }

    public Double getMileage() {
        return this.mileage;
    }

    public void setMileage(Double newMillage) {
        this.mileage = newMillage;
    }
}