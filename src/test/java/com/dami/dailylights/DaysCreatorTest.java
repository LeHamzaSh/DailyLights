package com.dami.dailylights;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DaysCreatorTest {

    @Test
    public void test_create_days(){
        DaysCreator creator = new DaysCreator();

        List<Map<Integer, Boolean>> daysList = creator.createDays(3);

        assertEquals(3, daysList.size());
    }

    @Test
    public void test_distribute_events(){
        DaysCreator creator = new DaysCreator();

        List<Map<Integer, Boolean>> daysList = creator.createDays(3);

        List<Integer> events = new ArrayList<>();

        events.add(19);
        events.add(30);
        events.add(45);

        List<Map<Integer, Boolean>> result = creator.distributeEvents(daysList, 1, events);

        assertEquals(1, daysList.get(0).size());
        assertEquals(0, daysList.get(1).size());
        assertEquals(0, daysList.get(2).size());
    }


    @Test
    public void test_distribute_events_9_EVENTS_3_days(){
        DaysCreator creator = new DaysCreator();

        int distributeSize = 3;

        List<Map<Integer, Boolean>> daysList = creator.createDays(distributeSize);

        List<Integer> events = new ArrayList<>();

        events.add(19);
        events.add(20);
        events.add(45);

        events.add(31);
        events.add(30);
        events.add(45);

        events.add(19);
        events.add(30);
        events.add(45);

        List<Map<Integer, Boolean>> result = creator.distributeEvents(daysList, distributeSize, events);

        assertEquals(2, daysList.get(0).size());
        assertEquals(0, daysList.get(1).size());
        assertEquals(1, daysList.get(2).size());
    }

    @Test
    public void test_distribute_events_5_EVENTS_2_days(){
        DaysCreator creator = new DaysCreator();

        int distributeSize = 2;

        List<Map<Integer, Boolean>> daysList = creator.createDays(distributeSize);

        List<Integer> events = new ArrayList<>();

        events.add(19);
        events.add(20);
        events.add(45);

        events.add(20);
        events.add(19);


        List<Map<Integer, Boolean>> result = creator.distributeEvents(daysList, distributeSize, events);

        assertEquals(2, daysList.get(0).size());
        assertEquals(1, daysList.get(1).size());
    }


}