import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RescheduleGUI implements Runnable {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RescheduleGUI());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame();

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        // Main Menu
        JPanel panel1 = new JPanel(new GridLayout(3, 2));// 3x2

        // Start Time & End Time Labels
        JLabel startTimeLabel = new JLabel("Enter Start Time: ");
        JLabel endTimeLabel = new JLabel("Enter End Time: ");
        panel1.add(startTimeLabel); // (1,1)
        panel1.add(endTimeLabel); // (1,2)

        // Start Time & End Time TextField
        JTextField startTimeTextField = new JTextField(10);
        JTextField endTimeTextField = new JTextField(10);

        panel1.add(startTimeTextField); // (2,1)
        panel1.add(endTimeTextField); // (2,2)

        // Back & Exit Button
        JButton back = new JButton("Back");
        JButton exit = new JButton("Exit");

        panel1.add(back);
        panel1.add(exit);

        content.add(panel1, BorderLayout.CENTER);

        // set Frame
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });




    }
}
