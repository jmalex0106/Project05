import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreGUI implements Runnable {
    private static String[] waitingList = {"Select Store", "Indiana University"};
    private static String[] approvedList = {"Select Store", "Indiana University"};


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new StoreGUI());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Welcome to Session at Date");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        //Main Panel
        JPanel panel1 = new JPanel(new GridLayout(3, 3)); // (3x3)

        JLabel waitingListLabel = new JLabel("View Waiting List: ");
        panel1.add(waitingListLabel); // (1,1)

        JComboBox<String> waitingListDropdown = new JComboBox<>(waitingList);
        panel1.add(waitingListDropdown); // (1,2)

        JButton approveButton = new JButton("Approve");
        panel1.add(approveButton); // (1,3)

        JLabel approveListLabel = new JLabel("View Approved List: ");
        panel1.add(approveListLabel); // (2,1)

        JComboBox<String> approvedListDropdown = new JComboBox<>(approvedList);
        panel1.add(approvedListDropdown); // (2,2)

        JButton denyButton = new JButton("Deny");
        panel1.add(denyButton); // (2,3)

        JButton rescheduleButton = new JButton("Reschedule");
        panel1.add(rescheduleButton); // (3,1)

        JLabel placeholder = new JLabel();
        panel1.add(placeholder); // (3,2)

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel1.add(exit);

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
        String startTime = "";
        String endTime = "";
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
