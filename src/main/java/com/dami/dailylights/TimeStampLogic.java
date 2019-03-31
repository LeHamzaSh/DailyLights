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

            if(day2.size() > day3.size()){

            }
        }

        /* CONDITIONS FOR DAY 2 */
        //if day 2 is greater than day 1 and day 3
        else if((day2.size() > day1.size()) && (day2.size() > day3.size())){
            LocalDateTime maxDay2 = Collections.max(day2.keySet());
            LocalDateTime minDay2 = Collections.min(day2.keySet());

            //if day1 is greater than day 3
            if (day1.size() > day3.size()){
                LocalDateTime maxDay1 = Collections.max(day1.keySet());
                LocalDateTime minDay1 = Collections.min(day1.keySet());

                //if max time day 1 is greater than max time day 2
                if(maxDay1.isAfter(maxDay2)){
                    Result.put(maxDay1,false);
                }

                //if max time day 2 is greater than max time day 1
                else{
                    Result.put(maxDay2,false);
                }

                if(minDay1.isBefore(minDay2)){
                    Result.put(minDay1,true);
                }
                else{
                    Result.put(minDay2,true);
                }
             //if day 3 is greater than day 2
            } else {
                LocalDateTime maxDay3 = Collections.max(day3.keySet());
                LocalDateTime minDay3 = Collections.min(day3.keySet());

            }
        }
        /*CONDITIONS FOR DAY 3 */
        else if((day3.size() > day1.size()) && (day3.size() > day2.size())){
            LocalDateTime maxDay3 = Collections.max(day3.keySet());
            LocalDateTime minDay3 = Collections.min(day3.keySet());
            Result.put(maxDay3,true);
            Result.put(minDay3,true);
        }

        return Result;
    }
}
