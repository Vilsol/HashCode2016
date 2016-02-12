package me.vilsol.hashcode2016.commands;

import me.vilsol.hashcode2016.Drone;

public class CommandWait extends Command {

    private int turns;

    public CommandWait(Drone drone, int turns) {
        super(drone, CommandType.WAIT);
        this.turns = turns;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    @Override
    public String toString() {
        return getDrone().getId() + " " + getType().getS() + " " + turns;
    }

}
