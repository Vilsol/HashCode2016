package me.vilsol.hashcode2016;

public class Item {

    private ProductType product;
    private int count;

    public Item(ProductType product, int count) {
        this.product = product;
        this.count = count;
    }

    public ProductType getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Item{" +
                "product=" + product +
                ", count=" + count +
                '}';
    }
}
