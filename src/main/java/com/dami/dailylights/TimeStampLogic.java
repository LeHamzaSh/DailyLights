package com.dami.dailylights;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimeStampLogic {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Map<Integer, Boolean> dataProcess(Map<Integer, Boolean> day1, Map<Integer, Boolean> day2, Map<Integer, Boolean> day3){

        Map<Integer, Boolean> Result = new HashMap<>();

        /* IF ALL DAYS ARE EMPTY THEN RETURN RESULT WITH ZERO */
        if (day1.isEmpty() && day2.isEmpty() && day3.isEmpty()){
            return getDefaultResult(Result);
        }

        /* IF VALUE FOR ONE DAY ONLY */
        if (day1.isEmpty() && day2.isEmpty() || day2.isEmpty() && day3.isEmpty() || day1.isEmpty() && day3.isEmpty()) {
            return getDefaultResult(Result);
        }

        /*CONDITIONS FOR DAY 1*/
        if((day1.size() > day2.size()) && (day1.size() > day3.size())){
            Integer maxDay1 = Collections.max(day1.keySet());
            Integer minDay1 = Collections.min(day1.keySet());

            updateResultComparingMinAndMax(day2, day3, Result, maxDay1, minDay1);
        }

        /* CONDITIONS FOR DAY 2 */
        //if day 2 is greater than day 1 and day 3
        else if((day2.size() > day1.size()) && (day2.size() > day3.size())){
            Integer maxDay2 = Collections.max(day2.keySet());
            Integer minDay2 = Collections.min(day2.keySet());

            updateResultComparingMinAndMax(day1, day3, Result, maxDay2, minDay2);

        }
        /*CONDITIONS FOR DAY 3 */
        else {
            Integer maxDay3 = Collections.max(day3.keySet());
            Integer minDay3 = Collections.min(day3.keySet());

            updateResultComparingMinAndMax(day1, day2, Result, maxDay3, minDay3);

        }

        return Result;
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
