/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class FormatData {

    public static String formatDate(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/yyyy");
        String dateFormatted = sDateFormat.format(date);
        return dateFormatted;
    }
}
