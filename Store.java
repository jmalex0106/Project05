import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
public class Store {
    private static final int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in normal years
    private static final int[] leapDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in leap years
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
     * TODO - Provide documentation. I have no idea how this work. Fix maybe???
     */
    public void importFromCsv() {
        try {
            File f = new File(name + "Store.txt");
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

            this.openingTimes = new int[daysOpen];
            this.closingTimes = new int[daysOpen];
            this.capacities = new int[daysOpen];

            for (int i = 0; i < daysOpen; i++) {
                String[] arr = list.get(idxs.get(i)).split(",");
                openingTimes[i] = Integer.parseInt(arr[2]);
                closingTimes[i] = Integer.parseInt(arr[3]);
                capacities[i] = Integer.parseInt(arr[4]);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO - Fix maybe??? Add docs, I have no idea how this works
     */
    public int makeCsvFromTxt() {
        int ans = 0;
        try {
            File f = new File(name + "Store.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();

            int daysOpen = 0;
            boolean[] isOpen = new boolean[7];

            for (int i = 1; i < 8; i++) {
                if (!list.get(i).equals("false")) {
                    isOpen[i - 1] = true;
                } else {
                    isOpen[i - 1] = false;
                }
            }

            String[] weekdays = {"Sunday", "Monday", "Tuesday", "Wednesday",
                    "Thursday", "Friday", "Saturday"};

            int[] openingTimes = new int[7];
            int[] closingTimes = new int[7];
            int[] capacity = new int[7];
            String[] location = {"", "", "", "", "", "", "", ""};

            for (int i = 1; i < 8; i++) {
                for (int j = 0; j < isOpen.length; j++) {
                    if (!list.get(i).equals("false")) {
                        String[] tmpArr = list.get(i).split(",");
                        openingTimes[i - 1] = Integer.parseInt(tmpArr[1]);
                        closingTimes[i - 1] = Integer.parseInt(tmpArr[2]);
                        capacity[i - 1] = Integer.parseInt(tmpArr[3]);
                        location[i - 1] = tmpArr[4];
                    } else {
                        openingTimes[i - 1] = 0;
                        closingTimes[i - 1] = 0;
                        capacity[i - 1] = 0;
                        location[i - 1] = "";
                    }
                }
            }

            BufferedWriter fw = new BufferedWriter(new FileWriter(name + "Store.csv"));
            for (int i = 0; i < isOpen.length; i++) {
                fw.write(weekdays[i] + "," +
                        isOpen[i] + "," + openingTimes[i] + "," +
                        closingTimes[i] + "," + capacity[i] + "," + location[i]);
                fw.newLine();
                ans++;
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * Checks the fields of a store to make sure they are valid. If they are valid, it sets the
     * fields of the store object and returns true. If any are not valid, it returns false and does
     * nothing. This method is never called directly, but only from other methods in Store.
     *
     * @param isOpen
     * @param openingTimes
     * @param closingTimes
     * @param capacities
     * @param locations
     * @return
     */
    public boolean setupStoreInputChecks(boolean[] isOpen, int[] openingTimes,
                                         int[] closingTimes, int[] capacities, String[] locations) {
        if (isOpen.length != 7) {
            return false;
        }
        int daysOpen = 0;
        for (int i = 0; i < isOpen.length; i++) {
            if (isOpen[i]) {
                daysOpen++;
            }
        }
        if (daysOpen == 0) {
            return false;
        }
        if (openingTimes.length != daysOpen) {
            return false;
        }
        if (closingTimes.length != daysOpen) {
            return false;
        }
        if (locations.length != daysOpen) {
            return false;
        }
        if (capacities.length != daysOpen) {
            return false;
        }
        int t = 0;
        for (int i = 0; i < daysOpen; i++) {
            if (isOpen[i]) {
                if (openingTimes[t] < 0) {
                    return false;
                }
                if (openingTimes[t] > 23) {
                    return false;
                }
                if (closingTimes[t] < 0) {
                    return false;
                }
                if (closingTimes[t] > 23) {
                    return false;
                }
                if (openingTimes[t] >= closingTimes[t]) {
                    return false;
                }
                if (capacities[t] <= 0) {
                    return false;
                }
                t++;
            }
        }
        this.isOpen = isOpen;
        this.openingTimes = openingTimes;
        this.closingTimes = closingTimes;
        this.capacities = capacities;
        this.locations = locations;
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
            File file = new File(name + "Store.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = "";
            add += seller;
            add += "\n";
            int t = 0;
            for (int i = 0; i < 7; i++) {
                if (isOpen[i]) {
                    add += "true,";
                    add += openingTimes[t];
                    add += ",";
                    add += closingTimes[t];
                    add += ",";
                    add += capacities[t];
                    add += ",";
                    add += locations[t];
                    add += "\n";
                    t++;
                } else {
                    add += "false\n";
                }
            }
            for (String customer : uniqueCustomers) {
                add += customer;
                add += "\n";
            }
            add += "BREAK\n";
            //Adds sessions back into file in the form
            //isApproved,hour,day,month,year,customer username
            for (int i = 0; i < sessions.size(); i++) {
                for (int j = 0; j < sessions.get(i).getWaitingCustomers().size(); j++) {
                    add += "waiting,";
                    add += sessions.get(i).getHour();
                    add += ",";
                    add += sessions.get(i).getDay();
                    add += ",";
                    add += sessions.get(i).getMonth();
                    add += ",";
                    add += sessions.get(i).getYear();
                    add += ",";
                    add += sessions.get(i).getWaitingCustomers().get(j);
                    add += "\n";
                }
                for (int k = 0; k < sessions.get(i).getWaitingCustomers().size(); k++) {
                    add += "approved,";
                    add += sessions.get(i).getHour();
                    add += ",";
                    add += sessions.get(i).getDay();
                    add += ",";
                    add += sessions.get(i).getMonth();
                    add += ",";
                    add += sessions.get(i).getYear();
                    add += ",";
                    add += sessions.get(i).getEnrolledCustomers().get(k);
                    add += "\n";
                }
            }
            add = add.trim();
            printWriter.println(add);
            printWriter.close();
            return true;
        } catch (Exception exception) {
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
        Store store = new Store(name, seller);
        try {
            File f = new File(name + "Store.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();

            boolean[] isOpen = new boolean[7];
            int daysOpen = 0;

            for (int i = 1; i < 8; i++) {
                if (!list.get(i).equals("false")) {
                    isOpen[i - 1] = true;
                    daysOpen++;
                } else {
                    isOpen[i - 1] = false;
                }
            }
            int[] openingTimes = new int[daysOpen];
            int[] closingTimes = new int[daysOpen];
            int[] capacity = new int[daysOpen];
            String[] location = new String[daysOpen];

            for (int i = 1; i < 8; i++) {
                for (int j = 0; j < daysOpen; j++) {
                    if (!list.get(i).equals("false")) {
                        String[] tmpArr = list.get(i).split(",");
                        openingTimes[j] = Integer.parseInt(tmpArr[1]);
                        closingTimes[j] = Integer.parseInt(tmpArr[2]);
                        capacity[j] = Integer.parseInt(tmpArr[3]);
                        location[j] = tmpArr[4];
                    }
                }
            }
            store.setupStoreInputChecks(isOpen, openingTimes, closingTimes, capacity, location);
            //Code to make uniqueCustomers
            File file = new File(name + "Store.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line1 = "";
            Set<String> uniqueCustomers = new HashSet<String>();
            for (int i = 0; i < 9; i++) {
                line1 = bufferedReader.readLine();
            }
            do {
                uniqueCustomers.add(line1);
                line1 = bufferedReader.readLine();
            } while (!line1.equals("BREAK"));
            store.setUniqueCustomers(uniqueCustomers);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
     * @param year
     * @param day
     * @param month
     * @param day
     * @param hour
     * @return
     */
    public boolean checkIfStoreIsOpen(int year , int month, int day , int hour) {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month);
        calender.set(Calendar.DAY_OF_MONTH, day);
        calender.set(Calendar.HOUR, hour);
        //Checks if the store is open on this particular day
        if (!isOpen[calender.get(Calendar.DAY_OF_WEEK) - 1]) {
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
            return false;
        }
        if (closingTimes[index] <= calender.get(Calendar.HOUR)) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if there already exists a session object in this store (in the field sessions) at the entered
     * year, month, day, and hour. This method is never called directly, but is called in other methods
     * that are used when a customer applies for an appointment so that there can never be two
     * session objects with the same time at the same store.
     */
    public boolean checkIfSessionAtTimeAlreadyExists(int year , int month, int day , int hour) {
        if (sessionAtSpecifiedTime(year , month , day , hour) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return session at the specified time, if it exists. If it does not exist, return null.
     */
    public Session sessionAtSpecifiedTime(int year , int month , int day , int hour) {
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
    public void requestAppointmentAtTime(int year , int month, int day , int hour ,
                                         String customerName) {
        if (!checkIfSessionAtTimeAlreadyExists(year , month , day ,hour)) {
            Session session = new Session(year , month , day ,hour, name);
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
    }

    /**
     * Removes customer from waitlist at the specified session, if applicable, or does nothing.
     * This method updates the correct store and customer objects and saves them.
     */
    public void declineAppointmentAtTime(int year , int month, int day , int hour ,
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
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param customerName
     */
    public void approveAppointmentAtTime(int year , int month, int day , int hour ,
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
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param customerName
     */
    public void cancelAlreadyApprovedAppointment(int year , int month, int day , int hour ,
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
}
