package tests;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.idgform.utils.DateParser;

import junit.framework.Assert;

public class UtilsTests
{

    @Test
    public void testStringToDate() throws ParseException
    {
        Date date = DateParser.stringToDate("22-03-2019 16:20:00");
        //Assert.assertEquals("Wed Mar 20 06:44:12 IST 2019", date.toString());
        System.out.println(date);
    }

    @Test
    public void testDateToString() throws Exception
    {
        String date = DateParser.dateToString(DateParser.stringToDate("20-03-2019 06:44:12"));
        Assert.assertEquals("20-03-2019 06:44:12", date);
    }
}
