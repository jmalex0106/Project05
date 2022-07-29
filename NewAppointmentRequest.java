import javax.print.attribute.standard.JobName;
import javax.swing.*;
import java.awt.*;

public class NewAppointmentRequest implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new NewAppointmentRequest());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Request New Appointment");

        Container content = frame.getContentPane();

        content.setLayout(new GridLayout(1, 2));

        // left panel
        JPanel panel1 = new JPanel(new GridLayout(5, 1)); // 5x1

        JTextField yearTextField = new JTextField(5);
        yearTextField.setText("Year");

        JTextField monthTextField = new JTextField(5);
        monthTextField.setText("Month");

        JTextField dayTextField = new JTextField(5);
        dayTextField.setText("Day");

        JTextField hourField = new JTextField(5);
        hourField.setText("Hour");

        JButton confirmButton = new JButton("Confirm");

        panel1.add(yearTextField);
        panel1.add(monthTextField);
        panel1.add(dayTextField);
        panel1.add(hourField);
        panel1.add(confirmButton);

        content.add(panel1); //left

        // right panel
        JPanel panel2 = new JPanel(new GridBagLayout()); // span multiple cells

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 220;
        gridBagConstraints.ipady = 115;

        JLabel storeInfoLabel = new JLabel("10", SwingConstants.LEFT);

        storeInfoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel2.add(storeInfoLabel, gridBagConstraints);


        // DON'T  Change these parameters ----------------------------------
        // where the next component is
        gridBagConstraints.gridy = 500;
        // modify width and height of the component

        gridBagConstraints.ipady = confirmButton.getHeight()+4;
        gridBagConstraints.ipadx = 175;
        // DON'T  Change these parameters ----------------------------------


        JButton backButton = new JButton("Back");
        panel2.add(backButton, gridBagConstraints);


        content.add(panel2); // right

        //set frame
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
