import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMenuGUI implements Runnable {
    private static  String[] Session = {"Select Session", "Indiana University"};
    private static  String[] Statistics = {"The most popular appointment", "other options"};

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CustomerMenuGUI());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Welcome Student");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel - Panel1
        JPanel panel1 = new JPanel(new GridLayout(3,3)); // 3x3

        // // View Store
        JLabel labelSession = new JLabel("View Appointment: ");
        panel1.add(labelSession); // (1,1)

        JComboBox<String> storeDropdown = new JComboBox<>(Session);
        panel1.add(storeDropdown); // (1,2)

        JButton confirmSession = new JButton("Confirm"); // add action listener

        panel1.add(confirmSession); // (1, 3)

        // View Statistics
        JLabel labelStatistics = new JLabel("View Statistics: ");
        panel1.add(labelStatistics); // (2, 1)

        // gridBagConstraints.gridx = 1;
        JComboBox<String> statDropdown = new JComboBox<>(Statistics);
        panel1.add(statDropdown); // (2,2)

        // gridBagConstraints.gridx = 2;
        JButton confirmStat = new JButton("Confirm");
        panel1.add(confirmStat); // (2,3)

        // // Open New Store
        JButton requestNewAppointment = new JButton("Request New Appointment");
        requestNewAppointment.setSize(50,50); // add action listener
        panel1.add(requestNewAppointment);// (3,1)

        // // Return to Main Menu

        JButton returnMain = new JButton("Main Menu");
        returnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new MainMenuGUI());
                frame.dispose();
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



        confirmSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellerSessionGUI ssg = new SellerSessionGUI();

            }
        });
    }
}
