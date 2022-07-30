import javax.swing.*;
import java.awt.*;

public class CancelAppointmentGUI implements Runnable {

    public static final String[] appointment = {"select"};

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CancelAppointmentGUI());
    }


    @Override
    public void run() {
        JFrame frame = new JFrame("Cancel Appointment");

        Container content = frame.getContentPane();

        content.setLayout(new GridLayout(2, 1)); // 2x1

        // Dropbox
        JComboBox<String> appointmentDropdown = new JComboBox<>(appointment);
        content.add(appointmentDropdown);

        // JPanel with 2 buttons
        JPanel panel = new JPanel(new GridLayout(1, 2)); // 1x2
        JButton confirmButton = new JButton("Confirm");

        JButton backButton = new JButton("Back");

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
