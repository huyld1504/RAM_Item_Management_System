/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool.utils;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class Validation {

    public static boolean isEmptyString(String string) {
        return string.trim().equals("");
    }

    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }

    public static boolean isMatchWithFormat(String string, String format) {
        return string.matches(format);
    }

    public static boolean isValidModelYear(int modelYear) {
        Date date = new Date();
        String now = date.toGMTString();
        int validYear = Integer.parseInt(now.split("\\s+")[2]);

        return modelYear < validYear;
    }
}
