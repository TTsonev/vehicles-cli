/**
 * @author Trayan Tsonev
 * @id 12127140
 */

import java.util.*;

public class Car extends Vehicle {
    private int inspection;

    public Car(String brand, String model, int year, double price, int id, int inspection) {
        super(brand, model, year, price, id);
        if (inspection > 2022 || inspection < this.getYear())
            throw new IllegalArgumentException("Inspection year invalid.");
        this.inspection = inspection;
    }

    @Override
    public int getDiscount() {
        int discount = 0;
        discount += 5 * getAge();
        discount += 2 * (2022 - inspection);
        if (discount > 15) discount = 15;
        return discount;
    }

    public int getInspection() {
        return inspection;
    }
}