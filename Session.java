import java.io.*;
import java.util.ArrayList;

/**
 * Each session represents an hour block of time at a specific store.
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version 8/2/2022
 */
public class Session implements Serializable {
    private final int hour;  //The hour of day that the session occurs at. For example, 13 is the 13:00 to 14:00
    //session.
    private final int day; //The day of the month that the session occurs at.
    private final int month;  //The month of the year that the session occurs on. 1 for January, and so on.
    private final int year;  //The year that the session occurs on.
    private final String store;  //The store that the session is associated with.
    private final ArrayList<String> enrolledCustomers;  //An array list of customers who are enrolled
    //in this session.
    private final ArrayList<String> waitingCustomers;  //An array list of customers who are waiting to be
    //approved for this session.

    private static final String SUCCESSFUL_RESERVATION = "Tutoring reservation made successfully for %s from %d:00" +
            " to %d:00 on %s %d%s, %d\n";
    private static final String SUCCESSFUL_WAITLIST = "Waitlist reservation made successfully for %s from %d:00" +
            " to %d:00 on %s %d%s, %d\n";

    /**
     * A basic constructor for a session object
     *
     * @param hour
     * @param day
     * @param month
     * @param year
     */
    public Session(int hour, int day, int month, int year, String store) {
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
        this.store = store;
        this.enrolledCustomers = new ArrayList<String>();
        this.waitingCustomers = new ArrayList<String>();
    }

    public synchronized ArrayList<String> getEnrolledCustomers() {
        return enrolledCustomers;
    }

    public synchronized ArrayList<String> getWaitingCustomers() {
        return waitingCustomers;
    }

    public synchronized void addToWaitingList(String customer) {
        waitingCustomers.add(customer);
    }

    public synchronized void addToEnrolledList(String customer) {
        enrolledCustomers.add(customer);
        //add customer to enrolled list for store
    }


    public synchronized void removeFromWaitingList(String customer) {
        waitingCustomers.remove(customer);
    }

    public synchronized void removeFromEnrolledList(String customer) {
        enrolledCustomers.remove(customer);
    }

    public synchronized int getDay() {
        return day;
    }

    public synchronized int getHour() {
        return hour;
    }

    public synchronized int getMonth() {
        return month;
    }

    public synchronized int getYear() {
        return year;
    }

    public synchronized String getStore() {
        return store;
    }

    public synchronized String toString() {
        String ans = "Year: " + year + "\n";
        ans += "Month: " + month + "\n";
        ans += "Day: " + day + "\n";
        ans += "Hour: " + hour + ":00" + "\n";
        return ans;
    }
}
