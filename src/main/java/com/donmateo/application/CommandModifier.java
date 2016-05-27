package com.donmateo.application;

/**
 * Created by mateusz.osypinski on 2016-05-22.
 */
enum CommandModifierEnum {
    ALL,
    AP,
    CLP,
    INFO,
    DESC,
    HT,
    NUM,
    NONE
}

public class CommandModifier {

    private CommandModifierEnum modifier;
    private String adnotation;

    public static CommandModifierEnum mapStringToCommandModifier(String command_str){
        if(command_str != null)
            if(command_str.length() > 0) {

                command_str = command_str.toLowerCase();
                if (command_str.equals("-all"))
                    return CommandModifierEnum.ALL;

                else if (command_str.equals("-ap"))
                    return CommandModifierEnum.AP;

                else if (command_str.equals("-clp"))
                    return CommandModifierEnum.CLP;

                else if (command_str.equals("-info"))
                    return CommandModifierEnum.INFO;

                else if (command_str.equals("-desc"))
                    return CommandModifierEnum.DESC;

                else if (command_str.contains("#"))
                    return CommandModifierEnum.HT;

                else if (UtilsClass.isInteger(command_str))
                    return CommandModifierEnum.NUM;

                else return CommandModifierEnum.NONE;
            }
        return CommandModifierEnum.NONE;
    }

    public static String mapCommandModifierToString(CommandModifierEnum command_modifier){

        switch (command_modifier) {
            case ALL:
                return "all";
            case AP:
                return "ap";
            case CLP:
                return "clp";
            case INFO:
                return "info";
            case DESC:
                return "desc";
            case HT: //hashtag has no constant modifier string
                return "";
            case NUM: //numeric has no constant modifier string
                return "";
            case NONE:
                return "";
            default:
                return "";
        }
    }

    public static boolean isCommandModifierAvailible(CommandModifierEnum command_modifier, ApplicationState app_state, CommandType command_type){
        if (Command2.isCommandAvailibleInState(command_type, app_state)){
            switch (app_state){
                case START:
                    switch (command_type) {
                        case SHOW_INFO:
                            if (command_modifier == CommandModifierEnum.ALL)
                                return true;
                            else return false;
                        case ADD_INFO:
                            if (command_modifier == CommandModifierEnum.NUM) //użytkownik może dodać kilka na raz
                                return true;
                            else return false;
                        case BACK:
                            return false;
                        case END:
                            return false;
                        case MAIN:
                            return false;
                        case INFO_FROM_HT:
                            if (command_modifier == CommandModifierEnum.HT)
                                return true;
                            else return false;
                        case NUM_CHOSEN:
                            return false;
                        case CHANGE:
                            return false;
                        case NONE:
                            return false;
                        default:
                            return false;
                    }
                case SET_OF_INFORMATION_DISPLAY:
                    switch (command_type) {
                        case SHOW_INFO:
                            if (command_modifier == CommandModifierEnum.ALL
                                    || command_modifier == CommandModifierEnum.NUM)
                                return true;
                            else return false;
                        case ADD_INFO:
                            return false;
                        case BACK:
                            return false;
                        case END:
                            return false;
                        case MAIN:
                            return false;
                        case INFO_FROM_HT:
                            return false;
                        case NUM_CHOSEN:
                            if (command_modifier == CommandModifierEnum.NUM)
                                return true;
                            else return false;
                        case CHANGE:
                            if (command_modifier == CommandModifierEnum.ALL
                                    || command_modifier == CommandModifierEnum.NUM)
                                return true;
                            else return false;
                        case NONE:
                            return false;
                        default:
                            return false;
                    }
                case INFORMATION_DISPLAY:
                    switch (command_type) {
                        case SHOW_INFO:
                            if (command_modifier == CommandModifierEnum.ALL)
                                return true;
                            else return false;
                        case ADD_INFO:
                            return false;
                        case BACK:
                            return false;
                        case END:
                            return false;
                        case MAIN:
                            return false;
                        case INFO_FROM_HT:
                            if (command_modifier == CommandModifierEnum.HT)
                                return true;
                            else return false;
                        case NUM_CHOSEN:
                            return false;
                        case CHANGE:
                            if (command_modifier == CommandModifierEnum.ALL
                                    || command_modifier == CommandModifierEnum.AP
                                    || command_modifier == CommandModifierEnum.CLP
                                    || command_modifier == CommandModifierEnum.INFO
                                    || command_modifier == CommandModifierEnum.DESC)
                                return true;
                            else return false;
                        case NONE:
                            return false;
                        default:
                            return false;
                    }
                case INFORMATION_ADD:
                    switch (command_type) {
                        case SHOW_INFO:
                            return false;
                        case ADD_INFO:
                            return false;
                        case BACK:
                            return false;
                        case END:
                            return false;
                        case MAIN:
                            return false;
                        case INFO_FROM_HT:
                            return false;
                        case NUM_CHOSEN:
                            return false;
                        case CHANGE:
                            return false;
                        case NONE:
                            return false;
                        default:
                            return false;
                    }
                case UNDEFINED:
                    switch (command_type) {
                        default:
                            return false;
                    }
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public CommandModifierEnum getModifier() {
        return modifier;
    }

    public void setModifier(CommandModifierEnum modifier) {
        this.modifier = modifier;
    }

    public String getAdnotation() {
        return adnotation;
    }

    public void setAdnotation(String adnotation) {
        this.adnotation = adnotation;
    }
}
