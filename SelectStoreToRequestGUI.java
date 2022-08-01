import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

public class SelectStoreToRequestGUI implements Runnable {
    private Customer customer;
    private Store[] allStores;
    private Socket socket;

    public SelectStoreToRequestGUI(Customer customer , Socket socket) {
        this.customer = customer;
        //TODO server runs ServerMethods.allStores() and sends an array list of stores to client
        //TODO this.allStores is set to this array list of stores and converts it to an array.
        this.socket = socket;
    }

    public void playGUI() {
        SwingUtilities.invokeLater(
                new SelectStoreToRequestGUI(customer , socket));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Request New Appointment");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new GridLayout(2,2)); // 2x2

        JLabel storeLabel = new JLabel("View Stores: ");
        panel1.add(storeLabel); // (1,1)

      //  JComboBox<String> storeDropdown = new JComboBox<>(AllStores);
      //  panel1.add(storeDropdown); // (1,2)

        JButton storeConfirm = new JButton("Confirm");
        panel1.add(storeConfirm); // (2,1)

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerMenuGUI(customer , socket).playGUI();
            }
        });
        panel1.add(backButton);  // (2,2)

        content.add(panel1, BorderLayout.CENTER);

        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}