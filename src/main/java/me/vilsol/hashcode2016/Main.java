package me.vilsol.hashcode2016;

import me.vilsol.hashcode2016.commands.Command;
import me.vilsol.hashcode2016.solvers.Solver;
import me.vilsol.hashcode2016.solvers.WeightSolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> files = Arrays.asList("busy_day.in", "redundancy.in", "mother_of_all_warehouses.in");
        files.stream().forEach(f -> {
            try {
                solve(f);
                System.gc();
                System.out.println("----------------------------------------");
                Thread.sleep(1000);
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void solve(String file) throws URISyntaxException, IOException {
        List<String> input = Files.readAllLines(Paths.get(Main.class.getResource("../../../" + file).toURI()));
        String[] line = input.remove(0).split("\\s");
        int rows = Integer.parseInt(line[0]);
        int columns = Integer.parseInt(line[1]);
        int drones = Integer.parseInt(line[2]);
        int maxTurns = Integer.parseInt(line[3]);
        int maxPayload = Integer.parseInt(line[4]);

        System.out.println("The timeout is " + maxTurns + " turns");

        System.out.println("I have " + drones + " drones, each can carry " + maxPayload + "u");

        int productTypeCount = Integer.parseInt(input.remove(0));
        List<ProductType> productTypes = new ArrayList<>(productTypeCount);

        System.out.println("I have " + productTypeCount + " product types");

        line = input.remove(0).split("\\s");
        for (int i = 0; i < productTypeCount; i++) {
            productTypes.add(new ProductType(i, Integer.parseInt(line[i])));
        }

        int warehouseCount = Integer.parseInt(input.remove(0));
        List<Warehouse> warehouses = new ArrayList<>(warehouseCount);

        System.out.println("I have " + warehouseCount + " warehouses");

        for (int i = 0; i < warehouseCount; i++) {
            line = input.remove(0).split("\\s");

            int row = Integer.parseInt(line[0]);
            int column = Integer.parseInt(line[1]);

            line = input.remove(0).split("\\s");

            Warehouse warehouse = new Warehouse(i, new Position(row, column));
            for (int j = 0; j < productTypeCount; j++) {
                warehouse.setProductCount(productTypes.get(j), Integer.parseInt(line[j]));
            }

            warehouses.add(warehouse);
        }

        int orderCount = Integer.parseInt(input.remove(0));
        List<Order> orders = new ArrayList<>(orderCount);

        int orderedProductCount = 0;
        for (int i = 0; i < orderCount; i++) {
            line = input.remove(0).split("\\s");

            int row = Integer.parseInt(line[0]);
            int column = Integer.parseInt(line[1]);

            Order order = new Order(i, new Position(row, column));

            int productCount = Integer.parseInt(input.remove(0));
            orderedProductCount += productCount;

            line = input.remove(0).split("\\s");
            for (int j = 0; j < productCount; j++) {
                ProductType type = productTypes.get(Integer.parseInt(line[j]));
                order.setProductCount(type, order.getProductCount(type) + 1);
            }

            orders.add(order);
        }

        System.out.println("I have " + orderCount + " orders with " + orderedProductCount + " items");
        System.out.println("Theoretical max points are " + (orderCount * 100));

        List<Solver> solvers = Collections.singletonList(new WeightSolver());

        solvers.forEach(s -> {
            System.out.println("--------------------");
            System.out.println("Solving with " + s.getClass().getSimpleName());
            Result result = s.solve(rows, columns, drones, maxTurns, maxPayload, Utils.cloneList(productTypes), Utils.cloneList(warehouses), Utils.cloneList(orders));
            System.out.println("Finished with score: " + result.getScore());
            try {
                Files.write(Paths.get("output/" + file.split("\\.")[0] + "-" + s.getClass().getSimpleName() + ".out"), result.getCommands().stream().map(Command::toString).collect(Collectors.joining("\n")).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
