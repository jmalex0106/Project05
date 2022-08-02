import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Each store represents a list of sessions waiting to be approved, a list that is approved, and
 * a name and owner.
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version 8/2/2022
 */

public class Store implements Serializable {
    private static final String[] DAY_NAMES = new String[]{"Sunday", "Monday",
            "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final int[] DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in normal years
    private static final int[] LEAP_DAYS = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in leap years
    private String name;  //The name of the store
    private String seller;  //The name of the seller that owns this store
    private boolean[] isOpen;  //A length 7 array. Position 0 = Sunday, 6 = Saturday. A value
    //of true means that the store will open on that day of the week, every week. A value of false
    //means that the store is closed that day of the week, every week.
    private int[] openingTimes;  //A length n array, where 0 < n <= 7 is the number of days in a
    //week that the store is open. An opening time of 11 means that this store begins its first session
    //at 11:00.
    private int[] closingTimes;  //A length n array, where 0 < n <= 7 is the number of days in a
    //week that the store is open. A closing time of 15 means that this store ends its last session
    //at 15:00
    private int[] capacities;  //A length n array, where 0 < n <= 7 is the number of days in a
    //week that the store is open. Capacity is the number of students each session can hold.
    private String[] locations;  //A length n array, where 0 < n <= 7 is the number of days in a
    //week that the store is open. Location of the sessions on that day of the week.
    private Set<String> uniqueCustomers;  //A set of unique customers. Sets are used because they
    //cannot have duplicate elements.
    private ArrayList<Session> sessions;  //All of the sessions at this store that have at least
    //one customer in the waiting list OR in the approved list.

    /**
     * A basic constructor for the store object. Sets the name of the store and the seller name,
     * and sets all the sessions to blank sessions.
     *
     * @param name   - the name of this store.
     * @param seller - the name of the owner of this store.
     */
    public Store(String name, String seller) {
        this.name = name;
        this.seller = seller;
        this.isOpen = new boolean[7];
        this.openingTimes = null;
        this.closingTimes = null;
        this.capacities = null;
        this.locations = null;
        this.uniqueCustomers = new HashSet<String>();
        this.sessions = new ArrayList<Session>();
    }

    public String getName() {
        return name;
    }

    public String getSeller() {
        return seller;
    }

    public boolean[] getIsOpen() {
        return isOpen;
    }

    public int[] getCapacities() {
        return capacities;
    }

    public int[] getClosingTimes() {
        return closingTimes;
    }

    public int[] getOpeningTimes() {
        return openingTimes;
    }

    public String[] getLocations() {
        return locations;
    }

    /**
     * Checks the fields of a store to make sure they are valid. If they are valid, it sets the
     * fields of the store object and returns true. If any are not valid, it returns false and does
     * nothing. This method is never called directly, but only from other methods in Store.
     *
     * @return
     */
    public boolean setupStoreInputChecks(boolean[] isOpenCheck, int[] openingTimesCheck,
                                         int[] closingTimesCheck,
                                         int[] capacitiesCheck, String[] locationsCheck) {
        if (isOpenCheck.length != 7) {
            return false;
        }
        int daysOpen = 0;
        for (int i = 0; i < isOpenCheck.length; i++) {
            if (isOpenCheck[i]) {
                daysOpen++;
            }
        }
        if (daysOpen == 0) {
            return false;
        }
        if (openingTimesCheck.length != daysOpen) {
            return false;
        }
        if (closingTimesCheck.length != daysOpen) {
            return false;
        }
        if (locationsCheck.length != daysOpen) {
            return false;
        }
        if (capacitiesCheck.length != daysOpen) {
            return false;
        }
        int t = 0;
        for (int i = 0; i < daysOpen; i++) {
            if (isOpenCheck[i]) {
                if (openingTimesCheck[t] < 0) {
                    return false;
                }
                if (openingTimesCheck[t] > 23) {
                    return false;
                }
                if (closingTimesCheck[t] < 0) {
                    return false;
                }
                if (closingTimesCheck[t] > 23) {
                    return false;
                }
                if (openingTimesCheck[t] >= closingTimesCheck[t]) {
                    return false;
                }
                if (capacitiesCheck[t] <= 0) {
                    return false;
                }
                t++;
            }
        }
        this.isOpen = isOpenCheck;
        this.openingTimes = openingTimesCheck;
        this.closingTimes = closingTimesCheck;
        this.capacities = capacitiesCheck;
        this.locations = locationsCheck;
        return true;
    }

    /**
     * Sets up a store. This method is ONLY to be called when a brand-new store has been
     * created for the first time (when a tutor makes a new store for the first time)
     */
    public boolean setupStore() {
        //Checks if inputs are valid
        if (!setupStoreInputChecks(isOpen, openingTimes, closingTimes,
                capacities, locations)) {
            return false;
        }
        try {
            //adds store as a line to AllStores.txt, which lists all existing stores
            File file = new File("AllStores.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = getName();
            add += ",";
            add += getSeller();
            printWriter.println(add);
            printWriter.close();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Makes the file associated with this store from the store object. This method needs to be
     * called on the server side to save changes made to a store object.
     * TODO-figure out how this works and provide better docs
     */
    public boolean makeFileFromStore() {
        try {
            File file = new File (name + "Store.txt");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return number of unique customers for this store
     */
    public int numberOfCustomers() {
        return uniqueCustomers.size();
    }

    public void remakeStoreFromFile() {
       try {
           File file = new File(name + "Store.txt");
           FileInputStream fileInputStream = new FileInputStream(file);
           ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
           Object storeObject = objectInputStream.readObject();
           if (storeObject instanceof Store) {
               System.out.println("STORE OBJECT");
               Store store = (Store) storeObject;
               this.sessions = store.getSessions();
               this.uniqueCustomers = store.getUniqueCustomers();
               this.isOpen = store.getIsOpen();
               this.openingTimes = store.getOpeningTimes();
               this.closingTimes = store.getClosingTimes();
               this.locations = store.getLocations();
               this.seller = store.getSeller();
               this.name = store.getName();
           }
           objectInputStream.close();
       } catch (Exception exception) {
           exception.printStackTrace();
       }
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setUniqueCustomers(Set<String> uniqueCustomers) {
        this.uniqueCustomers = uniqueCustomers;
    }

    public Set<String> getUniqueCustomers() {
        return uniqueCustomers;
    }

    /**
     * Checks if the store is open at the particular time entered. Note that invalid dates or
     * dates in the past will still be processed with this method. When calling this method on
     * the server side, you must first call checkIfDateIsValidAndFuture.
     *
     * @param year
     * @param day
     * @param month
     * @param day
     * @param hour
     * @return
     */
    public boolean checkIfStoreIsOpen(int year, int month, int day, int hour) {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month + 1);
        calender.set(Calendar.DAY_OF_MONTH, day + 1);
        calender.set(Calendar.HOUR, hour);
        //Checks if the store is open on this particular day
        //TODO delete test below
        for (int i = 0; i < 7; i++) {
            System.out.println(i + " OPEN " + isOpen[i]);
        }
        //TODO delete test above
        if (!isOpen[calender.get(Calendar.DAY_OF_WEEK) - 1]) {
            System.out.println("CLOSED DAY");
            System.out.println("DAY IS " + calender.get(Calendar.DAY_OF_WEEK));
            return false;
        }
        //Gets the index of the day n in the arrays openingTimes, closingTimes, capacities, and
        //locations
        int index = 0;
        for (int i = 0; i < calender.get(Calendar.DAY_OF_WEEK) - 1; i++) {
            if (isOpen[i]) {
                index++;
            }
        }
        //Checks if session time is within the open hours of the store
        if (openingTimes[index] > calender.get(Calendar.HOUR)) {
            System.out.println("CLOSED HOUR");
            return false;
        }
        return !(closingTimes[index] <= calender.get(Calendar.HOUR));
    }

    /**
     * Returns true if there already exists a session object in this store (in the field sessions) at the entered
     * year, month, day, and hour. This method is never called directly, but is called in other methods
     * that are used when a customer applies for an appointment so that there can never be two
     * session objects with the same time at the same store.
     */
    public boolean checkIfSessionAtTimeAlreadyExists(int year, int month, int day, int hour) {
        return !(sessionAtSpecifiedTime(year, month, day, hour) == null);
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return session at the specified time, if it exists. If it does not exist, return null.
     */
    public Session sessionAtSpecifiedTime(int year, int month, int day, int hour) {
        for (Session session : sessions) {
            if (session.getYear() == year &&
                    session.getMonth() == month &&
                    session.getDay() == day &&
                    session.getHour() == hour) {
                return session;
            }
        }
        return null;
    }

    /**
     * Creates a new session if a session at the appropriate time does not exist.
     * Adds customer to the waitlist at this session. This method can be treated as skeleton code
     * for similar methods like approveAppointment, denyAppointment, etc.
     * NOTE: This method does not update any fields in any customer object. To do so,
     * other methods must be called from the Customer class.
     */
    public void requestAppointmentAtTime(int year, int month, int day, int hour,
                                         String customerName) {
        System.out.println("RUNNING");
        if (!checkIfSessionAtTimeAlreadyExists(year, month, day, hour)) {
            Session session = new Session(year, month, day, hour, name);
            session.addToWaitingList(customerName);
            sessions.add(session);
        } else {
            for (Session session : sessions) {
                if (session.getYear() == year &&
                        session.getMonth() == month &&
                        session.getDay() == day &&
                        session.getHour() == hour) {
                    session.addToWaitingList(customerName);
                }
            }
        }
        this.makeFileFromStore();
    }

    /**
     * Removes customer from waitlist at the specified session, if applicable, or does nothing.
     * This method updates the correct store and customer objects and saves them.
     */
    public void declineAppointmentAtTime(int year, int month, int day, int hour,
                                         Customer customer) {
        for (Session session : sessions) {
            if (session.getYear() == year &&
                    session.getMonth() == month &&
                    session.getDay() == day &&
                    session.getHour() == hour) {
                session.removeFromWaitingList(customer.getName());
            }
        }
    }

    /**
     * Searches for customerName in the waitling list of the session at this store specified by the
     * year, month, day, and hour parameters. If found, this customerName is moved from
     * waitingCustomers to enrolledCustomers and this customer's name is added to the field
     * uniqueCustomers in this store.
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param customerName
     */
    public void approveAppointmentAtTime(int year, int month, int day, int hour,
                                         String customerName) {
        try {
            for (Session session : sessions) {
                if (session.getYear() == year &&
                        session.getMonth() == month &&
                        session.getDay() == day &&
                        session.getHour() == hour) {
                    session.removeFromWaitingList(customerName);
                    session.addToEnrolledList(customerName);
                    uniqueCustomers.add(customerName);
                }
            }
        } catch (Exception exception) {
            return;
        }
    }

    /**
     * Deletes the customerName from the enrolledCustomers field of the specified session.
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param customerName
     */
    public void cancelAlreadyApprovedAppointment(int year, int month, int day, int hour,
                                                 String customerName) {
        try {
            for (Session session : sessions) {
                if (session.getYear() == year &&
                        session.getMonth() == month &&
                        session.getDay() == day &&
                        session.getHour() == hour) {
                    session.removeFromEnrolledList(customerName);
                }
            }
        } catch (Exception exception) {
            return;
        }
    }

    /**
     * Creates a string array representing the most popular day of the week at this store.
     * A day of the week is the most popular if it has strictly more approved customers on
     * that day than any other. If multiple days are tied for the top spot, then all of the
     * top days are added to the output string array. This string array is then converted into
     * a toString that tells about this store's popularity over the days of the week.
     */
    public String getMostPopularDaysOfWeekToString() {
        int[] dayCounters = new int[7];
        //sets day Counters all to 0
        for (int i = 0; i < dayCounters.length; i++) {
            dayCounters[i] = 0;
        }
        for (Session session : sessions) {
            dayCounters[LocalDate.of(session.getYear(), session.getMonth() + 1,
                    session.getDay()).getDayOfWeek().getValue()]
                    += session.getEnrolledCustomers().size();
        }
        //converts dayCounters into a list of the most popular days
        //Sets int maxCustomers to the max number of customers on a certain day of the week
        int maxCustomers = 0;
        for (int i = 0; i < dayCounters.length; i++) {
            if (maxCustomers < dayCounters[i]) {
                maxCustomers = dayCounters[i];
            }
        }
        //Sets int popularDaysTieCount to the number of days of the week that have
        //maxCustomers number of customers
        int popularDaysTieCount = 0;
        for (int i = 0; i < dayCounters.length; i++) {
            if (maxCustomers == dayCounters[i]) {
                popularDaysTieCount++;
            }
        }
        //Sets String array popularDays to the most popular days
        String[] popularDays = new String[popularDaysTieCount];
        int index = 0;
        for (int i = 0; i < dayCounters.length; i++) {
            if (maxCustomers == dayCounters[i]) {
                popularDays[index] = DAY_NAMES[i];
                index++;
            }
        }
        //Converts popularDays into a string to display to the user
        String output = "";
        if (popularDaysTieCount == 1) {
            output = "The most popular day of the week at this store is " +
                    popularDays[0] + "with " + maxCustomers + " total customers";
        } else if (popularDaysTieCount == isOpen.length) {
            output = "All days that this store are tied for most popular at " +
                    maxCustomers + " total customers.";
        } else {
            output = "The days ";
            for (int i = 0; i < popularDays.length; i++) {
                output += popularDays[i];
                if (i == popularDays.length - 2 && popularDays.length != 2) {
                    output += ", and ";
                } else if (i == popularDays.length - 2 && popularDays.length == 2) {
                    output += " and ";
                } else if (i != popularDays.length - 1) {
                    output += ", ";
                }
            }
            output += " are all tied for most popular day at this store with " +
                    maxCustomers + " total customers";
        }
        output += "\n";
        return output;
    }

    /**
     * Creates a statistics toString for this store
     *
     * @return
     */
    public String createStatisticsToString() {
        String output = "Your tutoring service " + name +
                "has " + uniqueCustomers.size() +
                "unique customers with approved appointments!\n" +
                getMostPopularDaysOfWeekToString();
        return output;
    }

    public String showOpenedTimes() {
        String out = "";
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                out += DAY_NAMES[i] + " from " +
                        openingTimes[index] + ":00 to " +
                        closingTimes[index] + ":00\n";
                index++;
            }
        }
        return out;
    }
    public void importFromCsv(File file) {
        try {
            File f = file;
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            this.isOpen = new boolean[7];


            for (int i = 0; i < list.size(); i++) {
                String[] tmpArr = list.get(i).split(",");
                isOpen[i] = Boolean.parseBoolean(tmpArr[1]);
            }

            int daysOpen = 0;
            for (int i = 0; i < isOpen.length; i++) {
                if (isOpen[i]) {
                    daysOpen++;
                }
            }

            ArrayList<Integer> idxs = new ArrayList<Integer>();

            for (int i = 0; i < list.size(); i++) {
                String[] tmpArr = list.get(i).split(",");
                if (Boolean.parseBoolean(tmpArr[1])) {
                    idxs.add(i);
                }
            }

            int[] openingTimesArr = new int[daysOpen];
            int[] closingTimesArr = new int[daysOpen];
            int[] capacitiesArr = new int[daysOpen];
            String[] locationsArr = new String[daysOpen];

            for (int i = 0; i < daysOpen; i++) {
                String[] arr = list.get(idxs.get(i)).split(",");
                openingTimesArr[i] = Integer.parseInt(arr[2]);
                closingTimesArr[i] = Integer.parseInt(arr[3]);
                capacitiesArr[i] = Integer.parseInt(arr[4]);
                locationsArr[i] = arr[5];
            }
            this.openingTimes = openingTimesArr;
            this.closingTimes = closingTimesArr;
            this.capacities = capacitiesArr;
            this.locations = locationsArr;


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

