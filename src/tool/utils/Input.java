/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool.utils;

import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class Input {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String FORMAT_NUMBER = "^-?\\d+$";

    public static int getInt(String message) throws Exception {
        try {
            Scanner sc = scanner;
            String input = "";
            do {
                System.out.print(message);
                input = sc.nextLine();
            } while (!input.matches(FORMAT_NUMBER));

            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public static int getInt(String message, int oldData) throws Exception {
        try {
            Scanner sc = scanner;
            System.out.print(message);
            String input = sc.nextLine();

            if (!input.trim().matches(FORMAT_NUMBER)) {
                return oldData;
            }

            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public static String getString(String message) throws Exception {
        try {
            Scanner sc = scanner;
            String input;
            do {
                System.out.print(message);
                input = sc.nextLine();
            } while (input.trim().isEmpty());
            return input;
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getString(String message, String oldData) throws Exception {
        try {
            Scanner sc = scanner;
            System.out.print(message);
            String input = sc.nextLine();

            if (input.trim().equals("")) {
                input = oldData;
            }
            return input;
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean confirmYesOrNo(String message) throws Exception {
        try {
            boolean flag = true;
            String option;
            while (flag) {
                option = getString(message, "a");
                if (option.equalsIgnoreCase("y")) {
                    flag = true;
                    break;
                } else if (option.equalsIgnoreCase("n")) {
                    flag = false;
                    break;
                }
            }
            return flag;
        } catch (Exception e) {
            throw e;
        }
    }
}
