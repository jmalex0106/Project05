import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerSelectStoreGUI implements Runnable {
    private Customer customer;

    public CustomerSelectStoreGUI(Customer customer) {
        this.customer = customer;
    }

    public void playGUI() {
        SwingUtilities.invokeLater(new CustomerSelectStoreGUI(customer));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Select store");

        Container content = frame.getContentPane();

        content.setLayout(new GridLayout(2, 1)); // 2x1

        String[] allStores = new String[]{"Stores"};
        //TODO: Server runs ServerMethods.allStores() and sends the resulting arraylist to the client
        //TODO: The client sets the String array to the returned arraylist
        // Dropbox
        JComboBox<String> storeDropdown = new JComboBox<>(allStores);
        content.add(storeDropdown);

        // JPanel with 2 buttons
        JPanel panel = new JPanel(new GridLayout(1, 2)); // 1x2
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeDropdown.getSelectedItem().toString();
                //TODO client sends this String to the server, the server creates the relevant
                //TODO Store object from this String, sends the store object to the client, and the
                //TODO client sets the variable Store selectedStore to this store.
                Store selectedStore = new Store("JunmoMath" , "Junmo");
                frame.dispose();
                new NewAppointmentRequest(customer , selectedStore).playGUI();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerMenuGUI(customer).playGUI();
            }
        });

        panel.add(confirmButton);
        panel.add(backButton);

        content.add(panel);

        // frame set
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
