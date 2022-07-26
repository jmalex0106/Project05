import java.awt.Desktop;
import java.net.StandardSocketOptions;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class MainMenu {
    private static final String WELCOME = "Welcome to the tutoring scheduler";
    private static final String EXIT = "Thank you for using the tutoring scheduler";
    private static final String INVALID = "Invalid option selected. Please try again";
    private static final String ERROR = "An error has occurred. Please try again";
    private static final String INVALIDACCOUNT = "Your account information has been stored incorrectly. " +
            "Please contact customer support";
    private static final String WELCOMEMENU = """
            1. Sign in
            2. Create account
            3. Exit
            4. Mystery option""";
    private static final String ENTERUSERNAME = "Enter your username:";
    private static final String ENTERPASSWORD = "Enter your password:";
    private static final String SUCCESSFULLOGIN = "Login successful";
    private static final String FAILEDLOGIN = "Invalid login information. Please try again:";
    private static final String RETRYLOGINMENU = """
            1. Re-enter login information
            2. Create account
            3. Back to main menu""";
    private static final String NEWTUTORSTUDENT = "Are you registering as a tutor or a student?\n" +
            "Type 1 for tutor, 2 for student.";
    private static final String NEWUSERNAME = "Please enter a username to use with your account";
    private static final String USERNAMETAKEN = "Username is taken. Please try again";
    private static final String NEWEMAIL = "Please enter an email for your account";
    private static final String INVALIDEMAIL = "Invalid email. Please try again";
    private static final String NEWPASSWORD = "Please create a new password for your account";
    private static final String INVALIDPASSWORD = "Password is too short. " +
            "Please try again with another password";
    private static final String SUCCESSFULACCOUNTCREATION = "Account created successfully!";
    private static final String CUSTOMERINITIALMENU = """
            1. Request a new appointment
            2. List appointments
            3. Export CSV file of approved appointments
            4. View personal statistics
            5. Back to main menu""";
    private static final String ENTERSTORENAME = "Enter the name of the store for your appointment";
    private static final String ENTERYEAR = "Enter the year for your appointment";
    private static final String ENTERMONTH = "Enter the month for your appointment";
    private static final String ENTERDAY = "Enter the day for your appointment";
    private static final String ENTERHOUR = "Enter the hour for your appointment";
    private static final String SEEAPPOINTMENTS = "Enter 1 to view waiting appointemnts " +
            "or 2 to view approved appointments.";
    private static final String STATSMENU = "Enter 1 to view unsorted statistics or " +
            "2 to view sorted statistics, or 3 to go back to the customer menu.";
    private static final String SELLERINITIALMENU = """
            1. View list of stores
            2. Setup new store from CSV
            3. Back to main menu
            4. View unsorted statistics
            5. View sorted statistics
            """;
    private static final String INITIALSTOREMENU = "1. Approve pending appointments\n" +
            "2. Decline pending appointments\n" +
            "3. Back to seller menu";
    private String input;
    private Scanner scanner;


    public MainMenu() {
        this.input = "";
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public static void main(String[] args) {
        System.out.println(WELCOME);
        MainMenu mainMenu = new MainMenu();
        mainMenu.initialMainMenu();
    }

    public void initialMainMenu() {
        System.out.println(WELCOMEMENU);
        setInput(getScanner().nextLine());
        switch (getInput().trim()) {
            case "1" -> {
                initialLoginEnterInfo();
                initialMainMenu();
            }
            case "2" -> {
                createNewAccount();
                close();
            }
            case "3" -> {
                close();
            }
            case "4" -> {
                playVideo("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
                initialMainMenu();
            }
            default -> {
                System.out.println(INVALID);
                initialMainMenu();
            }
        }
    }

    public void initialLoginEnterInfo() {
        System.out.println(ENTERUSERNAME);
        String username = scanner.nextLine().trim();
        System.out.println(ENTERPASSWORD);
        String password = scanner.nextLine().trim();
        searchForValidLogin(username, password);
    }

    public void searchForValidLogin(String username, String password) {
        boolean loop = true;
        boolean login = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Users.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line != null) {
                    String[] lineSplit = line.split(",");
                    if (lineSplit.length == 4) {
                        if (lineSplit[1].equalsIgnoreCase(username) &&
                                lineSplit[3].equals(password) &&
                                lineSplit[0].equals("Tutor")) {
                            System.out.println(SUCCESSFULLOGIN);
                            Seller seller = new Seller(lineSplit[1], lineSplit[2]);
                            try {
                                seller.remakeSellerFromFile();
                            } catch (Exception exception) {
                                System.out.println("The account you are trying to sign in to may" +
                                        "not exist.");
                                initialMainMenu();
                            }
                            initialSellerMenu(seller);
                            login = true;
                        } else if (lineSplit[1].equalsIgnoreCase(username) &&
                                lineSplit[3].equals(password) &&
                                lineSplit[0].equals("Student")) {
                            System.out.println(SUCCESSFULLOGIN);
                            Customer customer = new Customer(lineSplit[1], lineSplit[2]);
                            try {
                                customer.remakeCustomerFromFile();
                            } catch (Exception exception) {
                                System.out.println("The account you are trying to sign in to may" +
                                        "not exist.");
                                initialMainMenu();
                            }
                            customerMenu(customer);
                            login = true;
                        }
                    }
                }
                line = bufferedReader.readLine();
            }
            if (!login) {
                System.out.println(FAILEDLOGIN);
                initialMainMenu();
            }
            bufferedReader.close();
        } catch (Exception exception) {
            System.out.println(ERROR);
        }
    }

    public void createNewAccount() {
        boolean loop = true;
        boolean innerLoop = true;
        String newAccountName = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Users.txt"));
            String addLine = "";
            String line;
            ArrayList<String> usernames = new ArrayList<String>();
            do {
                System.out.println(NEWTUTORSTUDENT);
                setInput(scanner.nextLine());
                if (getInput().trim().equals("1")) {
                    addLine += "Tutor,";
                    loop = false;
                } else if (getInput().trim().equals("2")) {
                    addLine += "Student,";
                    loop = false;
                } else {
                    System.out.println(INVALID);
                }
            } while (loop);
            loop = true;
            line = bufferedReader.readLine();
            while (line != null) {
                if (line != null) {
                    usernames.add(line.split(",")[1].toLowerCase());
                }
                line = bufferedReader.readLine();
            }
            do {
                System.out.println(NEWUSERNAME);
                setInput(scanner.nextLine());
                for (int i = 0; i < usernames.size(); i++) {
                    if (getInput().equalsIgnoreCase(usernames.get(i))) {
                        innerLoop = false;
                    }
                }
                if (innerLoop) {
                    addLine += getInput();
                    addLine += ",";
                    newAccountName = getInput();
                    loop = false;
                } else {
                    System.out.println(USERNAMETAKEN);
                }
                innerLoop = true;
            } while (loop);
            loop = true;
            do {
                System.out.println(NEWEMAIL);
                setInput(scanner.nextLine());
                if (getInput().split("@").length == 2) {
                    if (getInput().split("@")[1].split("\\.").length == 2) {
                        addLine += getInput();
                        addLine += ",";
                        loop = false;
                    } else {
                        System.out.println(INVALIDEMAIL);
                    }
                } else {
                    System.out.println(INVALIDEMAIL);
                }
            } while (loop);
            loop = true;
            do {
                System.out.println(NEWPASSWORD);
                setInput(scanner.nextLine());
                if (input.trim().length() >= 6) {
                    addLine += getInput();
                    loop = false;
                } else {
                    System.out.println(INVALIDPASSWORD);
                }
            } while (loop);
            System.out.println(SUCCESSFULACCOUNTCREATION);
            try {
                if (!newAccountName.equals("")) {
                    File f = new File(newAccountName + ".txt");
                    f.createNewFile();
                }
            } catch (Exception exception) {
                System.out.println("An error has occurred");
                initialMainMenu();
            }
            System.out.println("The tutoring scheduler will close to create your account file.\n" +
                    "Please run again to sign in.");
            bufferedReader.close();
            File file = new File("Users.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            printWriter.println(addLine);
            printWriter.close();
        } catch (Exception exception) {
            System.out.println(ERROR);
        }
    }

    public void playVideo(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } catch (Exception exception) {
            System.out.println(ERROR);
        }
    }

    public void customerMenu(Customer customer) {
        customer.makeFileIfNotFound();
        System.out.println(CUSTOMERINITIALMENU);
        setInput(getScanner().nextLine());
        switch (getInput().trim()) {
            case "1" -> {
                appointmentRequest(customer);
                customerMenu(customer);
            }
            case "2" -> {
                listAppointmentsMenu(customer);
                customerMenu(customer);
            }
            case "3" -> {
                exportCustomerAppointments(customer);
                customerMenu(customer);
            }
            case "4" -> {
                statisticsMenu(customer);
                customerMenu(customer);
            }
            case "5" -> {
                initialMainMenu();
            }
            default -> {
                System.out.println(INVALID);
                customerMenu(customer);
            }
        }
    }

    public void appointmentRequest(Customer customer) {
        try {
            System.out.println(ENTERSTORENAME);
            String store = getScanner().nextLine();
            System.out.println(ENTERYEAR);
            int year = getScanner().nextInt();
            getScanner().nextLine();
            System.out.println(ENTERMONTH);
            int month = getScanner().nextInt();
            getScanner().nextLine();
            System.out.println(ENTERDAY);
            int day = getScanner().nextInt();
            getScanner().nextLine();
            System.out.println(ENTERHOUR);
            int hour = getScanner().nextInt();
            getScanner().nextLine();
            Session session = new Session(hour, day, month, year, store);
            customer.requestAppointment(session);
            customer.makeFileIfNotFound();
            customer.remakeFileFromCustomer();
            customerMenu(customer);
        } catch (Exception exception) {
            System.out.println(INVALID);
            appointmentRequest(customer);
        }
        customerMenu(customer);
    }

    public void listWaitingAppointments(Customer customer) {
        System.out.println("Waiting Appointments");
        for (int i = 0; i < customer.getWaitingRequest().size(); i++) {
            System.out.println("Session " + (i + 1) + ":");
            System.out.println(customer.getWaitingRequest().get(i).toString());
        }
    }

    public void listApprovedAppointments(Customer customer) {
        System.out.println("Approved Appointments");
        for (int i = 0; i < customer.getApprovedRequest().size(); i++) {
            System.out.println("Session " + (i + 1) + ":");
            System.out.println(customer.getApprovedRequest().get(i).toString());
        }
    }

    public void listAppointmentsMenu(Customer customer) {
        System.out.println(SEEAPPOINTMENTS);
        setInput(getScanner().nextLine());
        switch (input) {
            case "1" -> {
                listWaitingAppointments(customer);
            }
            case "2" -> {
                listApprovedAppointments(customer);
            }
            default -> {
                System.out.println(INVALID);
                listAppointmentsMenu(customer);
            }
        }
        customerMenu(customer);
    }

    public void statisticsMenu(Customer customer) {
        System.out.println(STATSMENU);
        setInput(getScanner().nextLine());
        switch (getInput()) {
            case "1" -> {
                printUnsortedStatsForCustomer(customer);
                statisticsMenu(customer);
            }
            case "2" -> {
                //TODO sorted stats
                System.out.println("Sorted stats has not been implemented");
                statisticsMenu(customer);
            }
            case "3" -> {
                customerMenu(customer);
            }
            default -> {
                System.out.println(INVALID);
                statisticsMenu(customer);
            }
        }
    }

    //Prints two lines for each store that exists.
    //Storename has x customers
    //Storename's most popular day is Sunday.
    public void printUnsortedStatsForCustomer(Customer customer) {
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                Store store = new Store(line.split(",")[0], line.split(",")[1]);
                store.remakeStoreFromFile();
                System.out.println("Store " + store.getName() + " owned by " + store.getSeller()
                        + " has " + store.numberOfCustomers() + " customers.");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("An error has occurred!");
            customerMenu(customer);
        }
    }

    public void exportCustomerAppointments(Customer customer) {
        try {
            File f = new File(customer.getName() + ".txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            ArrayList<String> approvedAppointmentStrings = new ArrayList<String>();
            while (line != null) {
                if (line.split(",")[0].equals("approved")) {
                    approvedAppointmentStrings.add(line);
                }
                line = bfr.readLine();
            }
            bfr.close();
            File file = new File(customer.getName() + "appointments.txt");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = "";
            add += "Format: hour, day, month, year, tutor name";
            for (int i = 0; i < approvedAppointmentStrings.size(); i++) {
                add += "\n";
                add += approvedAppointmentStrings.get(i);
            }
            printWriter.println(add);
            System.out.println("File " +
                    "containg approved appointments " +
                    "exported to " + customer.getName() + "appointments.txt!");
            printWriter.close();
        } catch (Exception exception) {
            System.out.println("An error occurred.");
            customerMenu(customer);
        }
        customerMenu(customer);
    }

    public void initialSellerMenu(Seller seller) {
        System.out.println(SELLERINITIALMENU);
        setInput(scanner.nextLine());
        switch (getInput().trim()) {
            case "1" -> {
                selectStore(seller);
                initialSellerMenu(seller);
            }
            case "2" -> {
                setupNewStore(seller);
                initialSellerMenu(seller);
            }
            case "3" -> {
                System.out.println("Store statistics:");
                for (int i = 0; i < seller.getStores().size(); i++) {
                    printStoreStatistics(seller.getStores().get(i));
                }
                initialMainMenu();
            }
            case "4" -> {
                System.out.println("Sorted store statistics");
                initialSellerMenu(seller);
            }
            case "5" -> {
                initialSellerMenu(seller);
            }
            default -> {
                System.out.println(INVALID);
                initialSellerMenu(seller);
            }
        }
    }

    public void selectStore(Seller seller) {
        String fileName = seller.getName() + ".txt";
        File file = new File(fileName);
        setInput(getScanner().nextLine());
        int index = Integer.parseInt(getInput());
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String storeName = bfr.readLine();
            int i = 0;
            System.out.println("Please select a store from the list below:");
            while (storeName != null) {
                System.out.println("Store " + (i + 1) + " is " + storeName);
                if (index == i + 1) {
                    Store store = new Store(storeName, seller.getName());
                    store.remakeStoreFromFile();
                    initialStoreMenu(seller, store);
                }
                i++;
                storeName = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            System.out.println("There is no file.");
            initialSellerMenu(seller);
        }
    }

    public void initialStoreMenu(Seller seller, Store store) {
        System.out.println(INITIALSTOREMENU);
        setInput(getScanner().nextLine());
        switch (getInput()) {
            case "1" -> {
                approveAppointments(store);
                initialStoreMenu(seller, store);
            }
            case "2" -> {
                declineAppointments(store);
                initialStoreMenu(seller, store);
            }

            case "3" -> {
                initialSellerMenu(seller);
            }
            default -> {
                System.out.println(INVALID);
                initialStoreMenu(seller, store);
            }
        }
    }

    public void declineAppointments(Store store) {
        int n = 0;
        ArrayList<String> customers = new ArrayList<String>();
        ArrayList<Session> sessions = new ArrayList<Session>();
        System.out.println("Please select an appointment to approve " +
                "by inputting the corresponding number");
        for (int i = 0; i < store.getWaitingRequest().size(); i++) {
            for (int j = 0; j < store.getWaitingRequest().get(i).getWaitingCustomers().size(); j++) {
                n++;
                System.out.println("Student " +
                        store.getWaitingRequest().get(i).getWaitingCustomers().get(j)
                        + " is waiting for approval for sesson " +
                        store.getWaitingRequest().get(i).toString() +
                        "\n Please enter " +
                        n + "to approve.");
            }
        }
        setInput(getScanner().nextLine());
        try {
            System.out.println("Please input customer email to approve request.");
            String email = getScanner().nextLine();
            Customer customer = new Customer(customers.get(Integer.parseInt(getInput()) - 1), email);
            customer.remakeCustomerFromFile();
            store.decline(sessions.get(Integer.parseInt(getInput()) - 1), customer);
            customer.remakeFileFromCustomer();
            store.makeFileFromStore();
        } catch (Exception exception) {
            System.out.println("Invalid input, please try again");
        }
    }

    public void approveAppointments(Store store) {
        int n = 0;
        ArrayList<String> customers = new ArrayList<String>();
        ArrayList<Session> sessions = new ArrayList<Session>();
        System.out.println("Please select an appointment to approve " +
                "by inputting the corresponding number");
        for (int i = 0; i < store.getWaitingRequest().size(); i++) {
            for (int j = 0; j < store.getWaitingRequest().get(i).getWaitingCustomers().size(); j++) {
                n++;
                System.out.println("Student " +
                        store.getWaitingRequest().get(i).getWaitingCustomers().get(j)
                        + " is waiting for approval for sesson " +
                        store.getWaitingRequest().get(i).toString() +
                        "\n Please enter " +
                        n + "to approve.");
            }
        }
        setInput(getScanner().nextLine());
        try {
            System.out.println("Please input customer email to approve request.");
            String email = getScanner().nextLine();
            Customer customer = new Customer(customers.get(Integer.parseInt(getInput()) - 1), email);
            customer.remakeCustomerFromFile();
            store.approve(sessions.get(Integer.parseInt(getInput()) - 1), customer);
            customer.remakeFileFromCustomer();
            store.makeFileFromStore();
        } catch (Exception exception) {
            System.out.println("Invalid input, please try again");
        }
    }

    public void setupNewStore(Seller seller) {
        System.out.println("Please enter a name for your new store");
        String name = getScanner().nextLine();
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            boolean storeNameValid = true;
            while (line != null && storeNameValid) {
                if (line.split(",")[0].equalsIgnoreCase(name)) {
                    storeNameValid = false;
                }
            }
            if (storeNameValid) {
                Store store = new Store(name, seller.getName());
                store.makeCsvFromTxt();
                store.importFromCsv();
                store.setupStoreInputChecks(store.getIsOpen(),
                        store.getOpeningTimes(), store.getClosingTimes(),
                        store.getCapacities(), store.getLocations());
                store.setupStore();
                store.makeFileFromStore();
                System.out.println("Your new store " + name + "has been created!");
                System.out.println("The tutoring scheduler will close to create your new store file.\n" +
                        "Please run again to access your new store");
                close();
            } else {
                System.out.println("This store name is taken!");
            }
            bufferedReader.close();
        } catch (Exception exception) {
            System.out.println("An error has occurred");
            //
            System.out.println("Running test");
            exception.printStackTrace();
            //
            setupNewStore(seller);
        }
    }

    public void close() {
        System.out.println(EXIT);
    }

    public void printStoreStatistics(Store store) {
        System.out.println("The most popular day for the store " +
                store.getName() +
                " is " +
                store.mostPopularAp(store.getName(), store.getSeller()));
        System.out.println("This store has " +
                store.getUniqueCustomers() +
                " customers.\n");
    }
}