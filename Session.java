import java.io.*;
import java.util.ArrayList;
/**
 * Session
 * <p>
 * Each session represents a one-hour block of time.
 */
public class Session {
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

    private static final String SUCCESSFULRESERVATION = "Tutoring reservation made successfully for %s from %d:00" +
            " to %d:00 on %s %d%s, %d\n";
    private static final String SUCCESSFULWAITLIST = "Waitlist reservation made successfully for %s from %d:00" +
            " to %d:00 on %s %d%s, %d\n";

    /**
     * A basic constructor for a session object
     * @param hour
     * @param day
     * @param month
     * @param year
     */
    public Session(int hour, int day, int month, int year) {
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
        this.enrolledCustomers = new ArrayList<String>();
        this.waitingCustomers = new ArrayList<String>();
    }

    public ArrayList<String> getEnrolledCustomers() {
        return enrolledCustomers;
    }

    public ArrayList<String> getWaitingCustomers() {
        return waitingCustomers;
    }

    public void addToWaitingList(Customer customer) {
        waitingCustomers.add(customer.getName());
    }

    public void addToEnrolledList(Customer customer) {
        enrolledCustomers.add(customer.getName());
        //add customer to enrolled list for store
    }

    public void removeFromWaitingList(Customer customer) {
        waitingCustomers.remove(customer.getName());
    }

    public void removeFromEnrolledList(Customer customer) {
        enrolledCustomers.remove(customer.getName());
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
