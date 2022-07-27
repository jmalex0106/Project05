import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.*;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

public class Store {
    private static final int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in normal years
    private static final int[] leapDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //days in leap years
    private String name;  //The name of the store
    private String seller;  //The name of the seller that owns this store
    private Year[] years;  //An array of year objects that hold all the sessions at this store
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

    /**
     * A basic constructor for the store object. Sets the name of the store and the seller name,
     * and sets all of the sessions in years to blank sessions.
     * @param name - the name of this store.
     * @param seller - the name of the owner of this store.
     */
    public Store(String name, String seller) {
        this.name = name;
        this.seller = seller;
        this.years = new Year[10];
        this.isOpen = new boolean[7];
        this.openingTimes = null;
        this.closingTimes = null;
        this.capacities = null;
        this.locations = null;
        this.uniqueCustomers = new HashSet<String>();
        for (int i = 0; i < years.length; i++) {
            for (int j = 0; j < 12; j++) {
                Month month = new Month(j, i + 2022);
                for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                    Day day = new Day(k + 1, j, i + 2022);
                    int dayOfWeek = day.getDayOfWeek() - 1;
                    if (isOpen[dayOfWeek]) {
                        for (int l = 0; l < 24; l++) {
                            if (openingTimes[dayOfWeek] <= l &&
                                    closingTimes[dayOfWeek] > l) {
                                Session session = new Session(l, k, j, i + 2022);
                                years[i].getMonthAtInt(j).getDayAtInt(k).setSessionAtHour(l, session);
                            }
                        }
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }
    public String getSeller() {
        return seller;
    }

    public Year[] getYears() {
        return years;
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
    public void setupStore() {
        if (setupStoreInputChecks(isOpen, openingTimes, closingTimes,
                capacities, locations)) {
            for (int i = 0; i < years.length; i++) {
                for (int j = 0; j < 12; j++) {
                    Month month = new Month(j, i + 2022);
                    for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                        Day day = new Day(k + 1, j, i + 2022);
                        int dayOfWeek = day.getDayOfWeek() - 1;
                        if (isOpen[dayOfWeek]) {
                            for (int l = 0; l < 24; l++) {
                                if (openingTimes[dayOfWeek] <= l &&
                                        closingTimes[dayOfWeek] > l) {
                                    Session session = new Session(l, k, j, i + 2022);
                                    years[i].getMonthAtInt(j).getDayAtInt(k).setSessionAtHour(l, session);
                                }
                            }
                        }
                    }
                }
            }
        }
        //adds store as a line to AllStores.txt, which lists all existing stores
        try {
            File file = new File("AllStores.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = getName();
            add += ",";
            add += getSeller();
            printWriter.println(add);
            printWriter.close();
        } catch (Exception exception) {
            System.out.println("An error has occurred");
        }
    }

    /**
     * Remakes the file associated with this store from the store object. This method needs to be
     * called on the server side to save changes made to a store object.
     * TODO-figure out how this works and provide better docs
     */
    public void makeFileFromStore() {
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
            add += "BREAK";
            add += "\n";
            for (int i = 0; i < years.length; i++) {
                for (int j = 0; j < 12; j++) {
                    Month month = new Month(j, i + 2022);
                    for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                        Day day = new Day(k + 1, j, i + 2022);
                        int dayOfWeek = day.getDayOfWeek();
                        if (isOpen[dayOfWeek]) {
                            for (int l = 0; l < 24; l++) {
                                if (years[i].getMonthAtInt(j).getDayAtInt(k).
                                        getSessionAtHour(l).getEnrolledCustomers().size() > 0) {
                                    for (int m = 0; m < years[i].getMonthAtInt(j).getDayAtInt(k).
                                            getSessionAtHour(l).getEnrolledCustomers().size(); m++) {
                                        add += "approved,";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getHour();
                                        add += ",";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getDay();
                                        add += ",";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getMonth();
                                        add += ",";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getYear();
                                        add += ",";
                                        years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).
                                                getEnrolledCustomers().get(i);
                                        add += "\n";
                                    }
                                }
                                if (years[i].getMonthAtInt(j).getDayAtInt(k).
                                        getSessionAtHour(l).getWaitingCustomers().size() > 0) {
                                    for (int m = 0; m < years[i].getMonthAtInt(j).getDayAtInt(k).
                                            getSessionAtHour(l).getWaitingCustomers().size(); m++) {
                                        add += "approved,";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getHour();
                                        add += ",";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getDay();
                                        add += ",";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getMonth();
                                        add += ",";
                                        add += years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).getYear();
                                        add += ",";
                                        years[i].getMonthAtInt(j).getDayAtInt(k).
                                                getSessionAtHour(l).
                                                getWaitingCustomers().get(i);
                                        add += "\n";
                                    }
                                }
                            }
                        }
                    }
                }
            }
            add = add.trim();
            printWriter.println(add);
            printWriter.close();
        } catch (Exception exception) {
            System.out.println("An error has occurred");
        }
    }

    /**
     *
     * @return number of unique customers for this store
     */
    public int numberOfCustomers() {
        return uniqueCustomers.size();
    }

    /**
     * This method is called whenever the tutor approves a new
     * @param name
     */
    public void addCustomer(String name) {
        uniqueCustomers.add(name);
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

    public void setUniqueCustomers(Set<String> uniqueCustomers) {
        this.uniqueCustomers = uniqueCustomers;
    }

    /**
     *
     * @return A string for the most popular day at this store
     */
    public String mostPopularAp() {
        int cnt = 0;
        for (int i = 0; i < years.length; i++) {
            for (int j = 0; j < 12; j++) {
                Month month = new Month(j, i + 2022);
                for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                    Day day = new Day(k + 1, j, i + 2022);
                    for (int l = 0; l < 24; l++) {
                        for (int m = 0; m < 31; m++) {
                            day.setSessionAtHour(l, new Session(l, m, month.getMonth(), 2022));
                            Session tmpSession = day.getSessionAtHour(l);
                            if (cnt <= tmpSession.getEnrolledCustomers().size() +
                                    tmpSession.getWaitingCustomers().size()) {
                                int tmp = month.getFirstDay(i + 2022, month.getMonth()) - 1;
                                cnt = (tmpSession.getDay() + tmp) % 7;
                            }
                        }
                    }
                }
            }
        }
        switch (String.valueOf(cnt)) {
            case "0":
                return "Sunday";
            case "1":
                return "Monday";
            case "2":
                return "Tuesday";
            case "3":
                return "Wednesday";
            case "4":
                return "Thursday";
            case "5":
                return "Friday";
            case "6":
                return "Saturday";
            default:
                return "INVALID";
        }
    }

    public Set<String> getUniqueCustomers() {
        return uniqueCustomers;
    }
}

