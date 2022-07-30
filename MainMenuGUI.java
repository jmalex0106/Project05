import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class MainMenuGUI implements Runnable {
    public MainMenuGUI() {
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
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO make the create new account button work. This button needs to:
                /**
                 * 1. Display a JOptionPane that lets the user select if the new account is for a tutor or a student
                 * 2. Send the values entered in the username, password, and email boxes to the server
                 * 3. Recieve a boolean from the server
                 * 4. Display a JOptionPane with message depending on this boolean
                 */
                frame.dispose();
                new MainMenuGUI().playGUI();
            }
        });
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
                    new MainMenuGUI().playGUI();
                } catch (Exception exception) {
                    frame.dispose();
                    new MainMenuGUI().playGUI();
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
                // grab input information
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                //TODO send username and password to server and recieve and int back
                //TODO from server and set login to this int
                int login = 2;
                if (login == 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Invalid credentials entered. Please try again");
                } else if (login == 1) {
                    //TODO receive the appropriate seller from server and set seller to that seller
                    Seller seller = new Seller("Bob");
                    frame.dispose();
                    new SellerMenuGUI(seller).playGUI();
                } else if (login == 2) {
                    //TODO receive the appropriate customer from server and set customer
                    Customer customer = new Customer("Tom");
                    //TODO play customerMenuGUI
                    frame.dispose();
                }
            }
        });
    }
}