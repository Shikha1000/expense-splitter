import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ExpenseSplitterUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("💸 Daily Expense Splitter");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1, 10, 10));

        // Title
        JLabel title = new JLabel("Expense Splitter", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title);

        // Input for number of people
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Number of People:"));
        JTextField fieldPeople = new JTextField(10);
        panel1.add(fieldPeople);
        frame.add(panel1);

        // Names input
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Names (comma separated):"));
        JTextField fieldNames = new JTextField(20);
        panel2.add(fieldNames);
        frame.add(panel2);

        // Amounts input
        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Amounts Paid (comma separated):"));
        JTextField fieldAmounts = new JTextField(20);
        panel3.add(fieldAmounts);
        frame.add(panel3);

        // Buttons
        JPanel panel4 = new JPanel();
        JButton calculateBtn = new JButton("Calculate");
        JButton clearBtn = new JButton("Clear");
        panel4.add(calculateBtn);
        panel4.add(clearBtn);
        frame.add(panel4);

        // Output
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        frame.add(new JScrollPane(resultArea));

        // Action: Calculate
        calculateBtn.addActionListener(e -> {
            try {
                int n = Integer.parseInt(fieldPeople.getText());
                String[] names = fieldNames.getText().split(",");
                String[] inputs = fieldAmounts.getText().split(",");

                if (names.length != n || inputs.length != n) {
                    resultArea.setText("❌ Enter correct number of names and amounts!");
                    return;
                }

                double total = 0;
                double[] paid = new double[n];

                for (int i = 0; i < n; i++) {
                    paid[i] = Double.parseDouble(inputs[i].trim());
                    total += paid[i];
                }

                double perPerson = total / n;

                StringBuilder result = new StringBuilder();
                result.append("Total Expense: ").append(total).append("\n");
                result.append("Each should pay: ").append(perPerson).append("\n\n");

                for (int i = 0; i < n; i++) {
                    double diff = paid[i] - perPerson;

                    if (diff > 0) {
                        result.append(names[i]).append(" gets ₹").append(diff).append("\n");
                    } else if (diff < 0) {
                        result.append(names[i]).append(" pays ₹").append(-diff).append("\n");
                    } else {
                        result.append(names[i]).append(" is settled\n");
                    }
                }

                resultArea.setText(result.toString());

            } catch (Exception ex) {
                resultArea.setText("❌ Invalid input!");
            }
        });

        // Action: Clear
        clearBtn.addActionListener(e -> {
            fieldPeople.setText("");
            fieldNames.setText("");
            fieldAmounts.setText("");
            resultArea.setText("");
        });

        frame.setVisible(true);
    }
}