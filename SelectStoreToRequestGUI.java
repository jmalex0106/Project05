import javax.swing.*;
import java.awt.*;

public class SelectStoreToRequestGUI implements Runnable{
    private static  String[] Store = {"Select Session", "Indiana University"};


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SelectStoreToRequestGUI());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Request New Appointment");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new GridLayout(2,2)); // 2x2

        JLabel storeLabel = new JLabel("View Stores: ");
        panel1.add(storeLabel); // (1,1)

        JComboBox<String> storeDropdown = new JComboBox<>(Store);
        panel1.add(storeDropdown); // (1,2)

        JButton storeConfirm = new JButton("Confirm");
        panel1.add(storeConfirm); // (2,1)

        JButton backButton = new JButton("Back");
        panel1.add(backButton);  // (2,2)

        content.add(panel1, BorderLayout.CENTER);

        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
