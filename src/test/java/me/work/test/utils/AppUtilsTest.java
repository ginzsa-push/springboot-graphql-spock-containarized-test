package me.work.test.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.spockframework.util.Assert.notNull;

public class AppUtilsTest {
    @Test
    public void testMinimalDateFormat() {

        String date = AppUtils.getNDaysAgoDate(1);
        notNull(date);
        String[] elements = date.split("-");
        assertTrue(elements.length == 3);
        assertTrue(elements[0].length() == 4);
        assertTrue(elements[1].length() == 2);
        assertTrue(elements[2].length() == 2);
    }
}
