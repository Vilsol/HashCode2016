package me.vilsol.hashcode2016.commands;

import me.vilsol.hashcode2016.Drone;
import me.vilsol.hashcode2016.FlyingItem;
import me.vilsol.hashcode2016.Order;
import me.vilsol.hashcode2016.ProductType;

public class CommandDeliver extends Command {

    private Order order;
    private ProductType product;
    private int count;

    public CommandDeliver(Drone drone, FlyingItem item) {
        super(drone, CommandType.DELIVER);
        this.order = item.getOrder();
        this.product = item.getProduct();
        this.count = item.getCount();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        return getDrone().getId() + " " + getType().getS() + " " + order.getId() + " " + product.getId() + " " + count;
    }

}
