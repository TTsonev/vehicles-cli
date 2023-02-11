/**
 * @author Trayan Tsonev
 * @id
 */

import java.io.*;
import java.util.*;

public class SerializedVehicleDAO implements VehicleDAO {
    private String filename;
    public SerializedVehicleDAO(String filename) {
        if (filename == null || filename.isEmpty()) {
            System.err.println("Error: Invalid parameter.");
            System.exit(1);
        }
        this.filename = filename;
    }

    @SuppressWarnings("unchecked")
    public List<Vehicle> getVehicleList() {
        File file = new File(filename);
        if (file.length() == 0) return new ArrayList<Vehicle>();

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        try {
            ObjectInputStream reader;
            reader = new ObjectInputStream(new FileInputStream(filename));
            vehicles = (List<Vehicle>) reader.readObject(); 
            reader.close();
        }
        catch (Exception e) {
            System.err.println("Error during deserialization: " + e.getMessage());
            //e.printStackTrace();
            System.exit(1);
        }
        return vehicles;
    }

    public Vehicle getVehicle(int id) {
        List<Vehicle> vehicles = getVehicleList();
        for (Vehicle v: vehicles){
            if (v.getId() == id) return v;
        }
        return null;
    }

    public void saveVehicle(Vehicle vehicle) {
        List<Vehicle> vehicles = getVehicleList();

        for (Vehicle v: vehicles) {
            if (v.getId() == vehicle.getId()){
                throw new IllegalArgumentException("Vehicle already exists. (id=" + v.getId() + ")");
            }
        }

        vehicles.add(vehicle);

        File file = new File(filename);
        if (file.exists()) file.delete();
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename, true));
            writer.writeObject(vehicles);
            writer.close();
        }
        catch (Exception e) {
            System.err.println("Error during serialization: " + e.getMessage());
            //e.printStackTrace();
            System.exit(1);
        }
    }

    public void deleteVehicle(int id) {
        List<Vehicle> vehicles = getVehicleList();

        if (vehicles.removeIf(v -> v.getId() == id)) {
            File file = new File(filename);
            if (file.exists()) file.delete();
            try {
                ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename, true));
                writer.writeObject(vehicles);
                writer.close();
            }
            catch (Exception e) {
                System.err.println("Error during serialization: " + e.getMessage());
                //e.printStackTrace();
                System.exit(1);
            }
        }
        else throw new IllegalArgumentException("Vehicle not found. (id=" + id + ")");
    }
}
