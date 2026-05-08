package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame {
    private JTextField num1Field, num2Field;
    private JLabel resultLabel;

    public SimpleCalculator() {
        setTitle("Colorful Calculator");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255)); // AliceBlue background

        // Input Fields with Colors
        JPanel input1 = new JPanel(new BorderLayout());
        input1.setBackground(new Color(240, 248, 255));
        JLabel label1 = new JLabel("Number 1: ");
        label1.setForeground(new Color(70, 130, 180)); // SteelBlue
        label1.setFont(new Font("SansSerif", Font.BOLD, 14));
        input1.add(label1, BorderLayout.WEST);
        num1Field = new JTextField();
        num1Field.setBackground(Color.WHITE);
        num1Field.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 2));
        input1.add(num1Field, BorderLayout.CENTER);
        mainPanel.add(input1);

        JPanel input2 = new JPanel(new BorderLayout());
        input2.setBackground(new Color(240, 248, 255));
        JLabel label2 = new JLabel("Number 2: ");
        label2.setForeground(new Color(70, 130, 180));
        label2.setFont(new Font("SansSerif", Font.BOLD, 14));
        input2.add(label2, BorderLayout.WEST);
        num2Field = new JTextField();
        num2Field.setBackground(Color.WHITE);
        num2Field.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 2));
        input2.add(num2Field, BorderLayout.CENTER);
        mainPanel.add(input2);

        // Buttons with Colors
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        String[] operators = {"+", "-", "*", "/"};
        Color[] btnColors = {new Color(255, 182, 193), new Color(144, 238, 144), 
                             new Color(255, 255, 224), new Color(173, 216, 230)};
        
        for (int i = 0; i < operators.length; i++) {
            JButton btn = new JButton(operators[i]);
            btn.setBackground(btnColors[i]);
            btn.setFont(new Font("SansSerif", Font.BOLD, 18));
            btn.setFocusPainted(false);
            btn.addActionListener(new OperatorListener(operators[i]));
            buttonPanel.add(btn);
        }
        mainPanel.add(buttonPanel);

        // Result Label
        resultLabel = new JLabel("Wait for Calculation...", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        resultLabel.setForeground(new Color(105, 105, 105));
        mainPanel.add(resultLabel);

        add(mainPanel, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(240, 248, 255));
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class OperatorListener implements ActionListener {
        private String operator;

        public OperatorListener(String operator) {
            this.operator = operator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double n1 = Double.parseDouble(num1Field.getText());
                double n2 = Double.parseDouble(num2Field.getText());
                double res = 0;
                boolean error = false;

                switch (operator) {
                    case "+": res = n1 + n2; break;
                    case "-": res = n1 - n2; break;
                    case "*": res = n1 * n2; break;
                    case "/": 
                        if (n2 == 0) {
                            showPopup("Error: Division by Zero!", "Error", JOptionPane.ERROR_MESSAGE);
                            resultLabel.setText("Result: Error");
                            return;
                        }
                        res = n1 / n2; 
                        break;
                }
                
                String resultStr = "Result: " + res;
                resultLabel.setText(resultStr);
                resultLabel.setForeground(new Color(34, 139, 34)); // ForestGreen
                
                // Show Popup with Icon
                showPopup("The calculation is complete!\n\n" + n1 + " " + operator + " " + n2 + " = " + res, 
                          "Calculation Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                showPopup("Please enter valid numbers!", "Input Error", JOptionPane.WARNING_MESSAGE);
                resultLabel.setText("Result: Invalid Input");
            }
        }

        private void showPopup(String message, String title, int messageType) {
            // Using system icons as placeholders for "images"
            Icon icon = null;
            if (messageType == JOptionPane.INFORMATION_MESSAGE) {
                icon = UIManager.getIcon("OptionPane.informationIcon");
            } else if (messageType == JOptionPane.ERROR_MESSAGE) {
                icon = UIManager.getIcon("OptionPane.errorIcon");
            } else {
                icon = UIManager.getIcon("OptionPane.warningIcon");
            }

            JOptionPane.showMessageDialog(SimpleCalculator.this, message, title, messageType, icon);
        }
    }

    public static void main(String[] args) {
        try {
            // Set Look and Feel to System
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> new SimpleCalculator());
    }
}
