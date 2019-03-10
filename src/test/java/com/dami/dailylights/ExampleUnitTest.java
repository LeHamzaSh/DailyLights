package com.dami.dailylights;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void how_a_map_works() throws InterruptedException {
        Map<LocalDateTime, Boolean> timeMap = new HashMap<>();

        // user mode
        LocalDateTime time1 = LocalDateTime.now();

        timeMap.put(time1, false);

        LocalDateTime time2 = LocalDateTime.parse("2019-03-10T13:58:00");

        timeMap.put(time2, true);

        // auto mode

        // check every 1 minute,
        // get the system time
        // get timeMap time
        // compare if they are equal, then send command

        LocalDateTime runningTime = LocalDateTime.now().plusMinutes(3);
        while (true) {

            System.out.println("Checking Time Now: " + LocalDateTime.now());

            int hour = LocalDateTime.now().getHour();
            int minute = LocalDateTime.now().getMinute();

            timeMap.forEach( (date, booleanState) -> {
                if (date.getHour() == hour && date.getMinute() == minute) {
                    System.out.println("Found Programmed Value Time: " + date);
                    System.out.println("Changing Light State to: " + booleanState);
                }
            });

            Thread.sleep(1 * 60 * 1000);

            if (LocalDateTime.now().isAfter(runningTime)) {
                break;
            }
        }


    }
}