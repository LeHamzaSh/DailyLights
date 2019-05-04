package com.dami.dailylights;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  TimeStampLogic {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Map<Integer, Boolean> dataProcess(List<Map<Integer, Boolean>> days){


        Map<Integer, Boolean> Result = new HashMap<>();


        /* IF ALL DAYS ARE EMPTY THEN RETURN RESULT WITH ZERO */
        if (days.size() == 0 || howManyDaysHaveValues(days) == 0) {
            return getDefaultResult(Result);
        }

        /* IF VALUE FOR ONE DAY ONLY */
        if (howManyDaysHaveValues(days) == 1 ) {
            return getDefaultResult(Result);
        }

        List<Map<Integer, Boolean>> threeHighestDays = chooseThreeHighestDays(days);

        Integer highestDayMax = Collections.max(threeHighestDays.get(0).keySet());
        Integer highestDayMin = Collections.min(threeHighestDays.get(0).keySet());
        Log.d("HAMZA_APP", "Highest Day Max: " + highestDayMax);
        Log.d("HAMZA_APP", "Highest Day Min: " + highestDayMin);

        updateResultComparingMinAndMax(threeHighestDays.get(1), threeHighestDays.get(2), Result, highestDayMax, highestDayMin);

        return Result;

    }

    private int howManyDaysHaveValues(List<Map<Integer, Boolean>> days) {

        int result = 0;

        for (Map<Integer, Boolean> day: days){
            if (day.size() > 0) result++;
        }

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Map<Integer, Boolean>> chooseThreeHighestDays(List<Map<Integer, Boolean>> days) {

        Comparator<Map<Integer, Boolean>> mapComparator = new Comparator<Map<Integer, Boolean>>() {
            @Override
            public int compare(Map<Integer, Boolean> t1, Map<Integer, Boolean> t2) {

                return t2.size() - t1.size();
            }
        };

        days.sort(mapComparator);

        return Arrays.asList(days.get(0), days.get(1), days.get(2));
    }

    private Map<Integer, Boolean> getDefaultResult(Map<Integer, Boolean> result) {
        result.put(0, false);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateResultComparingMinAndMax(Map<Integer, Boolean> dayA, Map<Integer, Boolean> dayB, Map<Integer, Boolean> result, Integer maxDay2, Integer minDay2) {
        //if day1 is greater than day 3
        if (dayA.size() > dayB.size()){
            Integer maxDay1 = Collections.max(dayA.keySet());
            Integer minDay1 = Collections.min(dayA.keySet());

            //if max time day 1 is greater than max time day 2
            if(maxDay1 > (maxDay2)){
                result.put(maxDay1,false);
            }

            //if max time day 2 is greater than max time day 1
            else{
                result.put(maxDay2,false);
            }

            if(minDay1<(minDay2)){
                result.put(minDay1,true);
            }
            else{
                result.put(minDay2,true);
            }
            //if day 3 is greater than day 2
        } else {
            Integer maxDay3 = Collections.max(dayB.keySet());
            Integer minDay3 = Collections.min(dayB.keySet());

        }
    }
}
