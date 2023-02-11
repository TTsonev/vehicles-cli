/**
 * @author Trayan Tsonev
 * @id
 */

import java.util.*;
import java.io.File;
import java.text.DecimalFormat;

public class VehicleCLI {
	public static void main(String[] args) {
		try {
			File file = new File(args[0]);
			if (!file.exists()) file.createNewFile();

			VehicleManagement vm = new VehicleManagement(new SerializedVehicleDAO(args[0]));
			if (args.length < 2) throw new IllegalArgumentException("Invalid parameter.");

			switch(args[1]) {

				case "show":
					if (args.length == 2) {
						System.out.println(vm.allData());
					}
					else {
						int id = Integer.parseInt(args[2]);
						System.out.println(vm.dataOfVehicle(id));
					}
					break;

				case "add":
					if (args[2].equals("car")) {
						if (args.length != 9) throw new IllegalArgumentException("Invalid parameter.");
						Car c = new Car(args[4], args[5], Integer.parseInt(args[6]), Double.parseDouble(args[7]), Integer.parseInt(args[3]), Integer.parseInt(args[8]));
						vm.addVehicle(c);
					}
					else if (args[2].equals("truck")) {
						if (args.length != 8) throw new IllegalArgumentException("Invalid parameter.");
						Truck t = new Truck(args[4], args[5], Integer.parseInt(args[6]), Double.parseDouble(args[7]), Integer.parseInt(args[3]));
						vm.addVehicle(t);
					}
					else if (args[2].equals("bus")) {
						if (args.length != 9) throw new IllegalArgumentException("Invalid parameter.");
						Bus b = new Bus(args[4], args[5], Integer.parseInt(args[6]), Double.parseDouble(args[7]), Integer.parseInt(args[3]), Integer.parseInt(args[8]));
						vm.addVehicle(b);
					}
					else throw new IllegalArgumentException("Invalid parameter.");
					break;

				case "del":
					int id = Integer.parseInt(args[2]);
					vm.removeVehicle(id);
					break;

				case "count":
					if (args.length == 2) System.out.println(vm.countVehicles(v -> v instanceof Vehicle));
					else if (args[2].equals("car")) System.out.println(vm.countVehicles(v -> v instanceof Car));
					else if (args[2].equals("truck")) System.out.println(vm.countVehicles(v -> v instanceof Truck));

					else if (args[2].equals("bus")) System.out.println(vm.countVehicles(v -> v instanceof Bus));

					else throw new IllegalArgumentException("Invalid parameter.");
					break;

				case "meanprice":
					final DecimalFormat df = Vehicle.getDecimalFormat();
					System.out.println(df.format(vm.meanPrice()));
					break;

				case "oldest":
					var oldest = vm.oldest();
					for (Integer i : oldest){
						System.out.println("Id: " + i);
					}
					break;

				default:
					throw new IllegalArgumentException("Invalid parameter.");
			}
		}
		catch(NumberFormatException ne) {
			System.out.println("Error: Invalid parameter.");
			//e.printlnStackTrace();
			return;
		}
		catch(IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			//e.printlnStackTrace();
			return;
		}
		catch(Exception rest_e) {
			System.out.println("Error: Invalid parameter.");
			//e.printlnStackTrace();
			return;
		}
	}
}
