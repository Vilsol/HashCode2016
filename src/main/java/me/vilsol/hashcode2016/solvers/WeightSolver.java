package me.vilsol.hashcode2016.solvers;

import me.vilsol.hashcode2016.*;
import me.vilsol.hashcode2016.commands.*;

import java.util.*;

public class WeightSolver implements Solver {

    // Sort orders by total weight
    // Load all orders that can be fit in the drone
    // Deliver using TSP and start over

    private int turn = 0;
    private int score = 0;

    @Override
    public Result solve(int rows, int columns, int droneCount, int maxTurns, int maxPayload, List<ProductType> productTypes, List<Warehouse> warehouses, List<Order> orders) {
        List<Command> commands = new ArrayList<>();
        List<Drone> drones = new ArrayList<>();

        Position firstWarehousePosition = warehouses.get(0).getPosition();
        for (int i = 0; i < droneCount; i++) {
            drones.add(new Drone(i, firstWarehousePosition.copy()));
        }

        while(turn < maxTurns && orders.size() > 0){
            Collections.sort(orders, (o1, o2) -> o1.getTotalWeight() - o2.getTotalWeight());

            Optional<Order> oOrder = orders.stream().filter(o -> o.getProducts().size() > 1).findFirst();
            if(!oOrder.isPresent()){
                break;
            }

            Order order = oOrder.get();

            List<Drone> selectedDrones = new ArrayList<>();

            HashMap<ProductType, Item> products = order.getProducts();

            HashMap<Drone, HashMap<ProductType, Integer>> loadProducts = new HashMap<>();

            LinkedHashMap<Command, Integer> tempCommands = new LinkedHashMap<>();

            for (Iterator<Item> iterator = products.values().iterator(); iterator.hasNext(); ) {
                Item i = iterator.next();

                // Find the closest warehouse with this product
                Warehouse warehouse = warehouses.stream().filter(w -> w.getProductCount(i.getProduct()) > 0).sorted((o1, o2) ->  (int)  (Math.ceil(Utils.euclidianDistance(order.getPosition(), o1.getPosition())) - Math.ceil(Utils.euclidianDistance(order.getPosition(), o2.getPosition())))).findFirst().get();

                // Sort drones by distance to warehouse
                Collections.sort(drones, (o1, o2) -> (int) (Math.ceil(Utils.euclidianDistance(warehouse.getPosition(), o1.getPosition())) - Math.ceil(Utils.euclidianDistance(warehouse.getPosition(), o2.getPosition()))));

                // Find if any selected drones can carry this item
                Optional<Drone> oDrone = selectedDrones.stream().filter(d -> d.getCarryingWeight() + i.getProduct().getWeight() < maxPayload).findFirst();

                // If not, find a drone that is available
                if (!oDrone.isPresent()) {
                    oDrone = drones.stream().filter(d -> d.getCarryingWeight() + i.getProduct().getWeight() < maxPayload).findFirst();

                    // If there are no drones available, deliver all orders, find a new one
                    if (!oDrone.isPresent()) {

                        drones.forEach(drone -> {
                            PathSolver solver = new PathSolver(drone);
                            LinkedList<FlyingItem> path = solver.solvePath();

                            path.forEach(p -> {
                                CommandDeliver command = new CommandDeliver(drone, p);
                                tempCommands.put(command, 1);

                                turn += Math.ceil(Utils.euclidianDistance(drone.getPosition(), p.getOrder().getPosition())) + 1;

                                drone.setPosition(p.getOrder().getPosition().copy());
                                drone.getProducts().remove(p);

                                if(p.getOrder().allDelivered() && !p.getOrder().isCompleted()){
                                    p.getOrder().markCompleted(turn);
                                    score += p.getOrder().getScore(maxTurns);
                                }
                            });
                        });

                        oDrone = drones.stream().filter(d -> d.getCarryingWeight() + i.getProduct().getWeight() < maxPayload).findFirst();
                    }

                    selectedDrones.add(oDrone.get());
                }


                Drone drone = oDrone.get();

                loadProducts.putIfAbsent(drone, new HashMap<>());
                HashMap<ProductType, Integer> droneLoad = loadProducts.get(drone);

                int canFit = (int) Math.min(i.getCount(), Math.floor((maxPayload - drone.getCarryingWeight()) / i.getProduct().getWeight()));

                droneLoad.put(i.getProduct(), droneLoad.getOrDefault(i.getProduct(), 0) + canFit);

                CommandLoadUnload command = new CommandLoadUnload(drone, CommandType.LOAD, warehouse, i.getProduct(), canFit);
                tempCommands.put(command, tempCommands.getOrDefault(command, 0) + 1);

                turn += Math.ceil(Utils.euclidianDistance(drone.getPosition(), order.getPosition())) + 1;

                drone.setPosition(warehouse.getPosition().copy());
                drone.getProducts().add(new FlyingItem(i.getProduct(), canFit, order));

                if(canFit == i.getCount()){
                    iterator.remove();
                }
            }

            tempCommands.entrySet().stream().forEach(e -> {
                if(e.getKey() instanceof CommandLoadUnload){
                    ((CommandLoadUnload) e.getKey()).setCount(e.getValue());
                }else if(e.getKey() instanceof CommandDeliver){
                    ((CommandDeliver) e.getKey()).setCount(e.getValue());
                }else if(e.getKey() instanceof CommandWait){
                    ((CommandWait) e.getKey()).setTurns(e.getValue());
                }
            });

            commands.addAll(tempCommands.keySet());
        }

        if(turn < maxTurns) {
            drones.forEach(drone -> {
                PathSolver solver = new PathSolver(drone);
                LinkedList<FlyingItem> path = solver.solvePath();

                path.forEach(p -> {
                    CommandDeliver command = new CommandDeliver(drone, p);
                    commands.add(command);

                    turn += Math.ceil(Utils.euclidianDistance(drone.getPosition(), p.getOrder().getPosition())) + 1;

                    drone.setPosition(p.getOrder().getPosition().copy());
                    drone.getProducts().remove(p);

                    if (p.getOrder().allDelivered() && !p.getOrder().isCompleted()) {
                        p.getOrder().markCompleted(turn);
                    }
                });
            });
        }

        return new Result(score, commands);
    }

}
