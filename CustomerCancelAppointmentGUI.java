import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerCancelAppointmentGUI implements Runnable {
    private Customer customer;

    public CustomerCancelAppointmentGUI(Customer customer) {
        this.customer = customer;
    }

    public void playGUI() {
        SwingUtilities.invokeLater(new CustomerCancelAppointmentGUI(customer));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Cancel Appointment");

        Container content = frame.getContentPane();

        content.setLayout(new GridLayout(2, 1)); // 2x1

        String[] appointment = new String[this.customer.getApprovedRequest().size()];
        for (int i = 0; i < this.customer.getApprovedRequest().size(); i++) {
            appointment[i] = "waiting " + (this.customer.getWaitingRequest().get(i).getMonth()+1) +
                    "/" + this.customer.getWaitingRequest().get(i).getDay() +
                    " " + this.customer.getWaitingRequest().get(i).getYear() +
                    " at store " +
                    this.customer.getWaitingRequest().get(i).getStore();
        }
        // Dropbox
        JComboBox<String> appointmentDropdown = new JComboBox<>(appointment);
        content.add(appointmentDropdown);

        // JPanel with 2 buttons
        JPanel panel = new JPanel(new GridLayout(1, 2)); // 1x2
        JButton confirmButton = new JButton("Confirm");

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerMenuGUI(customer).playGUI();
            }
        });

        panel.add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedAppointment = appointmentDropdown.getSelectedIndex();
                //TODO the int selectedAppointment is sent to the server and the server runs
                //TODO ServerMethods.customerCancelAppointmentAtIndex
                JOptionPane.showMessageDialog(frame , "Appointment cancelled successfully");
                frame.dispose();
                new CustomerMenuGUI(customer).playGUI();
            }
        });
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
