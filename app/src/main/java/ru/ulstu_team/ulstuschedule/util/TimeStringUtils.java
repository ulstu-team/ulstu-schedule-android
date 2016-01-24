package ru.ulstu_team.ulstuschedule.util;

public class TimeStringUtils {

    private static String[] beginHours =
            new String[]{"8", "9", "11", "13", "14", "16", "18", "19"};
    private static String[] beginMinutes =
            new String[]{"00", "40", "30", "10", "50", "30", "10", "50"};
    private static String[] endHours =
            new String[] {"9", "11", "13", "14", "16", "18", "19", "21"};
    private static String[] endMinutes =
            new String[] {"30", "10", "00", "40", "20", "00", "40", "20"};

    public static String beginHour(int lessonNumber) {
        return beginHours[lessonNumber - 1];
    }

    public static String beginMinutes(int lessonNumber) {
        return beginMinutes[lessonNumber - 1];
    }

    public static String endHour(int lessonNumber) {
        return endHours[lessonNumber - 1];
    }

    public static String endMinutes(int lessonNumber) {
        return endMinutes[lessonNumber - 1];
    }
}
