import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This GUI plays the initial customer menu after a customer logs in.
 *
 * @author Moxiao Li , Junmo Kim , Aidan Davis
 *
 * @version 7/30/2022
 *
 */
public class CustomerMenuGUI implements Runnable {
    private Customer customer;

    public CustomerMenuGUI(Customer customer) {
        this.customer = customer;
    }

    public void playGUI() {
        SwingUtilities.invokeLater(new CustomerMenuGUI(customer));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Welcome " + customer.getName());

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel - Panel1
        JPanel panel1 = new JPanel(new GridLayout(3,3)); // 3x3

        JButton exportToCSV = new JButton("Export appointments\nto CSV");
        exportToCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO server runs ServerMethods.exportCustomerAppointmentsToCSV and
                //TODO sends the file path to the client. Client sets String csvPath to the
                //TODO CSV path
                String csvPath = "csvPath";
                JOptionPane.showMessageDialog(frame,
                        "CSV with your approved appointments has been made at " + csvPath);
            }
        });
        panel1.add(exportToCSV); // (2,3)

        // gridBagConstraints.gridx = 2;
        JButton viewSortedStat = new JButton("View sorted\n statistics");
        viewSortedStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sortedStats = "sorted stats";
                //TODO server runs ServerMethods.showSortedStatisticsToCustomer and sends the
                //TODO return string and the client and the client sets sortedStats to this
                JOptionPane.showMessageDialog(frame,
                        sortedStats);
            }
        });
        panel1.add(viewSortedStat); // (2,3)

        JButton cancelAppointment = new JButton("Cancel Appointment");
        cancelAppointment.setSize(50,50);
        cancelAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerCancelAppointmentGUI(customer)
                        .playGUI();
            }
        });
        panel1.add(cancelAppointment); // (2,1)

        // gridBagConstraints.gridx = 2;
        JButton viewUnsortedStat = new JButton("View unsorted\n statistics");
        viewUnsortedStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unsortedStats = "sorted stats";
                //TODO server runs ServerMethods.showUnsortedStatisticsToCustomer and sends the
                //TODO return string and the client and the client sets unsortedStats to this
                JOptionPane.showMessageDialog(frame,
                        unsortedStats);
            }
        });
        panel1.add(viewUnsortedStat); // (2,3)

        // // Open New Store
        JButton requestNewAppointment = new JButton("Request New Appointment");
        requestNewAppointment.setSize(50,50);
        requestNewAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerSelectStoreGUI(customer).playGUI();
            }
        });
        panel1.add(requestNewAppointment);// (3,1)

        // // Return to Main Menu

        JButton returnMain = new JButton("Main Menu");
        returnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainMenuGUI().playGUI();
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
}
