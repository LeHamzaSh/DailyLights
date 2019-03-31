package com.dami.dailylights;

import org.junit.Test;
import org.junit.runner.manipulation.Sorter;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class TimeStampLogicTest {

    @Test
    public void testTheLogic(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<LocalDateTime, Boolean> Day1 = new HashMap<>();
        Map<LocalDateTime, Boolean> Day2 = new HashMap<>();
        Map<LocalDateTime, Boolean> Day3 = new HashMap<>();
        Map<LocalDateTime, Boolean> ExpectedResult = new HashMap<>();

        LocalDateTime t1 = LocalDateTime.of(2019, 3, 30, 10, 03);
        LocalDateTime t2 = LocalDateTime.of(2019, 3, 30, 10, 28);
        LocalDateTime t3 = LocalDateTime.of(2019, 3, 30, 10, 29);
        LocalDateTime t4 = LocalDateTime.of(2019, 3, 30, 10, 31);

        Day1.put(t1, true);
        Day1.put(t2, true);
        Day1.put(t3, true);
        Day1.put(t4, false);

        LocalDateTime t5 = LocalDateTime.of(2019, 3, 30, 10, 03);
        LocalDateTime t6 = LocalDateTime.of(2019, 3, 30, 10, 10);
        LocalDateTime t7 = LocalDateTime.of(2019, 3, 30, 10, 11);
        LocalDateTime t8 = LocalDateTime.of(2019, 3, 30, 10, 12);
        LocalDateTime t9 = LocalDateTime.of(2019, 3, 30, 10, 13);

        Day2.put(t5, true);
        Day2.put(t6, true);
        Day2.put(t7, true);
        Day2.put(t8, true);
        Day2.put(t9, true);

        LocalDateTime t10 = LocalDateTime.of(2019, 3, 30, 10, 01);
        LocalDateTime t11 = LocalDateTime.of(2019, 3, 30, 10, 9);
        LocalDateTime t12 = LocalDateTime.of(2019, 3, 30, 10, 21);

        Day3.put(t10, true);
        Day3.put(t11, true);
        Day3.put(t12, true);

        LocalDateTime Highest = LocalDateTime.of(2019,3,30,10, 31);
        LocalDateTime Lowest = LocalDateTime.of(2019,3,30,10, 03);

        ExpectedResult.put(Highest, false);
        ExpectedResult.put(Lowest, true);

        Map<LocalDateTime, Boolean> actualresult = logic.dataProcess(Day1, Day2, Day3);

        assertEquals(ExpectedResult, actualresult);


    }


}