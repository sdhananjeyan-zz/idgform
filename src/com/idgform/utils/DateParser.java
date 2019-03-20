package com.idgform.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser
{
    public static Date stringToDate(String date) throws ParseException
    {
        return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(date);
    }

    public static String dateToString(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
        return dateFormat.format(date);
    }
}
