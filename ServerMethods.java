import java.awt.geom.AffineTransform;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * TODO ADD DESCRIPTIVE JAVADOCS
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version date
 */

public class ServerMethods {
    private static final String[] MONTH_NAMES = new String[]{"January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private static final String[] DAY_NAMES = new String[]{"Sunday", "Monday",
            "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    public ServerMethods() {
    }

    /**
     * @param username - the username entered
     * @param password - the password entered
     * @return - 0 if a matching account is not found in Users.txt, 1 if a matching Tutor account is
     * found in Users.txt, and 2 if a matching Student account is found in Users.txt.
     */
    public int searchForValidLogin(String username, String password) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Users.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",")[0].equals("Tutor") && //checks if user is student
                        line.split(",")[1].equals(username) &&  //checks username
                        line.split(",")[3].equals(password)) { //checks password
                    return 1;
                }
                if (line.split(",")[0].equals("Student") && //checks if user is student
                        line.split(",")[1].equals(username) &&  //checks username
                        line.split(",")[3].equals(password)) { //checks password
                    return 2;
                }
                line = bufferedReader.readLine();
            }
            return 0;
        } catch (Exception exception) {
            return 0;
        }
    }

    /**
     * @param isTutor     True if the new account being created is for a Tutor, false if the new
     *                    account is for a student
     * @param newUsername The new account's username
     * @param newEmail    The new account's email
     * @param newPassword The new account's password
     * @return A boolean corresponding to the success of the account creation. false
     * means no account was created, true means the account was created successfully.
     */
    public boolean createNewAccount(boolean isTutor, String newUsername,
                                    String newEmail, String newPassword) {
        //Checks if inputs are valid. Usernames must be Unique, must consist of only letters
        //And numbers, and cannot end in "Store". Usernames are case-sensitive.
        //Emails must contain only one "@" and only one "." after that "@"
        //Passwords must be at least six characters long
        //No input string may contain a comma ","
        //Checks if any inputs are null:
        if (newUsername == null) {
            System.out.println("T0");
            return false;
        }
        if (newEmail == null) {
            System.out.println("T1");
            return false;
        }
        if (newPassword == null) {
            System.out.println("T2");
            return false;
        }
        //Checks if any inputs contain a comma ",":
        if (newUsername.contains(",")) {
            System.out.println("T3");
            return false;
        }
        if (newEmail.contains(",")) {
            System.out.println("T4");
            return false;
        }
        if (newPassword.contains(",")) {
            System.out.println("T5");
            return false;
        }
        //Checks if password is six or more characters
        if (newPassword.length() < 6) {
            System.out.println("T6");
            return false;
        }
        //Checks if email contains only one "@" and only one "." after that "@"
        if (!newEmail.contains("@")) {
            System.out.println("T7");
            return false;
        }
        if (newEmail.split("@").length != 2) {
            System.out.println("T8");
            return false;
        }
        if (!newEmail.split("@")[1].contains(".")) {
            System.out.println("T9");
            return false;
        }
        //Checks if username ends in Store and contains only letters and numbers
        System.out.println("TEST0");
        if (newUsername.endsWith("Store")) {
            System.out.println("T11");
            return false;
        }
        if (!newUsername.matches("^[a-zA-Z0-9]*$")) {
            System.out.println("T12");
            return false;
        }
        try {
            System.out.println("TEST1");
            //Reads Users.txt to check if newUsername is unique.
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Users.txt"));
            String line = bufferedReader.readLine();
            System.out.println("LINE = " + line);
            System.out.println("TEST1-0");
            while (line != null) {
                System.out.println("LINE = " + line);
                if (line.split(",")[1].equals(newUsername)) {
                    System.out.println("T13");
                    return false;
                }
                line = bufferedReader.readLine();
            }
            System.out.println("TEST1-1");
            bufferedReader.close();
            System.out.println("TEST2");
            //Closes bufferedReader and opens printWriter
            File file = new File("Users.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            //Forms addLine, which is a line in Users.txt corresponding to the new account that
            //has just been made
            String addLine = "";
            if (isTutor) {
                addLine += "Tutor,";
            } else {
                addLine += "Student,";
            }
            addLine += newUsername;
            addLine += ",";
            addLine += newEmail;
            addLine += ",";
            addLine += newPassword;
            //Adds addLine to Users.txt and closes printWriter
            printWriter.println(addLine);
            printWriter.close();
            //Creates a new file for this new account
            File file1 = new File(newUsername + ".txt");
            file1.createNewFile();
        } catch (Exception exception) {
            System.out.println("T14");
            return false;
        }
        System.out.println("T15");
        return true;
    }

    /**
     * Reads AllStores.txt
     *
     * @return Returns an array list of strings containing the names of all the stores
     * that exist. This can be sent to the customer for displaying
     */
    public ArrayList<String> allExistingStores() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.add(line.split(",")[0]);
            }
            bufferedReader.close();
        } catch (Exception exception) {
            int catchInt = 0;
        }
        return output;
    }

    /**
     * This method is called on the server side when the user, a customer, presses the confirm
     * button on the request appointment GUI. After the confirm button has been pressed, a
     * JOptionpane with the appropriate error/success message will appear.
     *
     * @param customer
     * @param storeName
     * @param yearString
     * @param monthString
     * @param dayString
     * @param hourString
     * @return Each int from 0 to 6 corresponds to a different message to be displayed to
     * the user, a customer. These messages are approximations, actual JOptionpane may be different.
     * 0 - You have been added to the waitlist successfully
     * 1 - This session is at or over capacity. You have been added to the waitlist, but the
     * tutor may not accept your request.
     * 2 - Some fields were left blank. Please fill in all fields. Waitlist has not been updated.
     * 3 - The store entered does not exist. Waitlist has not been updated.
     * 4 - The date entered is in the past or is invalid. Please enter a valid date.
     * Waitlist has not been updated.
     * 5 - The store you have entered is closed on the date and time you have entered. Please
     * enter a valid date. Waitlist has not been updated.
     */
    public int requestAppointment(Customer customer, String storeName, String yearString,
                                  String monthString, String dayString, String hourString) {
        //Converts year, month , day , and hour to ints
        int year = Integer.parseInt(yearString.trim());
        int month = monthFormat(monthString);
        int day = dayFormat(dayString);
        int hour = hourFormat(hourString);
        //Searches if the store under storeName exists in AllStores.txt.
        try {
            String storeOwner = "";
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean storeExists = false;
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",")[0].equals(storeName)) {
                    storeExists = true;
                    storeOwner = line.split(",")[1];
                }
            }
            bufferedReader.close();
            if (!storeExists) {
                return 3;
            }
            //Creates a store object
            Store store = new Store(storeName, storeOwner);
            store.remakeStoreFromFile();
            //Checks if the date entered is a valid date that is in the future
            if (!checkIfDateIsFuture(year, month, day, hour)) {
                return 4;
            }
            if (!store.checkIfStoreIsOpen(year, month, day, hour)) {
                return 5;
            }
            //Sets the int capacity to the capacity of the store at the requested session time
            int index = 0;
            for (int i = 0; i < dayOfWeek(year, month, day); i++) {
                if (store.getIsOpen()[i]) {
                    index++;
                }
            }
            int output = 0;
            int capacity = store.getCapacities()[index];
            int requestedSessionCapacity =
                    store.sessionAtSpecifiedTime(year, month, day, hour).getEnrolledCustomers().size();
            if (capacity > requestedSessionCapacity) {
                output = 0;
            } else {
                output = 1;
            }
            store.requestAppointmentAtTime(year, month, day, hour, customer.getName());
            customer.requestAppointment(store.sessionAtSpecifiedTime(year, month, day, hour));
            store.makeFileFromStore();
            customer.remakeFileFromCustomer();
            return output;
        } catch (IOException ioException) {
            return 6;
        }
    }

    /**
     * Removes customer from waitlist at the specified session, if applicable, or does nothing.
     * This method updates the correct store and customer objects and saves them.
     */
    public void declineAppointmentAtTime(int year, int month, int day, int hour,
                                         Customer customer, Store store) {
        store.declineAppointmentAtTime(year, month, day, hour, customer);
        customer.removeFromWaitlistAtTime(year, month, day, hour, store.getName());
        store.makeFileFromStore();
        customer.remakeFileFromCustomer();
    }

    /**
     * Removes customer from waitlist at the specified session and adds them to the enrolledlist.
     * This method updates the correct store and customer objects and saves them.
     */
    public void approveAppointmentAtTime(int year, int month, int day, int hour,
                                         Customer customer, Store store) {
        store.approveAppointmentAtTime(year, month, day, hour, customer.getName());
        customer.approveAppointmentAtTime(year, month, day, hour, store.getName());
        store.makeFileFromStore();
        customer.remakeFileFromCustomer();
    }

    /**
     * Removes customer from approvedlist at the specified session, if applicable, or does nothing.
     * This method updates the correct store and customer objects and saves them.
     */
    public void cancelAppointmentAtTime(int year, int month, int day, int hour,
                                        Customer customer, Store store) {
        store.declineAppointmentAtTime(year, month, day, hour, customer);
        customer.removeFromApprovedlistAtTime(year, month, day, hour, store.getName());
        store.makeFileFromStore();
        customer.remakeFileFromCustomer();
    }

    /**
     * Cancels the appointment at the index appointmentIndex for a certain customer
     * @param customer
     * @param appointmentIndex
     */
    public void customerCancelAppointmentAtIndex(Customer customer , int appointmentIndex) {
        Session sessionToCancel = customer.getApprovedRequest().get(appointmentIndex);
        customer.getApprovedRequest().remove(appointmentIndex);
        String storeName = customer.getApprovedRequest().get(appointmentIndex).getStore();
        String sellerName = "";
        //Creates store from storeName
        try {
            File file = new File ("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",")[0].equals(storeName)) {
                    sellerName = line.split(",")[1];
                }
                break;
            }
            Store store = new Store(storeName , sellerName);
            store.remakeStoreFromFile();
            for (Session session : store.getSessions()) {
                if (session.getYear() ==
                        customer.getApprovedRequest().get(appointmentIndex).getYear() &&
                        session.getMonth() ==
                                customer.getApprovedRequest().get(appointmentIndex).getMonth() &&
                        session.getDay() ==
                                customer.getApprovedRequest().get(appointmentIndex).getDay() &&
                        session.getHour() ==
                                customer.getApprovedRequest().get(appointmentIndex).getHour()) {
                    session.removeFromEnrolledList(customer.getName());
                }
            }
            store.makeFileFromStore();
        } catch (Exception exception) {
            int catchInt = 1;
        }
        customer.remakeFileFromCustomer();
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return true if the entered date is in the future. This method assumes, as does
     * the rest of this project, that the time zone is West Lafayette Time. A date is invalid if
     * it does not exist, such as month = 15, day = -7, year = null , or hour = 55. A date is
     * in the past if the day that the date occurs on is in the past, or if the day that the
     * date occurs on is the present day and the hour is in the past, or if the day is the
     * present day and the hour is the start of the present hour. For example, if the present time
     * is May 3rd 15:07, 2022, then the time May 3rd 15:00, 2022 is considered past.
     */
    public boolean checkIfDateIsFuture(int year, int month, int day, int hour) {
        //Checks if date is valid
        if (!checkIfDateIsValid(year, month, day, hour)) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH");
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        String formattedCurrentDate = formatter.format(localDateTimeNow);
        //Checks if year strictly in the future or past
        if (year > Integer.parseInt(formattedCurrentDate.split("-")[0])) {
            return true;
        } else if (year < Integer.parseInt(formattedCurrentDate.split("-")[0])) {
            return false;
        }
        //Checks if month is strictly in the future or past
        if (month > Integer.parseInt(formattedCurrentDate.split("-")[1])) {
            return true;
        } else if (month < Integer.parseInt(formattedCurrentDate.split("-")[1])) {
            return false;
        }
        //Checks if day is strictly in the future or past
        if (day > Integer.parseInt(formattedCurrentDate.split("-")[2])) {
            return true;
        } else if (day < Integer.parseInt(formattedCurrentDate.split("-")[2])) {
            return false;
        }
        //Checks if hour is strictly in the future or past, or if the hour is the current hour
        return (hour > Integer.parseInt(formattedCurrentDate.split("-")[3]));
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return True if date is valid, false is date is not valid.
     */
    public boolean checkIfDateIsValid(int year, int month, int day, int hour) {
        try {
            //Sets formatter object
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH");
            //Builds a string out of the inputs year,month,day,hour to process.
            String yearString = String.valueOf(year);
            String monthString = String.valueOf(month);
            String dayString = String.valueOf(day);
            String hourString = String.valueOf(hour);
            if (yearString.length() != 4) {
                return false;
            }
            if (monthString.length() == 1) {
                monthString = "0" + monthString;
            }
            if (dayString.length() == 1) {
                dayString = "0" + dayString;
            }
            if (hourString.length() == 1) {
                hourString = "0" + hourString;
            }
            String dateString = String.format("%s-%s-%s-%s", yearString, monthString,
                    dayString, hourString);
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * @param year
     * @param month
     * @param day
     * @return 0 for Sunday, 1 for Monday , 6 for Saturday , and -1 for an invalid date.
     */
    public int dayOfWeek(int year, int month, int day) {
        if (!checkIfDateIsValid(year, month, day, 12)) {
            return -1;
        }
        return LocalDate.of(year, month + 1, day).getDayOfWeek().getValue();
    }

    /**
     * Takes a string month as entered by a user and converts it to an integer corresponding to
     * that month. The conversion is as follows:
     * If monthString is an integer, return the value minus 1.
     * If monthString is alphabetical, return the value of a month if the name of the month
     * unambiguously contains String month as a substring, without regard to case. So
     * "feb" , "FEB" , "bR" all return 1 because only "february" has these as substrings.
     * "ju" would return -1, since it is a substring of both "June" and "July".
     * "dx" would return -1, since no month's name contains "dx" as a substring.
     *
     * @param monthString
     * @return return 0 for January , 1 for February , 11 for December , and -1 for an invalid
     * month
     */
    public int monthFormat(String monthString) {
        //Checks if monthString is an integer
        try {
            int i = Integer.parseInt(monthString);
            return i - 1;
        } catch (NumberFormatException numberFormatException) {
            int catchInt = 0;
        }
        int numberSubstringCount = 0;
        int output = 0;
        for (int i = 0; i < MONTH_NAMES.length; i++) {
            if (MONTH_NAMES[i].toLowerCase().contains(monthString.toLowerCase())) {
                numberSubstringCount++;
                output = i;
            }
        }
        if (numberSubstringCount != 1) {
            return -1;
        }
        return output;
    }

    /**
     * Takes a string day as entered by a user and converts it to an integer corresponding to
     * that day. The conversion is as follows:
     * If dayString is an integer, return the value minus 1.
     * If monthString is alphabetical, return the value of a day if the name of the day
     * unambiguously contains String dayString as a substring, without regard to case. So
     * "mon" , "MO" , "onday" all return 1 because only "Monday" has these as substrings.
     * "nday" would return -1, since it is a substring of both "Sunday" and "Monday".
     * "dx" would return -1, since no day's name contains "dx" as a substring.
     *
     * @param dayString
     * @return return 0 for Sunday , 1 for Monday , 6 for Saturday , and -1 for an invalid
     * day
     */
    public int dayFormat(String dayString) {
        //Checks if dayString is an integer
        try {
            int i = Integer.parseInt(dayString);
            return i - 1;
        } catch (NumberFormatException numberFormatException) {
            int catchInt = 0;
        }
        int numberSubstringCount = 0;
        int output = 0;
        for (int i = 0; i < DAY_NAMES.length; i++) {
            if (DAY_NAMES[i].toLowerCase().contains(dayString.toLowerCase())) {
                numberSubstringCount++;
                output = i;
            }
        }
        if (numberSubstringCount != 1) {
            return -1;
        }
        return output;
    }

    /**
     * Converts a string hourString as entered by the user into an int representing that hour.
     * The int returned represents that hour in military time. The conversion is so:
     * Integer are returned as-is; "15" returns 15.
     * Integer followed by ":00" returns the integer. So "16:00" returns 16.
     * AM and PM notation processing
     * 12 noon vs midnight processing
     *
     * @param hourString
     * @return
     */
    public int hourFormat(String hourString) {
        String toProcess = hourString.replace(" ", "");
        toProcess = toProcess.replace(".", "");
        toProcess = toProcess.replace(":00", "");
        toProcess = toProcess.toLowerCase();
        int output = 0;
        if (toProcess.equals("noon")) {
            return 12;
        }
        if (toProcess.equals("midnight")) {
            return 0;
        }
        try {
            output = Integer.parseInt(toProcess);
            return output;
        } catch (NumberFormatException numberFormatException) {
            int catchInt = 0;
        }
        try {
            if (toProcess.contains("am")) {
                output = Integer.parseInt(toProcess.replace("am", ""));
                if (output != 12) {
                    return output;
                } else {
                    return 0;
                }
            } else if (toProcess.contains("pm")) {
                output = Integer.parseInt(toProcess.replace("pm", ""));
                if (output != 12) {
                    return output + 12;
                } else {
                    return 12;
                }
            }
        } catch (NumberFormatException numberFormatException) {
            int catchInt = 0;
        }
        return -1;
    }

    /**
     * Converts a string as entered by a user into a boolean. "1","yes","true","open"
     * return open, without regard to case. Other strings return false.
     *
     * @param booleanString
     * @return
     */
    public boolean booleanFormat(String booleanString) {
        if (booleanString == null) {
            return false;
        }
        switch (booleanString.trim().toLowerCase()) {
            case "1", "yes", "open" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * This method is only to be called when a tutor opens a brand new store for the
     * first time.
     *
     * @param seller
     * @param storeName
     * @param csvPath
     * @return 0 if a new store has been made successfully, 1 if the name is taken, 2
     * if the inputs are invalid or the CSV import gives an error.
     */
    public int setupNewStore(Seller seller, String storeName, String csvPath) {
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",")[0].equalsIgnoreCase(storeName)) {
                    return 1;
                }
            }
            bufferedReader.close();
            Store store = new Store(storeName, seller.getName());
            //Reads the csv file at csvPath and imports the variables to the store
            store.setupStoreInputChecks(makeIsOpenFromCSV(csvPath),
                    makeOpeningTimesFromCSV(csvPath), makeClosingTimesFromCSV(csvPath),
                    makeCapacitiesFromCSV(csvPath), makeLocationsFromCSV(csvPath));
            store.setupStore();
            store.makeFileFromStore();
            return 0;
        } catch (Exception exception) {
            return 2;
        }
    }

    /**
     * Reads the CSV at the created path and puts the boxes in a string array.
     *
     * @param csvPath
     * @return
     */
    public String[][] csvPathToString(String csvPath) {
        String[][] output = new String[7][5];
        try {
            FileReader fileReader = new FileReader(csvPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            for (int i = 0; i < 7; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < 5; j++) {
                    output[dayFormat(line.split(",")[0])][j] = line.split(",")[j + 1];
                }
            }
            bufferedReader.close();
        } catch (Exception exception) {
            int catchInt = 0;
        }
        return output;
    }

    public boolean[] makeIsOpenFromCSV(String csvPath) {
        String[][] csvArray = csvPathToString(csvPath);
        boolean[] output = new boolean[7];
        for (int i = 0; i < 7; i++) {
            output[i] = booleanFormat(csvArray[i][0]);
        }
        return output;
    }

    public int makeSizeOfArraysFromCSV(String csvPath) {
        String[][] csvArray = csvPathToString(csvPath);
        boolean[] isOpen = makeIsOpenFromCSV(csvPath);
        int output = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output++;
            }
        }
        return output;
    }

    public int[] makeOpeningTimesFromCSV(String csvPath) {
        String[][] csvArray = csvPathToString(csvPath);
        int size = makeSizeOfArraysFromCSV(csvPath);
        boolean[] isOpen = makeIsOpenFromCSV(csvPath);
        int[] output = new int[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = Integer.parseInt(csvArray[i][1]);
                index++;
            }
        }
        return output;
    }

    public int[] makeClosingTimesFromCSV(String csvPath) {
        String[][] csvArray = csvPathToString(csvPath);
        int size = makeSizeOfArraysFromCSV(csvPath);
        boolean[] isOpen = makeIsOpenFromCSV(csvPath);
        int[] output = new int[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = Integer.parseInt(csvArray[i][2]);
                index++;
            }
        }
        return output;
    }

    public int[] makeCapacitiesFromCSV(String csvPath) {
        String[][] csvArray = csvPathToString(csvPath);
        int size = makeSizeOfArraysFromCSV(csvPath);
        boolean[] isOpen = makeIsOpenFromCSV(csvPath);
        int[] output = new int[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = Integer.parseInt(csvArray[i][3]);
                index++;
            }
        }
        return output;
    }

    public String[] makeLocationsFromCSV(String csvPath) {
        String[][] csvArray = csvPathToString(csvPath);
        int size = makeSizeOfArraysFromCSV(csvPath);
        boolean[] isOpen = makeIsOpenFromCSV(csvPath);
        String[] output = new String[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = csvArray[i][4];
                index++;
            }
        }
        return output;
    }

    /**
     * Exports all of a customer's approved appointments to a CSV file
     *
     * @param customer
     */
    public void exportCustomerAppointmentsToCSV(Customer customer) {
        try {
            File file = new File(customer.getName() + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            ArrayList<String> approvedAppointmentStrings = new ArrayList<String>();
            while (line != null) {
                if (line.split(",")[0].equals("approved")) {
                    approvedAppointmentStrings.add(line);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            File file1 = new File(customer.getName() + "Appointments.csv");
            file1.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = "";
            add += "Format: hour, day, month, year, tutor name";
            for (int i = 0; i < approvedAppointmentStrings.size(); i++) {
                add += "\n";
                add += approvedAppointmentStrings.get(i);
            }
            printWriter.println(add);
            printWriter.close();
        } catch (Exception exception) {
            int catchInt = 0;
        }
    }

    //The following is copy of the method above the outputs a file object
    public File exportCustomerAppointmentsToCSVFile(Customer customer) {
        try {
            File file = new File(customer.getName() + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            ArrayList<String> approvedAppointmentStrings = new ArrayList<String>();
            while (line != null) {
                if (line.split(",")[0].equals("approved")) {
                    approvedAppointmentStrings.add(line);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            File file1 = new File(customer.getName() + "Appointments.csv");
            file1.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = "";
            add += "Format: hour, day, month, year, tutor name";
            for (int i = 0; i < approvedAppointmentStrings.size(); i++) {
                add += "\n";
                add += approvedAppointmentStrings.get(i);
            }
            printWriter.println(add);
            printWriter.close();
            return file1;
        } catch (Exception exception) {
            int catchInt = 0;
        }
        return null;
    }

    //The following methods show statistics to the seller
    public String showSellerUnsortedStatistics(Seller seller) {
        return seller.createUnsortedSellerStatisticsToString();
    }

    public String showSellerSortedStatistics(Seller seller) {
        return seller.createSortedSellerStatisticsToString();
    }

    //The following methods show statistics to the customer

    /**
     * reads AllStores.txt and creates all store and puts them into an array.
     *
     * @return
     */
    public ArrayList<Store> allStores() {
        ArrayList<Store> output = new ArrayList<Store>();
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                Store store = new Store(line.split(",")[0], line.split(",")[1]);
                store.remakeStoreFromFile();
                output.add(store);
            }
            return output;
        } catch (Exception exception) {
            int catchInt = 0;
        }
        return output;
    }

    public String showSortedStatisticsToCustomer() {
        String output = "";
        //Sets int maxCustomers to the maximum number of unique customers at a store
        //that this seller owns
        int maxCustomers = 0;
        for (int i = 0; i < allStores().size(); i++) {
            if (maxCustomers < allStores().get(i).getUniqueCustomers().size()) {
                maxCustomers = allStores().get(i).getUniqueCustomers().size();
            }
        }
        for (int i = maxCustomers; i <= 0; i--) {
            for (int j = 0; j < allStores().size(); j++) {
                if (allStores().get(j).getUniqueCustomers().size() == i) {
                    output += allStores().get(j).createStatisticsToString();
                }
            }
        }
        return output;
    }

    public String[] createStoreStatisticsToStringArrayForAllStores() {
        String[] output = new String[allStores().size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = allStores().get(i).getMostPopularDaysOfWeekToString();
        }
        return output;
    }

    public String showUnsortedStatisticsToCustomer() {
        String output = "";
        String[] storeStatisticsArray = createStoreStatisticsToStringArrayForAllStores();
        for (int i = 0; i < storeStatisticsArray.length; i++) {
            output += storeStatisticsArray[i];
        }
        return output;
    }

    //TEST METHODS
    public String[][] csvPathToStringFromFile(File csvPath) {
        String[][] output = new String[7][5];
        try {
            FileReader fileReader = new FileReader(csvPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            for (int i = 0; i < 7; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < 5; j++) {
                    output[dayFormat(line.split(",")[0])][j] = line.split(",")[j + 1];
                }
            }
            bufferedReader.close();
        } catch (Exception exception) {
            int catchInt = 0;
        }
        return output;
    }

    public boolean[] makeIsOpenFromCSVFile(File csvPath) {
        String[][] csvArray = csvPathToStringFromFile(csvPath);
        boolean[] output = new boolean[7];
        for (int i = 0; i < 7; i++) {
            output[i] = booleanFormat(csvArray[i][0]);
        }
        return output;
    }

    public int makeSizeOfArraysFromCSVFile(File csvPath) {
        String[][] csvArray = csvPathToStringFromFile(csvPath);
        boolean[] isOpen = makeIsOpenFromCSVFile(csvPath);
        int output = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output++;
            }
        }
        return output;
    }

    public int[] makeOpeningTimesFromCSVFile(File csvPath) {
        String[][] csvArray = csvPathToStringFromFile(csvPath);
        int size = makeSizeOfArraysFromCSVFile(csvPath);
        boolean[] isOpen = makeIsOpenFromCSVFile(csvPath);
        int[] output = new int[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = Integer.parseInt(csvArray[i][1]);
                index++;
            }
        }
        return output;
    }

    public int[] makeClosingTimesFromCSVFile(File csvPath) {
        String[][] csvArray = csvPathToStringFromFile(csvPath);
        int size = makeSizeOfArraysFromCSVFile(csvPath);
        boolean[] isOpen = makeIsOpenFromCSVFile(csvPath);
        int[] output = new int[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = Integer.parseInt(csvArray[i][2]);
                index++;
            }
        }
        return output;
    }

    public int[] makeCapacitiesFromCSVFile(File csvPath) {
        String[][] csvArray = csvPathToStringFromFile(csvPath);
        int size = makeSizeOfArraysFromCSVFile(csvPath);
        boolean[] isOpen = makeIsOpenFromCSVFile(csvPath);
        int[] output = new int[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = Integer.parseInt(csvArray[i][3]);
                index++;
            }
        }
        return output;
    }

    public String[] makeLocationsFromCSVFile(File csvPath) {
        String[][] csvArray = csvPathToStringFromFile(csvPath);
        int size = makeSizeOfArraysFromCSVFile(csvPath);
        boolean[] isOpen = makeIsOpenFromCSVFile(csvPath);
        String[] output = new String[size];
        int index = 0;
        for (int i = 0; i < 7; i++) {
            if (isOpen[i]) {
                output[index] = csvArray[i][4];
                index++;
            }
        }
        return output;
    }
    public int setupNewStoreFromFile(Seller seller, String storeName, File csvPath) {
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",")[0].equalsIgnoreCase(storeName)) {
                    return 1;
                }
            }
            bufferedReader.close();
            Store store = new Store(storeName, seller.getName());
            //Reads the csv file at csvPath and imports the variables to the store
            store.setupStoreInputChecks(makeIsOpenFromCSVFile(csvPath),
                    makeOpeningTimesFromCSVFile(csvPath),
                    makeClosingTimesFromCSVFile(csvPath),
                    makeCapacitiesFromCSVFile(csvPath),
                    makeLocationsFromCSVFile(csvPath));
            store.setupStore();
            store.makeFileFromStore();
            return 0;
        } catch (Exception exception) {
            return 2;
        }
    }

    public String[] allStoreNames() {
        ArrayList<String> allStoreNamesArrayList = new ArrayList<String>();
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                allStoreNamesArrayList.add(line.split(",")[0]);
            }
            String[] output = new String[allStoreNamesArrayList.size()];
            for (int i = 0; i < output.length; i++) {
                output[i] = allStoreNamesArrayList.get(i);
            }
            return output;
        } catch (Exception exception) {
            int catchInt = 1;
        }
        return null;
    }

    public Store getStoreWithName(String storeName) {
        ArrayList<Store> stores = allStores();
        for (Store store : stores) {
            if (store.getName().equals(storeName)) {
                return store;
            }
        }
        return null;
    }
}


