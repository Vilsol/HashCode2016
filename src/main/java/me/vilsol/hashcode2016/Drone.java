package me.vilsol.hashcode2016;

import java.util.*;

public class Drone {

    private int id;
    private Position position;

    private List<FlyingItem> products = new ArrayList<>();

    public Drone(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<FlyingItem> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", position=" + position +
                ", products=" + products +
                '}';
    }

    public int getCarryingWeight() {
        return products.stream().mapToInt(e -> e.getCount() * e.getProduct().getWeight()).sum();
    }
}
