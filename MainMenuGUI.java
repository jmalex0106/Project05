import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;

public class MainMenuGUI implements Runnable {
    public static final String[] TUTOR_STUDENT = new String[]{"Student", "Tutor"};
    public static final String[] appointment = {"select"}; //TODO delete this when ready
    private static final String[] STATISTICS = {"View sorted statistics", "View unsorted statistics"};
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public MainMenuGUI() {
        try {
            socket = new Socket("localhost", 1234);
            this.objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            System.out.println("CONSTRUCTED 0");
            this.objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
            System.out.println("CONSTRUCTED 1");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect with server");
        }
    }

    public void playGUI() {
        SwingUtilities.invokeLater(new MainMenuGUI());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Log in");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu - panel1
        JPanel panel1 = new JPanel(new GridLayout(3, 3)); // 2x3

        // // Username
        JLabel usernameLabel = new JLabel("Username: ");
        panel1.add(usernameLabel); // (1,1)
        JTextField usernameTextField = new JTextField(10);
        panel1.add(usernameTextField); // (1,2)

        // // log in button

        // Here, we classify if the user is a tutor or a student, then jump to corresponding GUI
        JButton loginButton = new JButton("Log in");
        panel1.add(loginButton); // (1,3)

        // // Password
        JLabel passwordLabel = new JLabel("Password: ");
        panel1.add(passwordLabel);// (2,1)
        JTextField passwordTextField = new JTextField(10);
        panel1.add(passwordTextField); // (2,2)

        // // sign in button
        JButton signInButton = new JButton("Create new account");
        panel1.add(signInButton); // (2,3)

        // Mystery option
        JButton mysteryOption = new JButton("Mystery Option");
        mysteryOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO find video URL to input and set String videoURL to the URL
                String videoURL = "https://www.youtube.com/watch?v=dAyXuLNxOfE";
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(new URI(videoURL));
                    frame.dispose();
                } catch (Exception exception) {
                    frame.dispose();
                }
            }
        });

        JLabel emailLabel = new JLabel("Email: ");
        panel1.add(emailLabel); // (3,1)

        //email input text field
        JTextField emailTextField = new JTextField(10);
        panel1.add(emailTextField); // (3,2)

        // // exit
        panel1.add(mysteryOption);// (3,3)

        content.add(panel1, BorderLayout.CENTER);

        // frame setting
        frame.setResizable(false);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button pressed");
                // grab input information
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                String[] credentials = new String[3];
                String[] requestCustomer = new String[2];
                String[] requestSeller = new String[2];
                requestCustomer[0] = "requestCustomer";
                requestSeller[0] = "requestSeller";
                credentials[0] = "loginCredentials";
                credentials[1] = username;
                credentials[2] = password;
                requestCustomer[1] = username;
                requestSeller[1] = username;
                int login = 0;
                System.out.println("TESTLINE");
                try {
                    System.out.println("TRY");
                    System.out.println("CONSTRUCTED 1");
                    objectOutputStream.writeObject(credentials);
                    objectOutputStream.flush();
                    System.out.println("WRITTEN");
                    Object loginObject = objectInputStream.readObject();
                    System.out.println("Login Object " + loginObject);
                    if (loginObject instanceof Integer) {
                        login = (Integer) loginObject;
                    }
                    if (login == 0) {
                        JOptionPane.showMessageDialog(frame,
                                "Invalid credentials entered. Please try again");
                    } else if (login == 1) {
                        objectOutputStream.writeObject(requestSeller);
                        objectOutputStream.flush();
                        Object sellerObject = objectInputStream.readObject();
                        System.out.println(sellerObject);
                        if (sellerObject instanceof Seller) {
                            System.out.println("OBJECT IS SELLER");
                            Seller seller = (Seller) sellerObject;
                            System.out.println(seller.getName());
                            frame.dispose();
                            System.out.println("MAKING GUI");
                            objectOutputStream.close();
                            objectInputStream.close();
                            playSellerMenuGUI(seller);
                        }
                    } else if (login == 2) {
                        System.out.println("LOGIN = 2 RUNNING");
                        objectOutputStream.writeObject(requestCustomer);
                        objectOutputStream.flush();
                        System.out.println("FLUSHED");
                        Object customerObject = objectInputStream.readObject();
                        System.out.println(customerObject);
                        if (customerObject instanceof Customer) {
                            System.out.println("OBJECT IS CUSTOMER");
                            Customer customer = (Customer) customerObject;
                            System.out.println(customer.getName());
                            frame.dispose();
                            playCustomerMenuGUI(customer);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                String email = emailTextField.getText();
                int isTutorInt = JOptionPane.showOptionDialog(frame, "Select whether your account is " +
                                "for a tutor or a student:",
                        "Create Account",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, TUTOR_STUDENT, TUTOR_STUDENT[0]);
                frame.dispose();
                boolean isTutor = false;
                if (isTutorInt == 1) {
                    isTutor = true;
                }
                String[] newAccountCreation = new String[5];
                newAccountCreation[0] = "newAccountCreation";
                newAccountCreation[1] = String.valueOf(isTutor);
                newAccountCreation[2] = username;
                newAccountCreation[3] = email;
                newAccountCreation[4] = password;
                boolean accountCreated = false;
                try {
                    objectOutputStream.writeObject(newAccountCreation);
                    objectOutputStream.flush();
                    Object newAccountBoolObject = objectInputStream.readObject();
                    if (newAccountBoolObject instanceof Boolean) {
                        accountCreated = (Boolean) newAccountBoolObject;
                        System.out.println("ACCOUNT BOOLEAN = " + (Boolean) newAccountBoolObject);
                    }
                    if (accountCreated) {
                        JOptionPane.showMessageDialog(null,
                                "Account created successfully for " + username +
                                        "\n Please log in!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid account information\n" +
                                "Please ensure your username is unique, your password is long enough, and your" +
                                " email is valid.");
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame, "connection error with server");
                }
                playGUI();
            }
        });
    }

    //SELLER MENU GUI CODE BELOW
    public Store getStoreWithName(String storeName, Seller seller) {
        for (Store store : seller.getStores()) {
            if (store.getName().equals(storeName)) {
                return store;
            }
        }
        return seller.getStores().get(0);
    }

    public String[] listAllStores(Seller seller) {
        String[] output = new String[seller.getStores().size()];
        for (int i = 0; i < seller.getStores().size(); i++) {
            output[i] = seller.getStores().get(i).getName();
        }
        return output;
    }

    public void playSellerMenuGUI(Seller seller) {
        JFrame frame = new JFrame("Welcome " + seller.getName() + "!");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel - Panel1
        JPanel panel1 = new JPanel(new GridLayout(3, 3)); // 3x3
        // GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // // View Store
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 0;
        JLabel labelStore = new JLabel("View Stores: ");
        panel1.add(labelStore); // (1,1)

        // gridBagConstraints.gridx = 1;
        JComboBox<String> storeDropdown = new JComboBox<>(listAllStores(seller));
        panel1.add(storeDropdown); // (1,2)

        // gridBagConstraints.gridx = 2;
        JButton confirmStore = new JButton("Confirm"); // add action listener
        confirmStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listAllStores(seller).length == 0) {
                    JOptionPane.showMessageDialog(frame, "You have no stores");
                } else {
                    playSellerStoreGUI(seller ,
                            getStoreWithName(
                                    storeDropdown.getSelectedItem().toString(), seller));
                    frame.dispose();
                }
            }
        });
        panel1.add(confirmStore); // (1, 3)

        // View Statistics
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 1;

        JLabel labelStatistics = new JLabel("View Statistics: ");
        panel1.add(labelStatistics); // (2, 1)

        // gridBagConstraints.gridx = 1;
        JComboBox<String> statDropdown = new JComboBox<>(STATISTICS);
        panel1.add(statDropdown); // (2,2)

        // gridBagConstraints.gridx = 2;
        JButton confirmStat = new JButton("Confirm");
        confirmStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] sellerRequestStatistics = new String[3];
                sellerRequestStatistics[0] = "sellerRequestStatistics";
                sellerRequestStatistics[1] = statDropdown.getSelectedItem().toString();
                sellerRequestStatistics[2] = seller.getName();
                if (listAllStores(seller).length == 0) {
                    JOptionPane.showMessageDialog(frame, "You have no stores");
                } else {
                    try {
                        objectOutputStream.writeObject(sellerRequestStatistics);
                        objectOutputStream.flush();
                        Object statisticsObject = objectInputStream.readObject();
                        if (statisticsObject instanceof String) {
                            String statistics = (String) statisticsObject;
                            JOptionPane.showMessageDialog(frame,
                                    statistics);
                        }
                    } catch (IOException | ClassNotFoundException ioException) {
                        JOptionPane.showMessageDialog(frame, "Connection with server failed");
                    }
                }
            }
        });
        panel1.add(confirmStat); // (2,3)

        // // Open New Store
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 2;

        JButton openNewStore = new JButton("Open new Store");
        openNewStore.setSize(50, 50);
        openNewStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    objectOutputStream.close();
                    objectInputStream.close();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame, "Connection error with server");
                }
                //playOpenNewStoreGUI(seller); TODO make this work and delete code below
                System.out.println("playing open new store gui");
                playSellerMenuGUI(seller);
                //TODO make this work and delete code above
            }
        });
        panel1.add(openNewStore); // (3,1)

        // // Return to Main Menu
        // gridBagConstraints.gridy = 2;
        // gridBagConstraints.gridx = 2;
        JButton returnMain = new JButton("Main Menu");
        returnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                playGUI();
            }
        });
        panel1.add(returnMain); // (3,2)

        // // Exit
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel1.add(exit); // (3,3)

        content.add(panel1, BorderLayout.CENTER);

        // set Frame
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //CUSTOMER MENU GUI CODE BELOW
    public void playCustomerMenuGUI(Customer customer) {
        JFrame frame = new JFrame("Welcome " + customer.getName());

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel - Panel1
        JPanel panel1 = new JPanel(new GridLayout(3, 3)); // 3x3

        JButton exportToCSV = new JButton("Export appointments\nto CSV");
        exportToCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer gets CSV file and creates file
                try {
                    String[] customerRequestCSV = new String[1];
                    customerRequestCSV[0] = "customerRequestCSV";
                    CustomerStringPacket customerStringPacket =
                            new CustomerStringPacket(customer , customerRequestCSV);
                    objectOutputStream.writeObject(customerStringPacket);
                    Object csvObject = objectInputStream.readObject();
                    if (csvObject instanceof File) {
                        File csvFile = (File) csvObject;
                        csvFile.createNewFile();
                        String cvsFilePath = csvFile.getPath();
                        JOptionPane.showMessageDialog(frame,
                                "CSV with your approved appointments has been made at "
                                        + cvsFilePath);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "connection error with server");
                }
            }
        });
        panel1.add(exportToCSV); // (2,3)

        // gridBagConstraints.gridx = 2;
        JButton viewSortedStat = new JButton("View sorted\n statistics");
        viewSortedStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] customerRequestSortedStats = new String[1]; //packet
                    customerRequestSortedStats[0] = "customerRequestSortedStats";
                    objectOutputStream.writeObject(customerRequestSortedStats);
                    objectOutputStream.flush();
                    Object sortedStatsObject = objectInputStream.readObject();
                    if (sortedStatsObject instanceof String) {
                        String sortedStats = (String) sortedStatsObject;
                        JOptionPane.showMessageDialog(frame,
                                sortedStats);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame ,
                            "Connection error with server");
                }
            }
        });
        panel1.add(viewSortedStat); // (2,3)

        JButton cancelAppointment = new JButton("Cancel Appointment");
        cancelAppointment.setSize(50, 50);
        cancelAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playCustomerCancelAppointmentGUI(customer);
            }
        });
        panel1.add(cancelAppointment); // (2,1)

        // gridBagConstraints.gridx = 2;
        JButton viewUnsortedStat = new JButton("View unsorted\n statistics");
        viewUnsortedStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] customerRequestUnsortedStats = new String[1]; //packet
                    customerRequestUnsortedStats[0] = "customerRequestUnsortedStats";
                    objectOutputStream.writeObject(customerRequestUnsortedStats);
                    objectOutputStream.flush();
                    Object unsortedStatsObject = objectInputStream.readObject();
                    if (unsortedStatsObject instanceof String) {
                        String unsortedStats = (String) unsortedStatsObject;
                        JOptionPane.showMessageDialog(frame,
                                unsortedStats);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame ,
                            "Connection error with server");
                }
            }
        });
        panel1.add(viewUnsortedStat); // (2,3)

        // // Open New Store
        JButton requestNewAppointment = new JButton("Request New Appointment");
        requestNewAppointment.setSize(50, 50);
        requestNewAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                playCustomerSelectStoreGUI(customer);
            }
        });
        panel1.add(requestNewAppointment);// (3,1)

        // // Return to Main Menu

        JButton returnMain = new JButton("Main Menu");
        returnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                playGUI();
            }
        });
        panel1.add(returnMain); // (3,2)

        // // Exit
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel1.add(exit); // (3,3)
        content.add(panel1, BorderLayout.CENTER);

        // set Frame
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //CUSTOMER CANCEL APPOINTMENT GUI CODE BELOW
    public void playCustomerCancelAppointmentGUI(Customer customer) {
        JFrame frame = new JFrame("Cancel Appointment");

        Container content = frame.getContentPane();

        content.setLayout(new

                GridLayout(2, 1)); // 2x1

        String[] appointment = new String[customer.getApprovedRequest().size()];
        for (int i = 0; i < customer.getApprovedRequest().size(); i++) {
            appointment[i] = "waiting " + (customer.getWaitingRequest().get(i).getMonth() + 1) +
                    "/" + customer.getWaitingRequest().get(i).getDay() +
                    " " + customer.getWaitingRequest().get(i).getYear() +
                    " at store " +
                    customer.getWaitingRequest().get(i).getStore();
        }

        // Dropbox
        JComboBox<String> appointmentDropdown = new JComboBox<>(appointment);
        content.add(appointmentDropdown);

        // JPanel with 2 buttons
        JPanel panel = new JPanel(new GridLayout(1, 2)); // 1x2
        JButton confirmButton = new JButton("Confirm");

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                playCustomerMenuGUI(customer);
            }
        });

        panel.add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedAppointment = appointmentDropdown.getSelectedIndex();
                String selectedAppointmentString = String.valueOf(selectedAppointment);
                String[] cancelAppointment = new String[2];
                cancelAppointment[0] = "customerCancelAppointment";
                cancelAppointment[1] = selectedAppointmentString;
                CustomerStringPacket customerStringPacket =
                        new CustomerStringPacket(customer , cancelAppointment);
                try {
                    objectOutputStream.writeObject(customerStringPacket);
                    objectOutputStream.flush();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame ,
                            "Connection error with server");
                }
                JOptionPane.showMessageDialog(frame, "Appointment cancelled successfully");
                frame.dispose();
                playCustomerMenuGUI(customer);
            }
        });
        panel.add(backButton);

        content.add(panel);

        // frame set
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //CUSTOMER SELECT STORE GUI CODE BELOW
    public void playCustomerSelectStoreGUI(Customer customer) {
        JFrame frame = new JFrame("Select store");

        Container content = frame.getContentPane();

        content.setLayout(new GridLayout(2, 1)); // 2x1

        String[] allStoreNames = new String[]{"An error has occurred"};
        String[] requestAllStoreNames = new String[1];
        requestAllStoreNames[0] = "requestAllStoreNames";
        try {
            objectOutputStream.writeObject(requestAllStoreNames);
            objectOutputStream.flush();
            Object allStoreNamesObject = objectInputStream.readObject();
            if (allStoreNamesObject instanceof String[]) {
                allStoreNames = (String[]) allStoreNamesObject;
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(frame ,
                    "connection error with server");
        }
        JComboBox<String> storeDropdown = new JComboBox<>(allStoreNames);
        content.add(storeDropdown);

        // JPanel with 2 buttons
        JPanel panel = new JPanel(new GridLayout(1, 2)); // 1x2
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeDropdown.getSelectedItem().toString();
                String[] customerRequestStore = new String[2];
                customerRequestStore[0] = "customerRequestStore";
                customerRequestStore[1] = storeName;
                try {
                    objectOutputStream.writeObject(customerRequestStore);
                    objectOutputStream.flush();
                    Object storeObject = objectInputStream.readObject();
                    if (storeObject instanceof Store) {
                        Store store = (Store) storeObject;
                        frame.dispose();
                        playNewAppointmentRequestGUI(customer , store);
                    } else {
                        JOptionPane.showMessageDialog(frame ,
                                "Connection error with server");
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame ,
                            "Connection error with server");
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                playCustomerMenuGUI(customer);
            }
        });

        panel.add(confirmButton);
        panel.add(backButton);

        content.add(panel);

        // frame set
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //NEW APPOINTMENT REQUEST GUI CODE BELOW
    public void playNewAppointmentRequestGUI(Customer customer, Store store) {
        JFrame frame = new JFrame("Request a new appointment at " + store.getName());

        Container content = frame.getContentPane();

        content.setLayout(new GridLayout(1, 2));

        // left panel
        JPanel panel1 = new JPanel(new GridLayout(5, 1)); // 5x1

        JTextField yearTextField = new JTextField(5);
        yearTextField.setText("Year");

        JTextField monthTextField = new JTextField(5);
        monthTextField.setText("Month");

        JTextField dayTextField = new JTextField(5);
        dayTextField.setText("Day");

        JTextField hourField = new JTextField(5);
        hourField.setText("Hour");

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String year = yearTextField.getText().trim();
                String month = yearTextField.getText().trim();
                String day = yearTextField.getText().trim();
                String hour = yearTextField.getText().trim();
                int success = 100;
                String[] customerRequestAppointment = new String[6];
                customerRequestAppointment[0] = "customerRequestAppointment";
                customerRequestAppointment[1] = year;
                customerRequestAppointment[2] = month;
                customerRequestAppointment[3] = day;
                customerRequestAppointment[4] = hour;
                customerRequestAppointment[5] = store.getName();
                CustomerStringPacket customerStringPacket = new CustomerStringPacket(
                        customer , customerRequestAppointment);
                try {
                    objectOutputStream.writeObject(customerStringPacket);
                    objectOutputStream.flush();
                    Object intObject = objectInputStream.readObject();
                    if (intObject instanceof Integer) {
                        Integer successInt = (Integer) intObject;
                        success = successInt;
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame ,
                            "Connection error with server");
                }

                switch (success) {
                    case 0 -> {
                        JOptionPane.showMessageDialog(frame,
                                "You have been added to the waitlist successfully");
                    }
                    case 1 -> {
                        JOptionPane.showMessageDialog(frame,
                                "This session is at or over capacity. You have been added " +
                                        "to the waitlist, \nbut the " +
                                        "tutor may not accept your request.");
                    }
                    case 2 -> {
                        JOptionPane.showMessageDialog(frame,
                                "Some fields were left blank. Please fill in all fields. " +
                                        "Waitlist has not been updated.");
                    }
                    case 3 -> {
                        JOptionPane.showMessageDialog(frame,
                                "The store entered does not exist. " +
                                        "Waitlist has not been updated.");
                    }
                    case 4 -> {
                        JOptionPane.showMessageDialog(frame,
                                "The date entered is in the past or is invalid. " +
                                        "Please enter a valid date.");
                    }
                    case 5 -> {
                        JOptionPane.showMessageDialog(frame,
                                "The store you have entered is closed on the date and " +
                                        "time you have entered.\n" +
                                        "Please enter a valid date. " +
                                        "Waitlist has not been updated.");
                    }
                    default -> {
                        JOptionPane.showMessageDialog(frame,
                                "An unexpected error has occurred with the server");
                    }
                }
                frame.dispose();
                playCustomerMenuGUI(customer);
            }
        });

        panel1.add(yearTextField);
        panel1.add(monthTextField);
        panel1.add(dayTextField);
        panel1.add(hourField);
        panel1.add(confirmButton);

        content.add(panel1); //left

        // right panel
        JPanel panel2 = new JPanel(new GridBagLayout()); // span multiple cells

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 220;
        gridBagConstraints.ipady = 115;

        JLabel storeInfoLabel = new JLabel("10", SwingConstants.LEFT);

        storeInfoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel2.add(storeInfoLabel, gridBagConstraints);


        // DON'T  Change these parameters ----------------------------------
        // where the next component is
        gridBagConstraints.gridy = 500;
        // modify width and height of the component

        gridBagConstraints.ipady = confirmButton.getHeight() + 4;
        gridBagConstraints.ipadx = 175;
        // DON'T  Change these parameters ----------------------------------


        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                playCustomerMenuGUI(customer);
            }
        });
        panel2.add(backButton, gridBagConstraints);


        content.add(panel2); // right

        //set frame
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //SELLER STORE GUI CODE BELOW
    public void playSellerStoreGUI(Seller seller , Store store) {
        JFrame frame = new JFrame("Welcome to " + store.getName());

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel
        JPanel panel1 = new JPanel(new GridLayout(2, 2)); // 2x2

        String[] appList = new String[store.getSessions().size()];
        for (int i = 0; i < store.getSessions().size(); i++) {
            appList[i] = (store.getSessions().get(i).getMonth() + 1) +
                    "/" + store.getSessions().get(i).getDay() +
                    " " + store.getSessions().get(i).getYear() +
                    " at " + store.getSessions().get(i).getHour() +
                    ":00";
        }
        if (store.getSessions().size() == 0 ) {
            appList = new String[]{"This store has no waiting or approved customers"};
        }
        JComboBox<String> appointmentDropDown = new JComboBox<>(appList);
        panel1.add(appointmentDropDown); // (1,1)

        JButton appointmentConfirm = new JButton("Confirm");
        appointmentConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int appointmentSelected = appointmentDropDown.getSelectedIndex();
                Session session = store.getSessions().get(appointmentSelected);
                frame.dispose();
                playSessionGUI(seller , store , session);
            }
        });
        panel1.add(appointmentConfirm); // (1,2)

        JButton backToTutor = new JButton("Back to Tutor");
        backToTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSellerMenuGUI(seller);
                frame.dispose();
            }
        });
        panel1.add(backToTutor); // (2,1)

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel1.add(exit); // (2,2)


        content.add(panel1, BorderLayout.CENTER);

        // set frame
        frame.setSize(600, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //SESSION GUI CODE BELOW
    public void playSessionGUI(Seller seller , Store store , Session session) {
        JFrame frame = new JFrame("Welcome to session at " + (session.getMonth()+1) +
                "/" + session.getDay() + " " + session.getYear() + " at store " +
                session.getStore());

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        //Main Panel
        JPanel panel1 = new JPanel(new GridLayout(3, 3)); // (3x3)

        JLabel waitingListLabel = new JLabel("View Waiting List: ");
        panel1.add(waitingListLabel); // (1,1)

        JComboBox<String> waitingListDropdown = new JComboBox<>(session.getWaitingCustomers().toArray(new String[0]));
        panel1.add(waitingListDropdown); // (1,2)

        JButton approveButton = new JButton("Approve");
        panel1.add(approveButton); // (1,3)

        JLabel approveListLabel = new JLabel("View Approved List: ");
        panel1.add(approveListLabel); // (2,1)

        JComboBox<String> approvedListDropdown = new JComboBox<>(session.getEnrolledCustomers().toArray(new String[0]));
        panel1.add(approvedListDropdown); // (2,2)

        JButton denyButton = new JButton("Deny");
        panel1.add(denyButton); // (2,3)

        JButton rescheduleButton = new JButton("Reschedule");
        panel1.add(rescheduleButton); // (3,1)

        JLabel placeholder = new JLabel();
        panel1.add(placeholder); // (3,2)

        JButton back = new JButton("back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                playSellerStoreGUI(seller , store);
            }
        });
        panel1.add(back);

        content.add(panel1, BorderLayout.CENTER);

        // Max capacity, Current # of approved & Start time - End time
        JPanel panel2 = new JPanel(new GridLayout(3, 1)); // 3x1
        // // Max capacity
        String maxCapacity = "";
        JLabel maxCapacityLabel = new JLabel("Max Capacity: " + maxCapacity);
        panel2.add(maxCapacityLabel); // (1,1)
        // // Current # of approved
        String numberOfApproved = "";
        JLabel numberOfApprovedLabel = new JLabel("Current Number of Approved: " + numberOfApproved);
        panel2.add(numberOfApprovedLabel); // (2,1)
        // Start time - End time
        String startTime = String.valueOf(session.getHour()) + ":00";
        String endTime = String.valueOf(session.getHour() + 1) + ":00";
        JLabel startAndEndTimeLabel = new JLabel(startTime + " - " + endTime);
        panel2.add(startAndEndTimeLabel);

        content.add(panel2, BorderLayout.NORTH);


        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //SELLER CANCEL APPOINTMENT GUI CODE BELOW
    //TODO what is this gui for?
    public void playSellerCancelAppointmentGUI(Seller seller) {
        JFrame frame = new JFrame("Cancel Appointment");

        Container content = frame.getContentPane();

        content.setLayout(new GridLayout(2, 1)); // 2x1

        // Dropbox
        JComboBox<String> appointmentDropdown = new JComboBox<>(appointment);
        content.add(appointmentDropdown);

        // JPanel with 2 buttons
        JPanel panel = new JPanel(new GridLayout(1, 2)); // 1x2
        JButton confirmButton = new JButton("Confirm");

        JButton backButton = new JButton("Back");

        panel.add(confirmButton);
        panel.add(backButton);

        content.add(panel);

        // frame set
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    //OPEN NEW STORE GUI CODE BELOW
    /*
    public void playOpenNewStoreGUI(Seller seller) {
        JFrame jFrame = new JFrame("Open new store for " + seller.getName());
        Container content = jFrame.getContentPane();
        content.setLayout(new GridLayout(5, 1)); // 2x1
        JLabel storeName = new JLabel("enter a new store name");
        JLabel csvPath = new JLabel("enter the path to a CSV file");
        JTextField storeNameInput = new JTextField();
        JTextField csvPathInput = new JTextField();
        JButton openStore = new JButton("confirm open store");
        JButton back = new JButton("back");
        openStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO make this button work
                String storeNameString = storeNameInput.getText();
                String csvPathString = csvPathInput.getText();
                if (csvPathString != null && storeNameString != null) {
                    try {
                        File file = new File(csvPathString);
                        if (!file.exists()) {
                            JOptionPane.showMessageDialog(jFrame , "The file path entered" +
                                    " is invalid , please try again");
                            jFrame.dispose();
                            playSellerMenuGUI(seller);
                        } else {
                            String[] tags = new String[1];
                            tags[0] = storeNameInput.getText();
                            FileStringPacket fileStringPacket = new FileStringPacket(file ,
                                    tags , seller);
                            objectOutputStream.writeObject(fileStringPacket);
                            objectOutputStream.flush();
                            Object object = objectInputStream.readObject();
                            if (object instanceof SellerIntegerPacket) {
                                SellerIntegerPacket sellerIntegerPacket =
                                        (SellerIntegerPacket) object;
                                seller = sellerIntegerPacket.getSeller();
                                int openedNewStore = sellerIntegerPacket.getInteger();
                                if (openedNewStore == 0) {
                                    JOptionPane.showMessageDialog(jFrame , "Store " +
                                            storeNameInput.getText() + " opened for seller " +
                                            seller.getName());
                                    jFrame.dispose();
                                    playSellerMenuGUI(seller);
                                } else if (openedNewStore == 1) {
                                    JOptionPane.showMessageDialog(jFrame ,
                                            "this store name is already taken");
                                } else {
                                    JOptionPane.showMessageDialog(jFrame ,
                                            "An error occurred importing your csv file");
                                }
                            } else {
                                JOptionPane.showMessageDialog(jFrame ,
                                        "Connection error with server");
                            }
                        }
                    } catch (IOException | ClassNotFoundException ioException) {
                        JOptionPane.showMessageDialog(jFrame , "Connection error with server");
                        jFrame.dispose();
                        new SellerMenuGUI(seller , socket);
                    }
                } else {
                    JOptionPane.showMessageDialog(jFrame , "Some fields where left blank.");
                    jFrame.dispose();
                    new SellerMenuGUI(seller , socket);
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                try {
                    socket.close();
                    objectInputStream.close();
                    objectOutputStream.close();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(jFrame ,
                            "Connection error with server");
                }
                playSellerMenuGUI(seller);
            }
        });
        jFrame.add(storeName);
        jFrame.add(storeNameInput);
        jFrame.add(csvPath);
        jFrame.add(csvPathInput);
        jFrame.add(openStore);
        jFrame.add(back);
        jFrame.setVisible(true);
    }*/
}
