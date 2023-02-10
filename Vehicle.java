/**
 * @author Trayan Tsonev
 * @id 12127140
 */

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public abstract class Vehicle implements Serializable {
    private String brand;
    private String model;
    private int year;
    private Double basePrice;
    private int id;

    public Vehicle(final String brand, final String model, final int year, final double price, final int id){
        if (brand == null || brand.isEmpty())
            throw new IllegalArgumentException("Invalid parameter.");
        if (model == null || model.isEmpty())
            throw new IllegalArgumentException("Invalid parameter.");
        if (year > 2022)
            throw new IllegalArgumentException("Year built invalid.");
        if (price.compareTo(0.) <= 0)
            throw new IllegalArgumentException("Base price invalid.");

        this.brand = brand;
        this.model = model;
        this.year = year;
        this.basePrice = price;
        this.id = id;
    }

    public int getAge(){
        return 2022 - year;
    }

    public abstract int getDiscount();

    public Double getPrice(){
        return basePrice - (basePrice * Double.valueOf(getDiscount()) / 100);
    }

    public static final DecimalFormat getDecimalFormat() {
        return new DecimalFormat("0.00");
    }

    public int getId() {return id;}
    public String getBrand() {return brand;}
    public String getModel() {return model;}
    public int getYear() {return year;}
    public Double getBasePrice() {return basePrice;}
}