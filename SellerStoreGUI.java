import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO ADD DESCRIPTIVE JAVADOCS
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 *
 * @version date
 *
 */

public class SellerStoreGUI implements Runnable {

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
        JFrame frame = new JFrame("Welcome to " + store.getName());

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel
        JPanel panel1 = new JPanel(new GridLayout(2, 2)); // 2x2

        String[] appList = new String[this.store.getSessions().size()];
        for (int i = 0; i < this.store.getSessions().size(); i++) {
            appList[i] = (this.store.getSessions().get(i).getMonth() + 1) +
                    "/" + this.store.getSessions().get(i).getDay() +
                    " " + this.store.getSessions().get(i).getYear() +
                    " at " + this.store.getSessions().get(i).getHour() +
                    ":00";
        }
        if (this.store.getSessions().size() == 0 ) {
            appList = new String[]{"This store has no waiting or approved customers"};
        }
        JComboBox<String> appointmentDropDown = new JComboBox<>(appList);
        panel1.add(appointmentDropDown); // (1,1)

        JButton appointmentConfirm = new JButton("Confirm");
        appointmentConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int appointmentSelected = appointmentDropDown.getSelectedIndex();
                Session session = store.getSessions().get(appointmentSelected);
                frame.dispose();
                new SessionGUI(seller , store , session).playGUI();
            }
        });
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
