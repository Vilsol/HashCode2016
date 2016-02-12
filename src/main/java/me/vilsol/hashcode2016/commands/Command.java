package me.vilsol.hashcode2016.commands;

import me.vilsol.hashcode2016.Drone;

public abstract class Command {

    private Drone drone;
    private CommandType type;

    public Command(Drone drone, CommandType type) {
        this.drone = drone;
        this.type = type;
    }

    public Drone getDrone() {
        return drone;
    }

    public CommandType getType() {
        return type;
    }

    public abstract String toString();

}
