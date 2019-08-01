package com.newmedia.erxeslibrary.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    //used in Conversation list.
    static public String convert_datetime(Long createDate,String language) {
        Long diffTime = Calendar.getInstance().getTimeInMillis() - createDate;

        diffTime = diffTime / 1000;
        long weeks = diffTime / 604800;
        long days = (diffTime % 604800) / 86400;
        long hours = ((diffTime % 604800) % 86400) / 3600;
        long minutes = (((diffTime % 604800) % 86400) % 3600) / 60;
        long seconds = (((diffTime % 604800) % 86400) % 3600) % 60;
        if (language == null || language.equalsIgnoreCase("en")) {
            if (weeks > 0) {
                return ("" + weeks + " weeks ago");
            } else if (days > 0) {
                return ("" + days + " d");
            } else if (hours > 0) {
                return ("" + hours + " h");
            } else if (minutes > 0) {
                return ("" + minutes + " m");
            } else {
                return ("" + seconds + " s");
            }
        } else {
            if (weeks > 0) {
                return ("" + weeks + " 7 Хоногийн өмнө");
            } else if (days > 0) {
                return ("" + days + " өдрийн өмнө");
            } else if (hours > 0) {
                return ("" + hours + " цагийн өмнө");
            } else if (minutes > 0) {
                return ("" + minutes + " минутын өмнө");
            } else {
                return ("" + seconds + " секундын өмнө");
            }
        }
    }

    //long unixtime to string formatted date
    static public String Message_datetime(String createDate_s,String language) {

        Long createDate = null;
        try {
            createDate = Long.valueOf(createDate_s);
        } catch (NumberFormatException e) {
            return "";
        }


        Date date = new Date();
        date.setTime(createDate);

        long diffTime = Calendar.getInstance().getTimeInMillis() - createDate;

        diffTime = diffTime / 1000;
        long weeks = diffTime / 604800;
        long days = (diffTime % 604800) / 86400;
        long hours = ((diffTime % 604800) % 86400) / 3600;
        long minutes = (((diffTime % 604800) % 86400) % 3600) / 60;
        long seconds = (((diffTime % 604800) % 86400) % 3600) % 60;
        SimpleDateFormat format =
                new SimpleDateFormat("HH:mm");
        SimpleDateFormat format2 =
                new SimpleDateFormat("EEE HH:mm");
        SimpleDateFormat format3 =
                new SimpleDateFormat("MMM d,HH:mm");
        if (language.equalsIgnoreCase("mn")) {
            format3 = new SimpleDateFormat("MMM сарын d,HH:mm");
            format2 = format3;
        }
        if (weeks > 0) {
            return format3.format(date);
        } else if (days > 0) {
            return format2.format(date);
        } else {
            return format.format(date);
        }
    }

    static public String now(String language) {
        Date date = new Date();
        SimpleDateFormat mn = new SimpleDateFormat("yyyy оны MM сарын d, HH:mm");
        SimpleDateFormat en = new SimpleDateFormat("MMM dd / yyyy HH:mm");
        if (language.equalsIgnoreCase("en")) {
            return en.format(date);
        } else {
            return mn.format(date);
        }
    }

    static public String full_date(String createDate_s,String language) {
        Long createDate = null;
        try {
            createDate = Long.valueOf(createDate_s);
        } catch (NumberFormatException e) {
            return "";
        }
        Date date = new Date();
        date.setTime(createDate);
        SimpleDateFormat mn = new SimpleDateFormat("yyyy оны MM сарын d, HH:mm");
        SimpleDateFormat en = new SimpleDateFormat("MMM dd / yyyy HH:mm");
        if (language.equalsIgnoreCase("en")) {
            return en.format(date);
        } else {
            return mn.format(date);
        }
    }

}
