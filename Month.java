import java.util.Calendar;

public class Month {
    private int year;
    private int month;
    private Day[] daysOfMonth;
    private static final int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in normal years
    private static final int[] leapDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in leap years

    public Month(int month, int year, String store) {
        Day[] temp = new Day[numberOfDaysInMonth()];

        for (int i = 0; i < temp.length; i++) {
            temp[i] = new Day((i + 1), month, year, store);
        }
    }

    public int getMonth() {
        return month;
    }

    public int numberOfDaysInMonth() {
        boolean leapYear = false;
        if (year % 4 == 0) {
            leapYear = true;
        } else if (year % 100 == 0) {
            leapYear = false;
        } else if (year % 400 == 0) {
            leapYear = true;
        }
        int monthLength;
        if (leapYear) {
            monthLength = leapDays[month];
        } else {
            monthLength = days[month];
        }
        return monthLength;
    }

    public Day getDayAtInt(int day) {
        return this.daysOfMonth[day];
    }

    public int getFirstDay(int year, int month) { //search the first day of the month
        Calendar tmpCalendar = Calendar.getInstance(); //temporary Calendar object
        tmpCalendar.set(year, month - 1, 1); //set year, month as given and date as 1 to find the day
        int idx = tmpCalendar.get(Calendar.DAY_OF_WEEK) - 1; //get the index of the day and -1
        return idx;
    }
}