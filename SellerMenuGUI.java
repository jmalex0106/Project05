import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SellerMenuGUI implements Runnable {
    private static final String[] Store = {"Select Store", "Indiana University"};
    private static final String[] Statistics = {"The most popular appointment", "other options"};


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SellerMenuGUI());
    }
    @Override
    public void run() {
        JFrame frame = new JFrame("Welcome Tutor");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());



        // Menu Panel - Panel1
        JPanel panel1 = new JPanel(new GridLayout(3,3)); // 3x3
        // GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // // View Store
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 0;
        JLabel labelStore = new JLabel("View Stores: ");
        panel1.add(labelStore); // (1,1)

        // gridBagConstraints.gridx = 1;
        JComboBox<String> storeDropdown = new JComboBox<>(Store);
        panel1.add(storeDropdown); // (1,2)

        // gridBagConstraints.gridx = 2;
        JButton confirmStore = new JButton("Confirm"); // add action listener
        confirmStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellerStoreGUI ssg = new SellerStoreGUI();

            }
        });
        panel1.add(confirmStore); // (1, 3)

        // View Statistics
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 1;

        JLabel labelStatistics = new JLabel("View Statistics: ");
        panel1.add(labelStatistics); // (2, 1)

        // gridBagConstraints.gridx = 1;
        JComboBox<String> statDropdown = new JComboBox<>(Statistics);
        panel1.add(statDropdown); // (2,2)

        // gridBagConstraints.gridx = 2;
        JButton confirmStat = new JButton("Confirm");
        panel1.add(confirmStat); // (2,3)

        // // Open New Store
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 2;

        JButton openNewStore = new JButton("Open new Store");
        openNewStore.setSize(50,50); // add action listener
        panel1.add(openNewStore);// (3,1)

        // // Return to Main Menu
        // gridBagConstraints.gridy = 2;
        // gridBagConstraints.gridx = 2;
        JButton returnMain = new JButton("Main Menu");
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


}
