import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class TripTracker extends JFrame {
    private ArrayList<String> tripRoute; // To store the trip destinations
    private ArrayList<JCheckBox> checkpointBoxes; // To store checkboxes for destinations
    private JTextArea routeDisplay; // To display the trip route
    private JTextField destinationInput; // For entering destinations
    private JTextField expenseDescriptionInput; // For entering expense descriptions
    private JTextField expenseAmountInput; // For entering expense amounts
    private JTextArea expenseDisplay; // To display the list of expenses
    private JTextArea totalExpenseDisplay; // To display total expenses
    private JTextArea costDistributionDisplay; // To display cost distribution
    private ArrayList<Expense> expenses; // To store expenses

    // Class to represent an Expense
    private static class Expense {
        String description;
        double amount;

        Expense(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return description + ": $" + String.format("%.2f", amount);
        }
    }

    public TripTracker() {
        tripRoute = new ArrayList<>();
        expenses = new ArrayList<>();
        checkpointBoxes = new ArrayList<>();

        // Set up frame with classic design
        setTitle("Trip Tracker");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Panel for adding destinations
        JLabel destinationLabel = new JLabel("Enter Destination:");
        destinationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(destinationLabel, gbc);

        destinationInput = new JTextField(15);
        destinationInput.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        add(destinationInput, gbc);

        JButton addDestinationButton = new JButton("Add Destination");
        addDestinationButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 2;
        add(addDestinationButton, gbc);

        // Route display area
        JLabel routeLabel = new JLabel("Planned Route:");
        routeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(routeLabel, gbc);

        routeDisplay = new JTextArea(5, 20);
        routeDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
        routeDisplay.setEditable(false);
        JScrollPane routeScrollPane = new JScrollPane(routeDisplay);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(routeScrollPane, gbc);
        gbc.gridwidth = 1; // Reset after component

        // Panel for adding expenses
        JLabel expenseLabel = new JLabel("Add Expense:");
        expenseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(expenseLabel, gbc);

        expenseDescriptionInput = new JTextField(10);
        expenseDescriptionInput.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        add(expenseDescriptionInput, gbc);

        expenseAmountInput = new JTextField(10);
        expenseAmountInput.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 2;
        add(expenseAmountInput, gbc);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 3;
        add(addExpenseButton, gbc);

        // Expense display area
        JLabel expenseListLabel = new JLabel("Expenses:");
        expenseListLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(expenseListLabel, gbc);

        expenseDisplay = new JTextArea(5, 20);
        expenseDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
        expenseDisplay.setEditable(false);
        JScrollPane expenseScrollPane = new JScrollPane(expenseDisplay);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(expenseScrollPane, gbc);
        gbc.gridwidth = 1; // Reset after component

        // Total expense display
        totalExpenseDisplay = new JTextArea(2, 20);
        totalExpenseDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
        totalExpenseDisplay.setEditable(false);
        totalExpenseDisplay.setBackground(Color.LIGHT_GRAY);
        totalExpenseDisplay.setText("Total Expenses: $0.00");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        add(totalExpenseDisplay, gbc);

        // Cost distribution display
        JLabel costDistributionLabel = new JLabel("Cost Distribution:");
        costDistributionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(costDistributionLabel, gbc);

        costDistributionDisplay = new JTextArea(3, 20);
        costDistributionDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
        costDistributionDisplay.setEditable(false);
        JScrollPane distributionScrollPane = new JScrollPane(costDistributionDisplay);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(distributionScrollPane, gbc);
        gbc.gridwidth = 1; // Reset after component

        // Action listeners
        addDestinationButton.addActionListener(e -> {
            String destination = destinationInput.getText();
            if (!destination.isEmpty()) {
                tripRoute.add(destination);
                destinationInput.setText("");
                addDestinationCheckBox(destination);
                updateRouteDisplay();
            }
        });

        addExpenseButton.addActionListener(e -> {
            String description = expenseDescriptionInput.getText();
            String amountText = expenseAmountInput.getText();
            if (!description.isEmpty() && !amountText.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountText);
                    Expense expense = new Expense(description, amount);
                    expenses.add(expense);
                    expenseDescriptionInput.setText("");
                    expenseAmountInput.setText("");
                    updateExpenseDisplay();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
                }
            }
        });
    }

    // Add a checkbox for the new destination
    private void addDestinationCheckBox(String destination) {
        JCheckBox checkbox = new JCheckBox(destination);
        checkbox.setFont(new Font("Arial", Font.PLAIN, 16));
        checkpointBoxes.add(checkbox);
    }

    // Update the route display with the current trip route
    private void updateRouteDisplay() {
        StringBuilder routeText = new StringBuilder();
        for (JCheckBox checkbox : checkpointBoxes) {
            routeText.append(checkbox.getText());
            routeText.append(checkbox.isSelected() ? " ✅\n" : " ❌\n");
        }
        routeDisplay.setText(routeText.toString());
        routeDisplay.append("End of Trip");
    }

    // Update the expense display
    private void updateExpenseDisplay() {
        expenseDisplay.setText("");
        double total = 0;
        for (Expense expense : expenses) {
            expenseDisplay.append(expense.toString() + "\n");
            total += expense.amount;
        }
        totalExpenseDisplay.setText("Total Expenses: $" + String.format("%.2f", total));
        updateCostDistribution(total);
    }

    // Update the cost distribution display
    private void updateCostDistribution(double total) {
        costDistributionDisplay.setText("");
        int participants = Integer.parseInt(JOptionPane.showInputDialog("Enter number of participants:"));
        double perPerson = total / participants;
        for (int i = 1; i <= participants; i++) {
            costDistributionDisplay.append("Participant " + i + ": $" + String.format("%.2f", perPerson) + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TripTracker().setVisible(true));
    }
}
