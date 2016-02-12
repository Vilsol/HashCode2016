package me.vilsol.hashcode2016.commands;

import me.vilsol.hashcode2016.Drone;
import me.vilsol.hashcode2016.ProductType;
import me.vilsol.hashcode2016.Warehouse;

public class CommandLoadUnload extends Command {

    private Warehouse warehouse;
    private ProductType product;
    private int count;

    public CommandLoadUnload(Drone drone, CommandType type, Warehouse warehouse, ProductType product, int count) {
        super(drone, type);
        this.warehouse = warehouse;
        this.product = product;
        this.count = count;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public ProductType getProduct() {
        return product;
    }

    public void setProduct(ProductType product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return getDrone().getId() + " " + getType().getS() + " " + warehouse.getId() + " " + product.getId() + " " + count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandLoadUnload)) return false;

        CommandLoadUnload that = (CommandLoadUnload) o;

        return warehouse.equals(that.warehouse) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        int result = warehouse.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }

}
