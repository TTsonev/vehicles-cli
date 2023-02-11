/**
 * @author Trayan Tsonev
 * @id
 */

import java.util.*;
import java.util.stream.*;
import java.util.function.Predicate;
import java.text.DecimalFormat;

public class VehicleManagement {
    private VehicleDAO DAO;

    DecimalFormat df = Vehicle.getDecimalFormat();

    public VehicleManagement(VehicleDAO DAO) {
        this.DAO = DAO;
    }

    private String getData(Vehicle v) {
        boolean isCar = (v instanceof Car);
        boolean isTruck = (v instanceof Truck);
        boolean isBus = (v instanceof Bus);

        String res = "";

        if(isCar) {
            res += "Type:       Car" +'\n';
        }
        else if (isTruck) {
            res += "Type:       Truck" +'\n';
        }
        else if (isBus) {
            res += "Type:       Bus" +'\n';
        }

        res += "Id:         " + v.getId() +'\n';
        res += "Brand:      " + v.getBrand() +'\n';
        res += "Model:      " + v.getModel() +'\n';
        res += "Year:       " + v.getYear() +'\n';

        if(isCar) {
            res += "Inspection: " + ((Car) v).getInspection() +'\n';
        }

        if(isBus) {
            res += "Inspection: " + ((Bus) v).getInspection() +'\n';
        }

        res += "Base price: " + df.format(v.getBasePrice()) +'\n';
        res += "Price:      " + df.format(v.getPrice()) +'\n';

        return res;
    }

    public String allData() {
        var vehicles = DAO.getVehicleList();
        String res = "";
        if (vehicles.isEmpty()) return res;
        for (Vehicle v : vehicles){
            res += dataOfVehicle(v.getId());
        }
        return res;
    }

    public String dataOfVehicle(int id) {
        var vehicles = DAO.getVehicleList();
        Vehicle vehicle = null;

        for (Vehicle i : vehicles) {
            if (i.getId() == id) vehicle = i;
        }
        if (vehicle == null) throw new IllegalArgumentException("Vehicle not found. (id=" + id + ")" );

        return getData(vehicle);
    }

    public void addVehicle(Vehicle vehicle) {
        DAO.saveVehicle(vehicle);
    }

    public void removeVehicle(int id) {
        DAO.deleteVehicle(id);
    }

    public int countVehicles(Predicate<Vehicle> vtype){
        return (int) DAO.getVehicleList().stream().filter(vtype).count();
    }

    public Double meanPrice(){
        var vehicles = DAO.getVehicleList();
        if (vehicles.isEmpty()) return 0.;
        Double sum = vehicles.stream().map(v -> Double.parseDouble(df.format(v.getPrice())) ).reduce(0., Double::sum);
        return (sum/vehicles.size());
    }

    public List<Integer> oldest(){
        Integer maxAge = DAO.getVehicleList().stream().max(Comparator.comparing(Vehicle::getAge)).get().getAge();
        return DAO.getVehicleList().stream().filter(v -> v.getAge() == maxAge).map(v -> v.getId()).collect(Collectors.toList());
    }
}
