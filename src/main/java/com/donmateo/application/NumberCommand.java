package com.donmateo.application;

/**
 * Created by mateusz.osypinski on 2016-05-02.
 */
public class NumberCommand {
    private int number;
    private CommandType command_type;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CommandType getCommand_type() {
        return command_type;
    }

    public void setCommand_type(CommandType command_type) {
        this.command_type = command_type;
    }
}
