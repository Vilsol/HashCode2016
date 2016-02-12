package me.vilsol.hashcode2016;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.NodeEvaluator2;
import com.google.ortools.constraintsolver.RoutingModel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PathSolver {

    private Drone drone;

    public PathSolver(Drone drone) {
        this.drone = drone;
    }

    public LinkedList<FlyingItem> solvePath(){
        List<Order> orders = drone.getProducts().stream().map(FlyingItem::getOrder).distinct().collect(Collectors.toList());
        int size = orders.size();
        RoutingModel routing = new RoutingModelWithoutGC(size + 1, 1);

        List<Position> pos = orders.stream().map(Order::getPosition).collect(Collectors.toList());
        Position[] positions = new Position[size + 1];

        positions[0] = drone.getPosition();
        for (int i = 1; i < size + 1; i++) {
            positions[i] = pos.remove(0);
        }

        routing.setCost(new NodeEvaluator2(){
            @Override
            public long run(int firstIndex, int secondIndex) {
                return (long) Utils.euclidianDistance(positions[firstIndex], positions[secondIndex]);
            }
        });

        routing.setDepot(0);
        routing.addDimension(new NodeEvaluator2(){
            @Override
            public long run(int firstIndex, int secondIndex) {
                return 1;
            }
        }, size + 1, size + 1, true, "main");
        routing.setFirstSolutionStrategy(RoutingModel.ROUTING_PATH_CHEAPEST_ARC);

        Assignment solution = routing.solve();

        LinkedList<FlyingItem> path = new LinkedList<>();

        if (solution != null) {
            for (long node = solution.value(routing.nextVar(0)); !routing.isEnd(node); node = solution.value(routing.nextVar(node))) {
                path.add(drone.getProducts().get((int) (node - 1)));
            }
        }

        return path;
    }

    static {
        System.loadLibrary("jniortools");
    }

    static public class RoutingModelWithoutGC extends RoutingModel {

        public RoutingModelWithoutGC(final int i, final int i1) {
            super(i, i1);
        }

        @Override
        protected void finalize() {}

    }

}
