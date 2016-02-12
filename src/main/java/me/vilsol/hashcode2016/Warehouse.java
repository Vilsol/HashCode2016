package me.vilsol.hashcode2016;

import java.util.HashMap;

public class Warehouse implements Copyable<Warehouse> {

    private int id;
    private Position position;

    private HashMap<ProductType, Integer> products = new HashMap<>();

    public Warehouse(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void setProductCount(ProductType type, int count){
        products.put(type, count);
    }

    public Integer getProductCount(ProductType type){
        return products.getOrDefault(type, 0);
    }

    @Override
    public Warehouse copy() {
        Warehouse warehouse = new Warehouse(id, position);
        warehouse.products.putAll(products);
        return warehouse;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "products=" + products +
                ", position=" + position +
                '}';
    }

}
