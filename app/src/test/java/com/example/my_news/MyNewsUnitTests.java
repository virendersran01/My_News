package com.example.my_news;

import com.example.my_news.utils.Utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyNewsUnitTests {

    //Date Formatting and Retrieval Testing//
    @Test
    public void checkEndDate() throws Exception {
        Utils formatter = new Utils();

        Assert.assertEquals("19980708", formatter.getEndDate("08/07/1998"));
        Assert.assertEquals(formatter.setCalendarFormat(), formatter.getEndDate(""));
    }

    @Test
    public void checkBeginDate() throws Exception {
        Utils formatter = new Utils();

        Assert.assertEquals("20190817", formatter.getEndDate("17/08/2019"));
        Assert.assertEquals(formatter.setCalendarFormatPriorYear(),
                formatter.getBeginDate(""));
    }

    @Test
    public void getItemArticleFormattedDate() throws Exception {
        Utils utils = new Utils();

        Assert.assertEquals(utils.getArticleItemFormattedDate(
                "2019-08-17T23:04:34-04:00"), "17/08/19");
    }

    @Test
    public void formatCalendar() throws Exception {
        Utils utils = new Utils();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        String actual = simpleDateFormat.format(calendar.getTime());
        String expected = utils.setCalendarFormat();

        Assert.assertEquals(expected, actual);
    }

    //Checkbox Functionality Testing//
    @Test
    public void getNewDesk() throws Exception {
        Utils utils = new Utils();

        String[] empty = {"", "", ""};
        String[] full = {"Books", "Health", "Movies",
        "Science", "Technology", "Travel"};
        String[] null_ = new String[5];

        Assert.assertEquals(utils.getNewDesk(full),
                "Books Health Movies Science Technology Travel");
        Assert.assertEquals(utils.getNewDesk(empty), "");
        Assert.assertEquals(utils.getNewDesk(null_), "");
    }

    @Test
    public void testNewDeskMethod() throws Exception {
        Utils utils = new Utils();
        String[] error = new String[6];
        error[0] = "Culture";

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < error.length; i++) {
            if (error[i] != null) {
                if (i > 0 && ((!error[i].isEmpty())))
                    res.append(" ");
                res.append(error[i]);
            }
        }

        String s = res.toString().isEmpty() ? "" : res.toString();

        Assert.assertEquals("Culture", s);
        Assert.assertEquals("Culture", utils.getNewDesk(error));
    }
}
