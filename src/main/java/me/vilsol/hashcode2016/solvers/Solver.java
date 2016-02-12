package me.vilsol.hashcode2016.solvers;

import me.vilsol.hashcode2016.Order;
import me.vilsol.hashcode2016.ProductType;
import me.vilsol.hashcode2016.Result;
import me.vilsol.hashcode2016.Warehouse;

import java.util.List;

public interface Solver {

    Result solve(int rows, int columns, int droneCount, int maxTurns, int maxPayload, List<ProductType> productTypes, List<Warehouse> warehouses, List<Order> orders);

}
