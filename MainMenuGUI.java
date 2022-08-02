import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;

public class MainMenuGUI implements Runnable {
    public static final String[] TUTOR_STUDENT = new String[]{"Student", "Tutor"};
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public MainMenuGUI() {
        try {
            socket = new Socket("localhost",1234);
            this.objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            System.out.println("CONSTRUCTED 0");
            this.objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
            System.out.println("CONSTRUCTED 1");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null , "Failed to connect with server");
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
                //TODO send username and password to server and receive and int back
                //TODO from server and set login to this int
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
                            new SellerMenuGUI(seller , socket).playGUI();
                        }
                    } else if (login == 2) {
                        System.out.println("LOGIN = 2 RUNNING");
                        //TODO receive the appropriate customer from server and set customer
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
                            new CustomerMenuGUI(customer , socket).playGUI();
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
                    JOptionPane.showMessageDialog(frame , "connection error with server");
                }
                new MainMenuGUI().playGUI();
            }
        });
    }
}