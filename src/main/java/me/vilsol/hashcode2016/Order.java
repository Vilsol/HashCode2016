package me.vilsol.hashcode2016;


import java.util.HashMap;

public class Order implements Copyable<Order> {

    private int id;
    private Position position;
    private int completedTurn = -1;

    private HashMap<ProductType, Item> products = new HashMap<>();

    public Order(int id, Position position) {
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
        products.put(type, new Item(type, count));
    }

    public Integer getProductCount(ProductType type){
        return products.getOrDefault(type, new Item(type, 0)).getCount();
    }

    public boolean isCompleted(){
        return completedTurn >= 0;
    }

    public void markCompleted(int currentTurn){
        this.completedTurn = currentTurn;
    }

    public int getScore(int totalTurns){
        return (int) Math.ceil(((totalTurns - completedTurn) / (double) totalTurns) * 100);
    }

    public boolean allDelivered(){
        return products.size() == 0;
    }

    public void removeProduct(ProductType type){
        products.remove(type);
    }

    public int getTotalWeight(){
        return products.entrySet().stream().mapToInt(e -> e.getValue().getCount() * e.getKey().getWeight()).sum();
    }

    @Override
    public Order copy() {
        Order order = new Order(id, position);
        order.products.putAll(products);
        return order;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", position=" + position +
                ", completedTurn=" + completedTurn +
                ", products=" + products +
                '}';
    }

    public HashMap<ProductType, Item> getProducts() {
        return products;
    }
}
