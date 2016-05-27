package com.donmateo.application;

import java.io.IOException;
import java.util.*;

import com.donmateo.tables.HashTag;
import com.donmateo.tables.Info;
import org.hibernate.SessionFactory;

/**
 * Created by mateusz.osypinski on 2016-05-01.
 */
public class ApplicationInterface3 {
    SessionFactory factory;
    private ManageHashTag manage_ht;
    private ManageHashTag_Info manage_ht_info;
    private ManageHashTag_HashTag manage_ht_ht;
    private ManageInfo manage_info;
    private ApplicationState app_state;
    private Stack<ApplicationState> app_stack;
    private CommandType global_command;
    private String command_str;
    private ApplicationState app_command_put_state; //state of application during command_str field overriding

    public ApplicationInterface3(SessionFactory factory) {
        app_state = ApplicationState.START;
        this.factory = factory;
        manage_ht = new ManageHashTag(factory);
        manage_ht_info = new ManageHashTag_Info(factory);
        manage_ht_ht = new ManageHashTag_HashTag(factory);
        manage_info = new ManageInfo(factory);
        app_stack = new Stack<ApplicationState>();
        this.global_command = CommandType.NONE;
    }

    public void updateCommandStr(String command_str){
        this.command_str = command_str;
        this.app_command_put_state = this.app_state;
    }

    public String getUserInput() {

        boolean back = false;
        String input = "";
        while (!back) {
            input = System.console().readLine();
            if (input.length() > 0) {
                back = true;
            } else {
                System.out.println("CANNOT PARSE EMPTY COMMAND");
            }
        }

        return input;
    }

    public int begin() throws IOException {
        setNewState(ApplicationState.START, false);

        boolean back = false;
        while (!back) {

            if (global_command == CommandType.MAIN)
                global_command = CommandType.NONE;
            else if (global_command == CommandType.END)
                return 0;

            printWhereAreYou();
            Command2 command = userPolling();
            switch (command.getCommand_type()) {
                case NONE:
                    System.out.print("COMMAND NOT RECOGNIZED");
                    break;
                case ADD_INFO:
                    addInfo();
                    break;
                case SHOW_INFO:
                    System.out.print("FUNCTION NOT IMPLEMENTED YET");
                    break;
                case INFO_FROM_HT:
                    infoFromHT(command_str);
                    break;
                case BACK:
                    back = true;
                    break;
                case END:
                    back = true;
                    break;
                case MAIN:
                    break;
                default:
                    System.out.print("COMMAND NOT RECOGNIZED");
                    break;
            }
        }

        returnToPreviousState();
        return 0;
    }

    public Command2 userPolling() throws IOException {
        Command2 command = new Command2();
        String command_str = getUserInput();
        command.setCommand_type(parseCommand(command_str));
        return command;
    }

    public CommandType parseCommand(String command_str) {
        CommandType command_type = CommandType.NONE;
        List<String> command_list = Arrays.asList(command_str.split("\\s*,\\s*"));
        if (command_str.length() > 0) {
            command_type = Command.parseString(Arrays.asList(command_str.split("\\s*,\\s*")).get(0));
            if (Command.isCommandAvailibleInState(command_type, app_state)) {
                updateCommandStr(command_str); //to nie potrzebne ale zachowamy dla wstecznej zgodności
            } else {
                System.out.println("COMMAND NOT AVAILIABLE IN THIS STATE");
            }
        }
        return command_type;
    }

    public List<CommandModifierEnum> parseModifier(String command_str, CommandType command_type){
        List<CommandModifierEnum> command_mod_list = new ArrayList<CommandModifierEnum>();
        command_mod_list.clear();
        List<String> command_list = Arrays.asList(command_str.split("\\s*,\\s*"));
        System.out.println("tu podzielona lista command:" + command_list);
        CommandModifierEnum tmp_modifier;
        for(int i = 1; i < command_list.size(); i++){
            tmp_modifier = CommandModifier.mapStringToCommandModifier(command_list.get(i));
            if(CommandModifier.isCommandModifierAvailible(tmp_modifier,app_state,command_type)){
                command_mod_list.add(tmp_modifier);
            }else{
                System.out.println("MODIFIER " + command_list.get(i) + "REJECTED");
            }
        }

        return command_mod_list; //todo: tu jeszcze funkcję dokładnie przejrzeć
    }

    public void addInfo() throws IOException {

        setNewState(ApplicationState.INFORMATION_ADD, true);

        String descpription = getStringFromUser("Give a short description of information", 3);
        if (descpription.contains("@")) {
            CommandType command = parseCommand(descpription);   //jest to rozwiązanie nadmiarowe, bo wystarczy dać return po
            switch (command) {                                  //if, ale jeżeli program będzie rozszerzany, zawsze może się przydać
                case BACK:
                    returnToPreviousState();
                    return;
                case END:
                    returnToPreviousState();
                    global_command = command;
                    return;
                case MAIN:
                    returnToPreviousState();
                    global_command = command;
                    return;
            }
        }

        String information = getStringFromUser("Give an information content", 3);
        if (information.contains("@")) {
            CommandType command = parseCommand(descpription);   //jest to rozwiązanie nadmiarowe, bo wystarczy dać return po
            switch (command) {                                  //if, ale jeżeli program będzie rozszerzany, zawsze może się przydać
                case BACK:
                    returnToPreviousState();
                    return;
                case END:
                    returnToPreviousState();
                    global_command = command;
                    return;
                case MAIN:
                    returnToPreviousState();
                    global_command = command;
                    return;
            }
        }

        String clipboard = getStringFromUser("Give a clipboard content", 0);
        if (information.contains("@")) {
            CommandType command = parseCommand(descpription);   //jest to rozwiązanie nadmiarowe, bo wystarczy dać return po
            switch (command) {                                  //if, ale jeżeli program będzie rozszerzany, zawsze może się przydać
                case BACK:
                    returnToPreviousState();
                    return;
                case END:
                    returnToPreviousState();
                    global_command = command;
                    return;
                case MAIN:
                    returnToPreviousState();
                    global_command = command;
                    return;
            }
        }

        Integer id_info = manage_info.addInfo(information, descpription, clipboard);
        if (id_info != null) {

            String ht = getStringFromUser("Tag the information", 0);
            if (ht.contains("@")) {
                CommandType command = parseCommand(descpription);   //jest to rozwiązanie nadmiarowe, bo wystarczy dać return po
                switch (command) {                                  //if, ale jest elastyczniejsze w razie rozszerzania
                    case BACK:
                        returnToPreviousState();
                        return;
                    case END:
                        returnToPreviousState();
                        global_command = command;
                        return;
                    case MAIN:
                        returnToPreviousState();
                        global_command = command;
                        return;
                }
            }

            if (ht.length() > 0) {
                List<String> ht_list = manage_ht.parseHashTagSequence(ht);
                Integer id_ht;
                for (String ht_elem : ht_list) {
                    if (!manage_ht.isHashTagInDB(ht_elem)) {
                        id_ht = manage_ht.addHashTag(ht_elem);
                        manage_ht_info.addHashTag_Info(id_info, id_ht);
                    } else {
                        HashTag hash_tag = manage_ht.getHashTagFromText(ht_elem);
                        if (ht != null){
                            manage_ht_info.addHashTag_Info(id_info, hash_tag.getId());
                        }
                    }
                }
            }
        }

        returnToPreviousState();
    }

    public String getStringFromUser(String message, int minimum_length) throws IOException {
        boolean ok = false;
        String answer = "";

        UtilsClass.clearCL();

        while (!ok) {
            System.out.println(message);
            answer = System.console().readLine();
            if (answer.length() >= minimum_length)
                ok = true;
            else {
                UtilsClass.clearCL();
                System.out.println("Too short command. Type at least" + Integer.toString(minimum_length) + "characters");
            }
        }

        UtilsClass.clearCL();

        return answer;
    }

    public void infoFromHT(String ht_list) throws IOException {
        List<String> ht_text_list = manage_ht.parseHashTagSequence(ht_list);
        List<Info> info_list = manage_info.getInfoIdsFromTagText(ht_text_list);
        showInfoList(info_list);
    }

    public void showInfoList(List<Info> info_list) throws IOException {
        setNewState(ApplicationState.SET_OF_INFORMATION_DISPLAY, false);
        if (info_list.size() > 0) {
            boolean back = false;
            while (!back) {

                switch (global_command) {
                    case END:
                        returnToPreviousState();
                        return;
                    case MAIN:
                        returnToPreviousState();
                        return;
                }

                printWhereAreYou();
                System.out.println("FOUND INFORMATION:");

                for (int i = 0; i < info_list.size(); i++) {
                    System.out.println(Integer.toString(i + 1) + ". " + info_list.get(i).getDescription());
                }

                System.out.println("\nGIVE NUMBER TO DISPLAY");
                NumberCommand number_command = Command.getCommandAndNumberFromRange(app_state, 1, info_list.size());
                switch (number_command.getCommand_type()) {
                    case NUM_CHOSEN:
                        showInfo(info_list.get(number_command.getNumber() - 1));
                        break;
                    case BACK:
                        returnToPreviousState();
                        return;
                    case END:
                        returnToPreviousState();
                        global_command = number_command.getCommand_type();
                        return;
                    case MAIN:
                        returnToPreviousState();
                        global_command = number_command.getCommand_type();
                        return;
                    case ADD_INFO:
                        System.out.println("ADDING CONTENT TO AN INFORMATION NOT IMPLEMENTED YET, WAIT FOR FURTHER RELEASE");
                        break;
                    case NONE:
                        System.out.println("COMMAND NOT RECOGNIZED");
                        break;
                    case SHOW_INFO:
                        System.out.println("COMMAND NOT RECOGNIZED");
                        break;
                    default:
                        System.out.println("COMMAND NOT RECOGNIZED");
                        break;
                }
            }
        } else {
            System.out.println("NO INFORMATION FOUND. TRY OTHER HASH TAGS");
        }
        returnToPreviousState();
    }

    public void showInfo(Info info) throws IOException {

        setNewState(ApplicationState.INFORMATION_DISPLAY, false);
        boolean back = false;
        while (!back) {
            switch (global_command) {
                case END:
                    return;
                case MAIN:
                    return;
            }

            printWhereAreYou();
            System.out.println(info);
            List<HashTag> ht_list = manage_ht.getHashTagsConnectedWithInfo(info);
            if (ht_list.size() == 0){
                System.out.println("NO HASHTAGS");
            }else {
                String ht_out = "";
                for (HashTag ht : ht_list) {
                    ht_out += "#" + ht.getText() + " ";

                }
                System.out.println("\nTAGS:");
                System.out.println(ht_out);
            }

//            info.setClipboard("nowa");
//            manage_info.updateInfo(info);
            CommandType command_type = userPolling();

            switch (command_type) {
                case BACK:
                    back = true;
                    break;
                case END:
                    global_command = command_type;
                    back = true;
                    break;
                case MAIN:
                    global_command = command_type;
                    back = true;
                    break;
                case CHANGE:
                    changeInfo(info);
                    break;
                default:
                    System.out.println("COMMAND NOT RECOGNIZED");
                    break;
            }
        }
        returnToPreviousState();
    }

    private void returnToPreviousState() {
        if (!this.app_stack.isEmpty()) {
            app_state = this.app_stack.pop();
        }
    }

    private void setNewState(ApplicationState app_state, boolean print_where_are_you) throws IOException {
        this.app_stack.push(this.app_state);
        this.app_state = app_state;

        if (print_where_are_you)
            printWhereAreYou();
    }

    private CommandType waitForUserCommand() {

        boolean back = false;
        CommandType command_type = null;
        while (!back) {
            String command = System.console().readLine();
            UtilsClass.cleanString(command);
            command_type = Command.parseString(command);
            if (Command.isCommandAvailibleInState(command_type, app_state)) {
                back = true;
            } else {
                System.out.println("COMMAND NOT AVAILABLE");
            }
        }
        return command_type;
    }

    public void printWhereAreYou() throws IOException {
        UtilsClass.clearCL();
        switch (app_state) {
            case START:
                System.out.println("%MAIN");
                break;
            case SET_OF_INFORMATION_DISPLAY:
                System.out.println("%INFORMATION LIST DISPLAY");
                break;
            case INFORMATION_DISPLAY:
                System.out.println("%INFORMATION DISPLAY");
                break;
            case INFORMATION_ADD:
                System.out.println("%ADD NEW INFORMATION");
                break;
            default:
                System.out.println("%UNDEFINED LOCATION");
                break;
        }
    }

    //todo: powinna być obsługa wyjścia ewentualnego @back @end @main
    public void changeInfo(Info info ) throws IOException {
        if (info != null) {
            boolean append = false;
            String description;
            String information;
            String clipboard;
            List<String> command_str_list = Arrays.asList(command_str.toLowerCase().split("[,\\s:\\?\\.#]"));// jakby coś nie hulało to tu jest poprzedni: "[,\\s\\-:\\?\\.#]"

            if (command_str_list.contains("-ap"))
                append = true;

            if (command_str_list.contains("-all") || !(command_str_list.contains("-desc") && command_str_list.contains("-info") && command_str_list.contains("-clp") && command_str_list.contains("-tag"))) {

                if (append) {
                    description = getStringFromUser("Give a description of information", 3);
                    info.setDescription(info.getDescription() + description);

                    information = getStringFromUser("Give an information content", 3);
                    info.setText(info.getText() + information);

                    clipboard = getStringFromUser("Give a description of clipboard", 0);
                    info.setClipboard(info.getClipboard() + clipboard);

                } else {
                    info.setDescription(getStringFromUser("Give a description of information", 3));
                    info.setText(getStringFromUser("Give an information content", 3));
                    info.setClipboard(getStringFromUser("Give a description of clipboard", 0));
                }
            } else {

                if (command_str_list.contains("-desc")) {
                    if (append) {
                        description = getStringFromUser("Give a description of information", 3);
                        info.setDescription(info.getDescription() + description);
                    } else {
                        info.setDescription(getStringFromUser("Give a description of information", 3));
                    }
                }

                if (command_str_list.contains("-info")) {
                    if (append) {
                        information = getStringFromUser("Give an information content", 3);
                        info.setText(info.getText() + information);
                    } else {
                        info.setText(getStringFromUser("Give an information content", 3));
                    }
                }

                if (command_str_list.contains("-clp")) {
                    if (append) {
                        clipboard = getStringFromUser("Give a description of clipboard", 0);
                        info.setClipboard(info.getClipboard() + clipboard);
                    } else {
                        info.setClipboard(getStringFromUser("Give a description of clipboard", 0));
                    }
                }
            }

            manage_info.updateInfo(info);
        } else {
            System.out.println("%null class in changeInfo method (ApplicationInterface2)");
        }
    }
}