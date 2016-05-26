package com.donmateo.application;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.donmateo.tables.Info;
import org.hibernate.SessionFactory;

enum ApplicationState {
    START,
    SET_OF_INFORMATION_DISPLAY,
    INFORMATION_DISPLAY,
    INFORMATION_ADD,
    UNDEFINED
}

/**
 * Created by mateusz.osypinski on 2016-05-01.
 */
public class ApplicationInterface {
    SessionFactory factory;
    private ManageHashTag manage_ht;
    private ManageHashTag_Info manage_ht_info;
    private ManageHashTag_HashTag manage_ht_ht;
    private ManageInfo manage_info;
    private ApplicationState app_state;
    private Stack<ApplicationState> app_stack;

    public ApplicationInterface(SessionFactory factory) {
        app_state = ApplicationState.START;
        this.factory = factory;
        manage_ht = new ManageHashTag(factory);
        manage_ht_info = new ManageHashTag_Info(factory);
        manage_ht_ht = new ManageHashTag_HashTag(factory);
        manage_info = new ManageInfo(factory);
        app_stack = new Stack<ApplicationState>();
    }

    public List<String> getUserInput() {

        String input;
        input = System.console().readLine();

        return Arrays.asList(input.split("\\s*,\\s*"));
    }

    public void userPolling() throws IOException {
        setNewState(ApplicationState.START, false);
        boolean back = false;
        while (!back) {
            printWhereAreYou();
            List<String> command = getUserInput();

            if (command != null && command.size() > 0) {

                CommandType comm_type = Command.parseString(command.get(0));
                switch (comm_type) {
                        case NONE:
                            System.out.print("COMMAND NOT RECOGNIZED");
                            break;
                        case ADD_INFO:
                            addInfo();
                            break;
                        case SHOW_INFO:
                            break;
                        case INFO_FROM_HT:
                            String ht_list = "";
                            for ( String elem : command ) {
                                ht_list += elem;
                            }
                            infoFromHT(ht_list);
                            break;
                        case BACK:
                            back = true;
                            break;
                        default:
                            System.out.print("COMMAND NOT RECOGNIZED");
                            break;
                }
            }

        }
        returnToPreviousState();
    }

    public void addInfo() throws IOException {

        setNewState(ApplicationState.INFORMATION_ADD, true);

        String descpription = getStringFromUser("Give a short description of information", 3);
        if (descpription.contains("@back"))
            return;
        String information = getStringFromUser("Give an information content", 3);
        if (information.contains("@back"))
            return;
        Integer id_info = manage_info.addInfo(information, descpription);
        if (id_info != null) {

            String ht = getStringFromUser("Tag the information", 0);
            if (ht.contains("@back"))
                return;

            if (ht.length() > 0) {
                List<String> ht_list = manage_ht.parseHashTagSequence(ht);
                Integer id_ht;
                for (String ht_elem : ht_list) {
                    if (!manage_ht.isHashTagInDB(ht_elem)) {
                        id_ht = manage_ht.addHashTag(ht_elem);
                        manage_ht_info.addHashTag_Info(id_info, id_ht);
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

    public void infoFromHT (String ht_list) throws IOException {
        List<String> ht_text_list = manage_ht.parseHashTagSequence(ht_list);
        List<Info> info_list = manage_info.getInfoIdsFromTagText(ht_text_list);
        showInfoList(info_list);
    }

    public void showInfoList(List<Info> info_list) throws IOException {
        setNewState(ApplicationState.SET_OF_INFORMATION_DISPLAY, false);
        if (info_list.size() > 0){
            boolean back = false;
            while (!back) {
                UtilsClass.clearCL();
                printWhereAreYou();
                System.out.println("FOUND INFORMATION:");

                for (int i = 0; i < info_list.size(); i++) {
                    System.out.println(Integer.toString(i + 1) + ". " + info_list.get(i).getDescription());
                }

                System.out.println("\nGIVE NUMBER TO DISPLAY");
                NumberCommand number_command = Command.getCommandAndNumberFromRange(app_state, 1, info_list.size());
                switch (number_command.getCommand_type()){
                    case NUM_CHOSEN:
                        showInfo(info_list.get(number_command.getNumber() - 1));
                        break;
                    case BACK:
                        returnToPreviousState();
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
            UtilsClass.clearCL();
            System.out.println("NO INFORMATION FOUND. TRY OTHER HASH TAGS");
        }
        returnToPreviousState();
    }

    public void showInfo(Info info){
        setNewState(ApplicationState.INFORMATION_DISPLAY, false);
        boolean back = false;
        while(!back){
            printWhereAreYou();
            System.out.println(info);
            CommandType command_type = waitForUserCommand();

            switch (command_type){
                case BACK:
                    back = true;
                default:
                    System.out.println("COMMAND NOT RECOGNIZED");
                    break;
            }
        }
        returnToPreviousState();
    }

    private void returnToPreviousState(){
        if (!this.app_stack.isEmpty()){
            app_state = this.app_stack.pop();
        }
    }

    private void setNewState(ApplicationState app_state, boolean print_where_are_you){
        this.app_stack.push(this.app_state);
        this.app_state = app_state;

        if(print_where_are_you)
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

    public void printWhereAreYou(){
        switch (app_state){
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
}



