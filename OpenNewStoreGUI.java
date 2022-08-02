import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OpenNewStoreGUI implements Runnable {
    private Seller seller;  //The seller that is associated with this GUI
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public OpenNewStoreGUI(Seller seller , Socket socket) {
        this.seller = seller;
        this.socket = socket;
        try {
            this.objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            System.out.println("CONSTRUCTED 0");
            this.objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect with server");
        }
    }
    public void playGUI() {
        SwingUtilities.invokeLater(new OpenNewStoreGUI(seller , socket));
    }
    public void run() {
        JFrame jFrame = new JFrame("Open new store for " + seller.getName());
        Container content = jFrame.getContentPane();
        content.setLayout(new GridLayout(5, 1)); // 2x1
        JLabel storeName = new JLabel("enter a new store name");
        JLabel csvPath = new JLabel("enter the path to a CSV file");
        JTextField storeNameInput = new JTextField();
        JTextField csvPathInput = new JTextField();
        JButton openStore = new JButton("confirm open store");
        JButton back = new JButton("back");
        openStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO make this button work
                String storeNameString = storeNameInput.getText();
                String csvPathString = csvPathInput.getText();
                if (csvPathString != null && storeNameString != null) {
                    try {
                        File file = new File(csvPathString);
                        if (!file.exists()) {
                            JOptionPane.showMessageDialog(jFrame , "The file path entered" +
                                    " is invalid , please try again");
                            jFrame.dispose();
                            new SellerMenuGUI(seller , socket).playGUI();
                        } else {
                            String[] tags = new String[1];
                            tags[0] = storeNameInput.getText();
                            FileStringPacket fileStringPacket = new FileStringPacket(file ,
                                    tags , seller);
                            objectOutputStream.writeObject(fileStringPacket);
                            objectOutputStream.flush();
                            Object object = objectInputStream.readObject();
                            if (object instanceof SellerIntegerPacket) {
                                SellerIntegerPacket sellerIntegerPacket =
                                        (SellerIntegerPacket) object;
                                seller = sellerIntegerPacket.getSeller();
                                int openedNewStore = sellerIntegerPacket.getInteger();
                                if (openedNewStore == 0) {
                                    JOptionPane.showMessageDialog(jFrame , "Store " +
                                            storeNameInput.getText() + " opened for seller " +
                                            seller.getName());
                                    jFrame.dispose();
                                    new SellerMenuGUI(seller , socket).playGUI();
                                } else if (openedNewStore == 1) {
                                    JOptionPane.showMessageDialog(jFrame ,
                                            "this store name is already taken");
                                } else {
                                    JOptionPane.showMessageDialog(jFrame ,
                                            "An error occurred importing your csv file");
                                }
                            } else {
                                JOptionPane.showMessageDialog(jFrame ,
                                        "Connection error with server");
                            }
                        }
                    } catch (IOException | ClassNotFoundException ioException) {
                        JOptionPane.showMessageDialog(jFrame , "Connection error with server");
                        jFrame.dispose();
                        new SellerMenuGUI(seller , socket);
                    }
                } else {
                    JOptionPane.showMessageDialog(jFrame , "Some fields where left blank.");
                    jFrame.dispose();
                    new SellerMenuGUI(seller , socket);
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                new SellerMenuGUI(seller , socket);
            }
        });
        jFrame.add(storeName);
        jFrame.add(storeNameInput);
        jFrame.add(csvPath);
        jFrame.add(csvPathInput);
        jFrame.add(openStore);
        jFrame.add(back);
        jFrame.setVisible(true);
    }
}
