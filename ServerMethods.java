import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServerMethods {
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
     *
     * @param isTutor True if the new account being created is for a Tutor, false if the new
     *                account is for a student
     * @param newUsername The new account's username
     * @param newEmail  The new account's email
     * @param newPassword  The new account's password
     * @return A boolean corresponding to the success of the account creation. false
     * means no account was created, true means the account was created successfully.
     */
    public boolean createNewAccount(boolean isTutor , String newUsername ,
                                 String newEmail , String newPassword) {
        //Checks if inputs are valid. Usernames must be Unique, must consist of only letters
        //And numbers, and cannot end in "Store". Usernames are case-sensitive.
        //Emails must contain only one "@" and only one "." after that "@"
        //Passwords must be at least six characters long
        //No input string may contain a comma ","
        //Checks if any inputs are null:
        if (newUsername == null) {
            return false;
        }
        if (newEmail == null) {
            return false;
        }
        if (newPassword == null) {
            return false;
        }
        //Checks if any inputs contain a comma ",":
        if (newUsername.contains(",")) {
            return false;
        }
        if (newEmail.contains(",")) {
            return false;
        }
        if (newPassword.contains(",")) {
            return false;
        }
        //Checks if password is six or more characters
        if (newPassword.length() < 6) {
            return false;
        }
        //Checks if email contains only one "@" and only one "." after that "@"
        if (!newEmail.contains("@")) {
            return false;
        }
        if (newEmail.split("@").length != 2) {
            return false;
        }
        if (!newEmail.split("@")[1].contains(".")) {
            return false;
        }
        if (newEmail.split("@")[1].split(".").length != 2) {
            return false;
        }
        //Checks if username ends in Store and contains only letters and numbers
        if (newUsername.endsWith("Store")) {
            return false;
        }
        if (!newUsername.matches("^[a-zA-Z0-9]*$")) {
            return false;
        }
        try {
            //Reads Users.txt to check if newUsername is unique.
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Users.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",")[1].equals(newUsername)) {
                    return false;
                }
            }
            bufferedReader.close();
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
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Reads AllStores.txt
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
        }
        return output;
    }

    /**
     * This method is called on the server side when the user, a customer, presses the confirm
     * button on the request appointment GUI. After the confirm button has been pressed, a
     * JOptionpane with the appropriate error/success message will appear.
     * @param customer
     * @param storeName
     * @param year
     * @param month
     * @param day
     * @param hour
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
     * 6 - An unexpected error has occurred. Please try again. Waitlist has not been updated.
     */
    public int requestAppointment (Customer customer , String storeName , int year ,
                                   int month , int day , int hour) {
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
            Store store = new Store(storeName , storeOwner);
            //Checks if the date entered is a valid date that is in the future

        } catch (Exception exception) {
            return 6;
        }
        //TODO finish this method.
        return -4; //This is a placeholder so the method compiles.
    }

    /**
     *
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
    public boolean checkIfDateIsFuture (int year , int month , int day , int hour) {
        //Checks if date is valid
        if (!checkIfDateIsValid(year , month , day , hour)) {
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
        if (hour > Integer.parseInt(formattedCurrentDate.split("-")[3])) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return True if date is valid, false is date is not valid.
     */
    public boolean checkIfDateIsValid(int year , int month , int day , int hour) {
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
            LocalDateTime localDateTime = LocalDateTime.parse(dateString , dateTimeFormatter);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}

