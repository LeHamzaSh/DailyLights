package com.dami.dailylights;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DaysCreator {
    public List<Map<Integer, Boolean>> createDays(int count) {

        List<Map<Integer, Boolean>> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            result.add(new HashMap<>());
        }

        return result;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Map<Integer, Boolean>> distributeEvents(List<Map<Integer, Boolean>> daysList, int distributeSize, List<Integer> events) {

        int i = 0;
        int dayNumber=1;
        for (Map<Integer, Boolean> day : daysList) {

            for (int j = 0; j < distributeSize; j++ ) {

                int e = events.get(i);

                if (e <= 20) {
                    day.put(e, true);
                    Log.d("HAMZA_APP", "Boolean True Time: " + dayNumber);
                }
                i++;

            }
            String message = "Filled day: " + dayNumber + "  values for day: " + day;
            System.out.println(message);
            Log.d("HAMZA_APP", message);

            dayNumber++;

        }

        return daysList;
    }

    public void clearDays(List<Map<Integer, Boolean>> generatedDays) {
        for (Map<Integer, Boolean> day : generatedDays) {
            day.clear();
        }
    }
}
