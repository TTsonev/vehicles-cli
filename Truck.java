/**
 * @author Trayan Tsonev
 * @id 12127140
 */

import java.util.*;

public class Truck extends Vehicle {
    public Truck(String brand, String model, int year, double price, int id) {
        super(brand, model, year, price, id);
    }

    @Override
    public int getDiscount() {
        int discount = 0;
        discount += 5 * getAge();
        if (discount > 20) discount = 20;
        return discount;
    }
}