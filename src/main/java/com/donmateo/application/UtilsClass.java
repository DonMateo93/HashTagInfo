package com.donmateo.application;

import java.io.IOException;

/**
 * Created by mateusz.osypinski on 2016-05-01.
 */
public class UtilsClass {

    public static void clearCL() throws IOException {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static boolean isInteger(String s) {
        try{
            int num = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String cleanString(String input){
        input.replace(" ", "");
        input.replace(".", "");
        input.replace(",", "");
        input.replace("?", "");
        input.replace("-", "");
        input.replace("!", "");
        input.replace(":", "");
        input.replace(";", "");
        return input;
    }


}
