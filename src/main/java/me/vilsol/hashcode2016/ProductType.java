package me.vilsol.hashcode2016;

public class ProductType implements Copyable<ProductType> {

    private int id;
    private int weight;

    public ProductType(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public ProductType copy() {
        return new ProductType(id, weight);
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", weight=" + weight +
                '}';
    }
}
