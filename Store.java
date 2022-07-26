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
    private String name;
    private String seller;
    private Year[] years;
    private boolean[] isOpen;
    private int[] openingTimes;
    private int[] closingTimes;
    private int[] capacities;
    private String[] locations;
    private Set<String> uniqueCustomers;
    private ArrayList<Session> waitingRequest;
    private ArrayList<Session> approvedRequest;


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
                Month month = new Month(j, i + 2022, name);
                for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                    Day day = new Day(k + 1, j, i + 2022, name);
                    int dayOfWeek = day.getDayOfWeek() - 1;
                    if (isOpen[dayOfWeek]) {
                        for (int l = 0; l < 24; l++) {
                            if (openingTimes[dayOfWeek] <= l &&
                                    closingTimes[dayOfWeek] > l) {
                                Session session = new Session(l, k, j, i, name);
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

    public void setupStore() {
        if (setupStoreInputChecks(isOpen, openingTimes, closingTimes,
                capacities, locations)) {
            for (int i = 0; i < years.length; i++) {
                for (int j = 0; j < 12; j++) {
                    Month month = new Month(j, i + 2022, name);
                    for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                        Day day = new Day(k + 1, j, i + 2022, name);
                        int dayOfWeek = day.getDayOfWeek() - 1;
                        if (isOpen[dayOfWeek]) {
                            for (int l = 0; l < 24; l++) {
                                if (openingTimes[dayOfWeek] <= l &&
                                        closingTimes[dayOfWeek] > l) {
                                    Session session = new Session(l, k, j, i, name);
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
                    Month month = new Month(j, i + 2022, name);
                    for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                        Day day = new Day(k + 1, j, i + 2022, name);
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

    public int numberOfCustomers() {
        return uniqueCustomers.size();
    }

    public void addCustomer(String name) {
        uniqueCustomers.add(name);
    }

    public boolean approve(Session session, Customer customer) {
        if (customer.waitingRequest.contains(session) &&
                waitingRequest.contains(session)) {
            customer.approvedRequest.add(session);
            customer.waitingRequest.remove(session);
            approvedRequest.add(session);
            waitingRequest.remove(session);
            return true;
        } else {
            System.out.println("The Session is not in the waiting list");
            return false;
        }
    }

    public boolean decline(Session session, Customer customer) {
        if (customer.waitingRequest.contains(session) &&
                waitingRequest.contains(session)) {
            customer.waitingRequest.remove(session);
            waitingRequest.remove(session);
            return true;
        } else {
            System.out.println("The Session is not in the waiting list");
            return false;
        }
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
            } while (line1 != "BREAK");
            store.setUniqueCustomers(uniqueCustomers);
            //Code to set waitingRequest and approvedRequest
            waitingApproved(name + "Store.txt");
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitingApproved(String fileName) { //Code to set waitingRequest and approvedRequest
        File file = new File(fileName);
        ArrayList<String> list = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int idx = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase("BREAK")) {
                idx = i;
            }
        }

        for (int i = idx + 1; i < list.size(); i++) {
            String[] tmpArr = list.get(i).split(",");
            if (tmpArr[0].equalsIgnoreCase("Approved")) {
                int tmpHour = Integer.parseInt(tmpArr[1]);
                int tmpDay = Integer.parseInt(tmpArr[2]);
                int tmpMonth = Integer.parseInt(tmpArr[3]);
                int tmpYear = Integer.parseInt(tmpArr[4]);
                Session tmpSession = new Session(tmpHour, tmpDay, tmpMonth, tmpYear, name);
                String tmpEmail = tmpSession.getEmailFromFile();
                tmpSession.addToEnrolledList(new Customer(tmpArr[5], tmpEmail));
                approvedRequest.add(tmpSession);
            } else if (tmpArr[0].equalsIgnoreCase("Waiting")) {
                int tmpHour = Integer.parseInt(tmpArr[1]);
                int tmpDay = Integer.parseInt(tmpArr[2]);
                int tmpMonth = Integer.parseInt(tmpArr[3]);
                int tmpYear = Integer.parseInt(tmpArr[4]);
                Session tmpSession = new Session(tmpHour, tmpDay, tmpMonth, tmpYear, name);
                String tmpEmail = tmpSession.getEmailFromFile();
                tmpSession.addToWaitingList(new Customer(tmpArr[5], tmpEmail));
                waitingRequest.add(tmpSession);
            }
        }

    }


    public void setUniqueCustomers(Set<String> uniqueCustomers) {
        this.uniqueCustomers = uniqueCustomers;
    }

    public void setWaitingRequest(ArrayList<Session> waitingRequest) {
        this.waitingRequest = waitingRequest;
    }

    public void setApprovedRequest(ArrayList<Session> approvedRequest) {
        this.approvedRequest = approvedRequest;
    }

    public String mostPopularAp(String storeName, String seller) {
        int cnt = 0;
        Store store = new Store(storeName, seller);
        store.remakeStoreFromFile();

        for (int i = 0; i < years.length; i++) {
            for (int j = 0; j < 12; j++) {
                Month month = new Month(j, i + 2022, name);
                for (int k = 0; k < month.numberOfDaysInMonth(); k++) {
                    Day day = new Day(k + 1, j, i + 2022, name);
                    for (int l = 0; l < 24; l++) {
                        for (int m = 0; m < 31; m++) {
                            day.setSessionAtHour(l, new Session(l, m, month.getMonth(), 2022, name));
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

    public ArrayList<Session> getWaitingRequest() {
        return waitingRequest;
    }

    public ArrayList<Session> getApprovedRequest() {
        return approvedRequest;
    }

    public Set<String> getUniqueCustomers() {
        return uniqueCustomers;
    }
}