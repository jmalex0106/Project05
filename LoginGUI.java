import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI implements Runnable{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LoginGUI());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Log in");

        Container content = new JFrame().getContentPane();

        content.setLayout(new BorderLayout());

        // Menu - panel1
        JPanel panel1 = new JPanel(new GridLayout(3,2)); // 3x2

        // // Username
        JLabel usernameLabel = new JLabel("Username: ");
        panel1.add(usernameLabel); // (1,1)
        JTextField usernameTextField = new JTextField(10);
        panel1.add(usernameTextField); // (1,2)

        // // Password
        JLabel passwordLabel = new JLabel("Password: ");
        panel1.add(passwordLabel);// (2,1)
        JTextField passwordTextField = new JTextField(10);
        panel1.add(passwordTextField); // (2,2)

        // // login button
        JButton loginButton = new JButton("login");
        panel1.add(loginButton);

        // // exit
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel1.add(exitButton);

        //

        content.add(panel1, BorderLayout.CENTER);

        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);




    }
}
