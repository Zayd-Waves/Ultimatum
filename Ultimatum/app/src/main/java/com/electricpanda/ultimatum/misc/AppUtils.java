package com.electricpanda.ultimatum.misc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static String generateUserId() {
        char digits[] = {'0','1','2','3','4','5','6','7','8','9'};
        char letters[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'g', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z'};


        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            result.append(digits[(int)Math.floor(Math.random() * 10)]);
        }
        for(int i = 0; i < 5; i++) {
            result.append(letters[(int)Math.floor(Math.random() * 26)]);
        }
        return result.toString();
    }

    public static String generatePactId() {
        char digits[] = {'0','1','2','3','4','5','6','7','8','9'};
        char letters[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'g', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z'};


        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            result.append(digits[(int)Math.floor(Math.random() * 10)]);
        }
        for(int i = 0; i < 10; i++) {
            result.append(letters[(int)Math.floor(Math.random() * 26)]);
        }
        return result.toString();
    }

    public static String convertDateToString(Date date) {
        String dateString = "";
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        dateString = dateFormat.format(date);

        return dateString;
    }

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }
}
