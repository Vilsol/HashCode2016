package me.vilsol.hashcode2016;

import me.vilsol.hashcode2016.commands.Command;

import java.util.List;

public class Result {

    private int score;
    private List<Command> commands;

    public Result(int score, List<Command> commands) {
        this.score = score;
        this.commands = commands;
    }

    public int getScore() {
        return score;
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public String toString() {
        return "Result{" +
                "score=" + score +
                ", commands=" + commands +
                '}';
    }
}
