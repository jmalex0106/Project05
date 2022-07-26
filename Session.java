import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Session
 * <p>
 * Each session represents a one-hour block of time that can be reserved or not reserved by a student.
 */
public class Session {
    private String name;  //The name of the person reserving this session. Is "" if the session is
    // unvalaible or if no one has reserved the session. This is set by the student, not the tutor.
    private String location;  //The location that the session will be held at. This is set by the tutor,
    //not the student. If the session is not available, the location is always "".
    private int capacity;  //Capacity as set by the tutor. A capacity of 0 is an unavailable session.
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

    public Session(int hour, int day, int month, int year, String store) {
        this.name = "";
        this.location = "";
        this.capacity = 0;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
        this.store = store;
        this.enrolledCustomers = new ArrayList<String>();
        this.waitingCustomers = new ArrayList<String>();
    }

    public boolean checkOpenSession() {
        if (!name.equals("") || capacity == 0) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public String getEmailFromFile() {
        String ans = "";
        File f = new File("studentInfo.txt");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            for (int i = 0; i < list.size(); i++) {
                String[] tmpArr = list.get(i).split(",");
                ans = tmpArr[2];
                return ans;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;
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

    public boolean isValidTutor(String userName, String passWord) {
        try {
            File f = new File("tutorInfo.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();

            String[] userPassArr = list.toArray(new String[list.size()]);

            for (int i = 0; i < userPassArr.length; i++) {
                String[] tmpArr = userPassArr[i].split(",");
                String user = userPassArr[0];
                String pass = userPassArr[1];
                if (user.equals(userName) && pass.equals(passWord)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isValidStudent(String userName, String passWord) {
        try {
            File f = new File("studentInfo.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();

            String[] userPassArr = list.toArray(new String[list.size()]);

            for (int i = 0; i < userPassArr.length; i++) {
                String[] tmpArr = userPassArr[i].split(",");
                String user = userPassArr[0];
                String pass = userPassArr[1];
                if (user.equals(userName) && pass.equals(passWord)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
        String ans = "Year: " + getYear() + "\n";
        ans += "Month: " + getMonth() + "\n";
        ans += "Day: " + getDay() + "\n";
        ans += "Hour: " + getHour() + ":00" + "\n";
        ans += "Capacity: " + getCapacity() + "\n";
        ans += "Location: " + getLocation() + "\n";
        return ans;
    }
}
