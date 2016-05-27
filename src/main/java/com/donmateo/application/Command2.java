package com.donmateo.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz.osypinski on 2016-05-22.
 */
public class Command2 {

    private CommandType command_type;
    private List<CommandModifier> modifier_list;

    public Command2(){
        this.command_type = CommandType.NONE;
        this.modifier_list = new ArrayList<CommandModifier>();
        this.modifier_list.clear();
    }

    public static CommandType parseString(String command){

        if(command.length() > 0) {
            command.toLowerCase();
            if (command.contains("@showi")) {
                return CommandType.SHOW_INFO;

            } else if (command.contains("@addi")) {
                return CommandType.ADD_INFO;

            } else if (command.contains("@back")) {
                return CommandType.BACK;

            }else if ( command.substring(0,1).equals("#") ) {
                return CommandType.INFO_FROM_HT;

            }else if ( command.contains("@end")) {
                return CommandType.END;

            }else if ( command.contains("@main")) {
                return CommandType.MAIN;

            }else if ( command.contains("@ch")) {
                return CommandType.CHANGE;
            }
        }

        return CommandType.NONE;
    }

    public static NumberCommand getCommandAndNumberFromRange(ApplicationState app_state, int from, int to){

        NumberCommand number_command = null;
        boolean back = false;
        while(!back){
            String input = System.console().readLine();
            if ( UtilsClass.isInteger(input) ){
                int int_input = Integer.parseInt(input);
                if ( int_input >= from && int_input <= to){
                    number_command = new NumberCommand();
                    number_command.setCommand_type(CommandType.NUM_CHOSEN);
                    number_command.setNumber(int_input);
                    back = true;
                } else {
                    System.out.println("IMPROPER NUMBER. TRY AGAIN");
                }
            } else {
                input = UtilsClass.cleanString(input);
                CommandType command_type = parseString(input);
                if ( isCommandAvailibleInState(command_type,app_state)) {
                    back = true;
                    number_command = new NumberCommand();
                    number_command.setNumber(-1);
                    number_command.setCommand_type(command_type);
                }
            }
        }
        return number_command;
    }

    public static boolean isCommandAvailibleInState(CommandType command_type, ApplicationState app_stae) {

        if (command_type == CommandType.BACK || command_type == CommandType.END || command_type == CommandType.MAIN)
            return true;

        switch (app_stae) {
            case UNDEFINED:
                return false;
            case SET_OF_INFORMATION_DISPLAY:
                if (command_type == CommandType.NUM_CHOSEN
                        || command_type == CommandType.ADD_INFO
                        || command_type == CommandType.BACK
                        || command_type == CommandType.SHOW_INFO
                        || command_type == CommandType.END
                        || command_type == CommandType.MAIN)
                    return true;
                else return false;

            case START:
                if (command_type == CommandType.NUM_CHOSEN
                        || command_type == CommandType.ADD_INFO
                        || command_type == CommandType.INFO_FROM_HT
                        || command_type == CommandType.BACK
                        || command_type == CommandType.SHOW_INFO
                        || command_type == CommandType.END
                        || command_type == CommandType.MAIN)
                    return true;
                else return false;

            case INFORMATION_DISPLAY:
                if ( command_type == CommandType.ADD_INFO
                        || command_type == CommandType.INFO_FROM_HT
                        || command_type == CommandType.BACK
                        || command_type == CommandType.END
                        || command_type == CommandType.MAIN
                        || command_type == CommandType.CHANGE)
                    return true;
                else return false;

            case INFORMATION_ADD:
                if ( command_type == CommandType.BACK
                        || command_type == CommandType.END
                        || command_type == CommandType.MAIN)
                    return true;
                else return false;

            default:
                return false;
        }
    }

    public CommandType getCommand_type() {
        return command_type;
    }

    public void setCommand_type(CommandType command_type) {
        this.command_type = command_type;
    }

    public List<CommandModifier> getModifier_list() {
        return modifier_list;
    }

    public void setModifier_list(List<CommandModifier> modifier_list) {
        this.modifier_list = modifier_list;
    }

    public void pushModifier(CommandModifier modifier){
        modifier_list.add(modifier);
    }
}

