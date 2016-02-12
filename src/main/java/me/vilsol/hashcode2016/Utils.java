package me.vilsol.hashcode2016;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static <T> List<T> cloneList(List<? extends Copyable<T>> list) {
        return list.stream().map(Copyable::copy).collect(Collectors.toList());
    }

    public static double euclidianDistance(Position pos1, Position pos2){
        return Math.sqrt(Math.abs(pos1.getRow() - pos2.getRow()) + Math.abs(pos1.getColumn() - pos2.getColumn()));
    }

}
