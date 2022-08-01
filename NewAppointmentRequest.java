import javax.print.attribute.standard.JobName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewAppointmentRequest implements Runnable {
    private Customer customer;
    private Store store;
    public NewAppointmentRequest(Customer customer , Store store) {
        this.customer = customer;
        this.store = store;

    }
    public void playGUI () {
        SwingUtilities.invokeLater(new NewAppointmentRequest(customer , store));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Request a new appointment at " + store.getName());

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
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String year = yearTextField.getText().trim();
                String month = yearTextField.getText().trim();
                String day = yearTextField.getText().trim();
                String hour = yearTextField.getText().trim();
                //TODO the client sends these four Strings to the server and the server runs
                //TODO ServerMethods.requestAppointment and the client sets int success to the
                //TODO return value
                int success = 0;
                switch(success) {
                    case 0 -> {
                        JOptionPane.showMessageDialog(frame ,
                                "You have been added to the waitlist successfully");
                    }
                    case 1 -> {
                        JOptionPane.showMessageDialog(frame ,
                                "This session is at or over capacity. You have been added " +
                                        "to the waitlist, \nbut the " +
                                        "tutor may not accept your request.");
                    }
                    case 2 -> {
                        JOptionPane.showMessageDialog(frame ,
                                "Some fields were left blank. Please fill in all fields. " +
                                        "Waitlist has not been updated.");
                    }
                    case 3 -> {
                        JOptionPane.showMessageDialog(frame ,
                                "The store entered does not exist. " +
                                        "Waitlist has not been updated.");
                    }
                    case 4 -> {
                        JOptionPane.showMessageDialog(frame ,
                                "The date entered is in the past or is invalid. " +
                                        "Please enter a valid date.");
                    }
                    case 5 -> {
                        JOptionPane.showMessageDialog(frame ,
                                "The store you have entered is closed on the date and " +
                                        "time you have entered.\n" +
                                        "Please enter a valid date. " +
                                        "Waitlist has not been updated.");
                    }
                    default -> {
                    }
                }
                frame.dispose();
                new CustomerMenuGUI(customer).playGUI();
            }
        });

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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerMenuGUI(customer).playGUI();
            }
        });
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
