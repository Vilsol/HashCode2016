package me.vilsol.hashcode2016;

public class FlyingItem {

    private ProductType product;
    private int count;
    private Order order;

    public FlyingItem(ProductType product, int count, Order order) {
        this.product = product;
        this.count = count;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "FlyingItem{" +
                "product=" + product +
                ", count=" + count +
                ", order=" + order +
                '}';
    }
}