import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainMenuGUI implements Runnable {

    public static void main(String[] args) {
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
        JButton signInButton = new JButton("Sign in");
        panel1.add(signInButton); // (2,3)

        // Mystery option
        JButton mysteryOption = new JButton("Mystery Option");
        panel1.add(mysteryOption); // (3,1)

        // empty label
        JLabel emptyLabel = new JLabel();
        panel1.add(emptyLabel); // (3,2)

        // // exit
        JButton exitButton = new JButton("Exit");
        panel1.add(exitButton); // (3,3)

        content.add(panel1, BorderLayout.CENTER);

        // frame setting
        frame.setResizable(false);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // grab input information
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                // import Users.txt //
                // Tutor,MoxiaoLi,moxiao@gmail.com,mo12345 . length = 4

                File file = new File("Users.txt");
                FileReader fr = null;
                try {
                    fr = new FileReader(file);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to find user data.","Appointment System",JOptionPane.ERROR_MESSAGE);
                }
                BufferedReader bfr = new BufferedReader(fr);

                try {
                    String line = bfr.readLine();
                    while (line != null){
                        String[] userInfo = line.split(",");


                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to import user data","Appointment System",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}