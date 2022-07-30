import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO ADD DESCRIPTIVE JAVADOCS
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version date
 */

public class SellerStoreGUI implements Runnable {
    private static final String[] APP_LIST = {"Select Appointment"};

    private Seller seller;

    private Store store;

    public SellerStoreGUI(Seller seller, Store store) {
        this.seller = seller;
        this.store = store;
    }

    public void playGUI() {
        SwingUtilities.invokeLater(new SellerStoreGUI(seller, store));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Welcome to Tutoring Service");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel
        JPanel panel1 = new JPanel(new GridLayout(2, 2)); // 2x2

        JComboBox<String> appointmentDropDown = new JComboBox<>(APP_LIST);
        panel1.add(appointmentDropDown); // (1,1)

        JButton appointmentConfirm = new JButton("Confirm");
//        appointmentConfirm.addActionListener(new );
        panel1.add(appointmentConfirm); // (1,2)

        JButton backToTutor = new JButton("Back to Tutor");
        backToTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SellerMenuGUI(seller).playGUI();
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
}
