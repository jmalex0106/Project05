import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This GUI opens from SellerStoreGUI after the user has selected a session
 */
public class SessionGUI implements Runnable {
    private Seller seller;
    private Store store;
    private Session session;

    public SessionGUI(Seller seller , Store store , Session session) {
        this.seller = seller;
        this.store = store;
        this.session = session;
    }

    public void playGUI () {
        SwingUtilities.invokeLater(new SessionGUI(seller , store , session));
    }

    @Override
    public void run() {
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
                new SellerStoreGUI(seller , store).playGUI();
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
        String startTime = String.valueOf(this.session.getHour()) + ":00";
        String endTime = String.valueOf(this.session.getHour() + 1) + ":00";
        JLabel startAndEndTimeLabel = new JLabel(startTime + " - " + endTime);
        panel2.add(startAndEndTimeLabel);

        content.add(panel2, BorderLayout.NORTH);


        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
