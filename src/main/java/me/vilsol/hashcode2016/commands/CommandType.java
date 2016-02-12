package me.vilsol.hashcode2016.commands;

public enum CommandType {

    LOAD("L"), UNLOAD("U"), DELIVER("D"), WAIT("W");

    private String s;

    CommandType(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

}
