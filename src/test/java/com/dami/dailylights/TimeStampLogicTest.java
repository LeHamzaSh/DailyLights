package com.dami.dailylights;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TimeStampLogicTest {

    @Test
    public void testTheLogic(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<Integer, Boolean> Day1 = new HashMap<>();
        Map<Integer, Boolean> Day2 = new HashMap<>();
        Map<Integer, Boolean> Day3 = new HashMap<>();
        Map<Integer, Boolean> ExpectedResult = new HashMap<>();

        Integer t1 = 3;
        Integer t2 = 28;
        Integer t3 = 29;
        Integer t4 = 31;

        Day1.put(t1, true);
        Day1.put(t2, true);
        Day1.put(t3, true);
        Day1.put(t4, true);

        Integer t5 = 3;
        Integer t6 = 10;
        Integer t7 = 11;
        Integer t8 = 12;
        Integer t9 = 13;

        Day2.put(t5, true);
        Day2.put(t6, true);
        Day2.put(t7, true);
        Day2.put(t8, true);
        Day2.put(t9, true);

        Integer t10 = 01;
        Integer t11 = 9;
        Integer t12 = 21;

        Day3.put(t10, true);
        Day3.put(t11, true);
        Day3.put(t12, true);

        Integer Highest = 31;
        Integer Lowest = 03;

        ExpectedResult.put(Highest, false);
        ExpectedResult.put(Lowest, true);

        Map<Integer, Boolean> actualresult = logic.dataProcess(Day1, Day2, Day3);

        assertEquals(ExpectedResult, actualresult);

    }

    @Test
    public void testTheLogic_whenDay1HasValueOnlyAndOthersAreEmpty(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<Integer, Boolean> Day1 = new HashMap<>();
        Map<Integer, Boolean> Day2 = new HashMap<>();
        Map<Integer, Boolean> Day3 = new HashMap<>();
        Map<Integer, Boolean> ExpectedResult = new HashMap<>();

        Day1.put(10, true);
        Day1.put(23, true);
        Day1.put(53, true);

        ExpectedResult.put(0, false);

        Map<Integer, Boolean> actualresult = logic.dataProcess(Day1, Day2, Day3);

        assertEquals(ExpectedResult, actualresult);
    }

    @Test
    public void testTheLogic_whenDay2HasValueOnlyAndOthersAreEmpty(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<Integer, Boolean> Day2 = new HashMap<>();
        Map<Integer, Boolean> Day1 = new HashMap<>();
        Map<Integer, Boolean> Day3 = new HashMap<>();
        Map<Integer, Boolean> ExpectedResult = new HashMap<>();

        Day2.put(10, true);
        Day2.put(23, true);
        Day2.put(53, true);

        ExpectedResult.put(0, false);

        Map<Integer, Boolean> actualresult = logic.dataProcess(Day2, Day1, Day3);

        assertEquals(ExpectedResult, actualresult);
    }

    @Test
    public void testTheLogic_whenDay3HasValueOnlyAndOthersAreEmpty(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<Integer, Boolean> Day3 = new HashMap<>();
        Map<Integer, Boolean> Day1 = new HashMap<>();
        Map<Integer, Boolean> Day2 = new HashMap<>();
        Map<Integer, Boolean> ExpectedResult = new HashMap<>();

        Day3.put(10, true);
        Day3.put(23, true);
        Day3.put(53, true);

        ExpectedResult.put(0, false);

        Map<Integer, Boolean> actualresult = logic.dataProcess(Day3, Day1, Day2);

        assertEquals(ExpectedResult, actualresult);
    }

    @Test
    public void testTheLogic_whenAllDaysAreEmpty(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<Integer, Boolean> Day1 = new HashMap<>();
        Map<Integer, Boolean> Day2 = new HashMap<>();
        Map<Integer, Boolean> Day3 = new HashMap<>();
        Map<Integer, Boolean> ExpectedResult = new HashMap<>();

        ExpectedResult.put(0, false);

        Map<Integer, Boolean> actualresult = logic.dataProcess(Day1, Day2, Day3);

        assertEquals(ExpectedResult, actualresult);

    }

    @Test
    public void testTheLogic_changeOrder(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<Integer, Boolean> Day2 = new HashMap<>();
        Map<Integer, Boolean> Day1 = new HashMap<>();
        Map<Integer, Boolean> Day3 = new HashMap<>();
        Map<Integer, Boolean> ExpectedResult = new HashMap<>();

        Integer t1 = 3;
        Integer t2 = 28;
        Integer t3 = 29;
        Integer t4 = 31;

        Day2.put(t1, true);
        Day2.put(t2, true);
        Day2.put(t3, true);
        Day2.put(t4, true);

        Integer t5 = 3;
        Integer t6 = 10;
        Integer t7 = 11;
        Integer t8 = 12;
        Integer t9 = 13;

        Day1.put(t5, true);
        Day1.put(t6, true);
        Day1.put(t7, true);
        Day1.put(t8, true);
        Day1.put(t9, true);

        Integer t10 = 01;
        Integer t11 = 9;
        Integer t12 = 21;

        Day3.put(t10, true);
        Day3.put(t11, true);
        Day3.put(t12, true);

        Integer Highest = 31;
        Integer Lowest = 03;

        ExpectedResult.put(Highest, false);
        ExpectedResult.put(Lowest, true);

        Map<Integer, Boolean> actualresult = logic.dataProcess(Day2, Day1, Day3);

        assertEquals(ExpectedResult, actualresult);

    }

    @Test
    public void testTheLogic_whenOnly2DaysHaveValues(){
        TimeStampLogic logic = new TimeStampLogic();

        Map<Integer, Boolean> Day2 = new HashMap<>();
        Map<Integer, Boolean> Day1 = new HashMap<>();
        Map<Integer, Boolean> Day3 = new HashMap<>();
        Map<Integer, Boolean> ExpectedResult = new HashMap<>();

        Integer t1 = 3;
        Integer t2 = 28;
        Integer t3 = 29;
        Integer t4 = 31;

        Day2.put(t1, true);
        Day2.put(t2, true);
        Day2.put(t3, true);
        Day2.put(t4, true);

        Integer t5 = 3;
        Integer t6 = 10;
        Integer t7 = 11;
        Integer t8 = 12;
        Integer t9 = 13;

        Day1.put(t5, true);
        Day1.put(t6, true);
        Day1.put(t7, true);
        Day1.put(t8, true);
        Day1.put(t9, true);

        Integer Highest = 31;
        Integer Lowest = 3;

        ExpectedResult.put(Highest, false);
        ExpectedResult.put(Lowest, true);

        Map<Integer, Boolean> actualresult = logic.dataProcess(Day2, Day1, Day3);

        assertEquals(ExpectedResult, actualresult);

    }


}