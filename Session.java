import java.io.*;
import java.util.ArrayList;

/**
 * Each session represents an hour block of time at a specific store.
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 *
 * @version 8/2/2022
 */
public class Session implements Serializable {
    private int hour;  //The hour of day that the session occurs at. For example, 13 is the 13:00 to 14:00
    //session.
    private int day; //The day of the month that the session occurs at.
    private int month;  //The month of the year that the session occurs on. 1 for January, and so on.
    private int year;  //The year that the session occurs on.
    private String store;  //The store that the session is associated with.
    private ArrayList<String> enrolledCustomers;  //An array list of customers who are enrolled
    //in this session.
    private ArrayList<String> waitingCustomers;  //An array list of customers who are waiting to be
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

    public ArrayList<String> getEnrolledCustomers() {
        return enrolledCustomers;
    }

    public ArrayList<String> getWaitingCustomers() {
        return waitingCustomers;
    }

    public void addToWaitingList(String customer) {
        waitingCustomers.add(customer);
    }

    public void addToEnrolledList(String customer) {
        enrolledCustomers.add(customer);
        //add customer to enrolled list for store
    }

    public void removeFromWaitingList(String customer) {
        waitingCustomers.remove(customer);
    }

    public void removeFromEnrolledList(String customer) {
        enrolledCustomers.remove(customer);
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getStore() {
        return store;
    }

    public String toString() {
        String ans = "Year: " + year + "\n";
        ans += "Month: " + month + "\n";
        ans += "Day: " + day + "\n";
        ans += "Hour: " + hour + ":00" + "\n";
        return ans;
    }
}
