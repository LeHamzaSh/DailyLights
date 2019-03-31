package com.dami.dailylights;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimeStampLogic {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Map<LocalDateTime, Boolean> dataProcess(Map<LocalDateTime, Boolean> day1, Map<LocalDateTime, Boolean> day2, Map<LocalDateTime, Boolean> day3){

        Map<LocalDateTime, Boolean> Result = new HashMap<>();

        /*CONDITIONS FOR DAY 1*/
        if((day1.size() > day2.size()) && (day1.size() > day3.size())){
            LocalDateTime maxDay1 = Collections.max(day1.keySet());
            LocalDateTime minDay1 = Collections.min(day1.keySet());

            updateResultComparingMinAndMax(day2, day3, Result, maxDay1, minDay1);
        }

        /* CONDITIONS FOR DAY 2 */
        //if day 2 is greater than day 1 and day 3
        else if((day2.size() > day1.size()) && (day2.size() > day3.size())){
            LocalDateTime maxDay2 = Collections.max(day2.keySet());
            LocalDateTime minDay2 = Collections.min(day2.keySet());

            updateResultComparingMinAndMax(day1, day3, Result, maxDay2, minDay2);

        }
        /*CONDITIONS FOR DAY 3 */
        else if((day3.size() > day1.size()) && (day3.size() > day2.size())){
            LocalDateTime maxDay3 = Collections.max(day3.keySet());
            LocalDateTime minDay3 = Collections.min(day3.keySet());

            updateResultComparingMinAndMax(day1, day2, Result, maxDay3, minDay3);

        }

        return Result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateResultComparingMinAndMax(Map<LocalDateTime, Boolean> dayA, Map<LocalDateTime, Boolean> dayB, Map<LocalDateTime, Boolean> result, LocalDateTime maxDay2, LocalDateTime minDay2) {
        //if day1 is greater than day 3
        if (dayA.size() > dayB.size()){
            LocalDateTime maxDay1 = Collections.max(dayA.keySet());
            LocalDateTime minDay1 = Collections.min(dayA.keySet());

            //if max time day 1 is greater than max time day 2
            if(maxDay1.isAfter(maxDay2)){
                result.put(maxDay1,false);
            }

            //if max time day 2 is greater than max time day 1
            else{
                result.put(maxDay2,false);
            }

            if(minDay1.isBefore(minDay2)){
                result.put(minDay1,true);
            }
            else{
                result.put(minDay2,true);
            }
         //if day 3 is greater than day 2
        } else {
            LocalDateTime maxDay3 = Collections.max(dayB.keySet());
            LocalDateTime minDay3 = Collections.min(dayB.keySet());

        }
    }
}
