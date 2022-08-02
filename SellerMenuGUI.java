import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * TODO ADD DESCRIPTIVE JAVADOCS
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version date
 */
public class SellerMenuGUI implements Runnable {
    private Seller seller;  //The seller that is associated with this GUI
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public SellerMenuGUI(Seller seller, Socket socket) {
        System.out.println("MAKING SELLER MENU GUI");
        this.seller = seller;
        this.socket = socket;
        try {
            this.objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            System.out.println("CONSTRUCTED 0");
            this.objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
            System.out.println("CONSTRUCTED 1");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect with server");
        }
    }


    /**
     * Returns the names of all the stores that this seller owns in a String array to display
     *
     * @return
     */
    public String[] listAllStores() {
        String[] output = new String[this.seller.getStores().size()];
        for (int i = 0; i < this.seller.getStores().size(); i++) {
            output[i] = this.seller.getStores().get(i).getName();
        }
        return output;
    }

    public Store getStoreWithName(String storeName) {
        for (Store store : this.seller.getStores()) {
            if (store.getName().equals(storeName)) {
                return store;
            }
        }
        return this.seller.getStores().get(0);
    }

    private static final String[] STATISTICS = {"View sorted statistics", "View unsorted statistics"};

    public void playGUI() {
        System.out.println("PlayGUI");
        SwingUtilities.invokeLater(new SellerMenuGUI(seller , socket));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Welcome " + seller.getName() + "!");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Menu Panel - Panel1
        JPanel panel1 = new JPanel(new GridLayout(3, 3)); // 3x3
        // GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // // View Store
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 0;
        JLabel labelStore = new JLabel("View Stores: ");
        panel1.add(labelStore); // (1,1)

        // gridBagConstraints.gridx = 1;
        JComboBox<String> storeDropdown = new JComboBox<>(listAllStores());
        panel1.add(storeDropdown); // (1,2)

        // gridBagConstraints.gridx = 2;
        JButton confirmStore = new JButton("Confirm"); // add action listener
        confirmStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listAllStores().length == 0) {
                    JOptionPane.showMessageDialog(frame , "You have no stores");
                } else {
                    new SellerStoreGUI(seller,
                            getStoreWithName(storeDropdown.getSelectedItem().toString()) ,
                            socket).playGUI();
                    frame.dispose();
                }
            }
        });
        panel1.add(confirmStore); // (1, 3)

        // View Statistics
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 1;

        JLabel labelStatistics = new JLabel("View Statistics: ");
        panel1.add(labelStatistics); // (2, 1)

        // gridBagConstraints.gridx = 1;
        JComboBox<String> statDropdown = new JComboBox<>(STATISTICS);
        panel1.add(statDropdown); // (2,2)

        // gridBagConstraints.gridx = 2;
        JButton confirmStat = new JButton("Confirm");
        confirmStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: send selection (sorted or unsorted statistics) to server,
                //TODO: receive appropriate statistics toString from server, and
                //TODO: set String statistics to this toString.
                String[] sellerRequestStatistics = new String[3];
                sellerRequestStatistics[0] = "sellerRequestStatistics";
                sellerRequestStatistics[1] = statDropdown.getSelectedItem().toString();
                sellerRequestStatistics[2] = seller.getName();
                if (listAllStores().length == 0) {
                    JOptionPane.showMessageDialog(frame , "You have no stores");
                } else {
                    try {
                        objectOutputStream.writeObject(sellerRequestStatistics);
                        objectOutputStream.flush();
                        Object statisticsObject = objectInputStream.readObject();
                        if (statisticsObject instanceof String) {
                            String statistics = (String) statisticsObject;
                            JOptionPane.showMessageDialog(frame,
                                    statistics);
                        }
                    } catch (IOException | ClassNotFoundException ioException) {
                        JOptionPane.showMessageDialog(frame , "Connection with server failed");
                    }
                }
            }
        });
        panel1.add(confirmStat); // (2,3)

        // // Open New Store
        // gridBagConstraints.gridx = 0;
        // gridBagConstraints.gridy = 2;

        JButton openNewStore = new JButton("Open new Store");
        openNewStore.setSize(50, 50);
        openNewStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    socket.close();
                    objectOutputStream.close();
                    objectInputStream.close();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame , "Connection error with server");
                }
                new OpenNewStoreGUI(seller , socket).playGUI();
            }
        });
        panel1.add(openNewStore); // (3,1)

        // // Return to Main Menu
        // gridBagConstraints.gridy = 2;
        // gridBagConstraints.gridx = 2;
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
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
