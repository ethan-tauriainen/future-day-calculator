package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import business.CalendarCalculations;

public class FutureDayFrame extends JFrame {

    private final DateTimeFormatter DT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField daysOutField;
    private JTextField totalDaysField;
    private JTextField weekendDaysField;
    private JTextField weekdaysField;

    public FutureDayFrame() {
        initComponents();
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }

        setTitle("Future Day Calculator");
        setLocationByPlatform(true);

        LocalDate currentDate = LocalDate.now();

        startDateField = new JTextField(currentDate.format(DT));
        endDateField = new JTextField();
        daysOutField = new JTextField();
        totalDaysField = new JTextField();
        weekendDaysField = new JTextField();
        weekdaysField = new JTextField();

        totalDaysField.setEditable(false);
        weekendDaysField.setEditable(false);
        weekdaysField.setEditable(false);

        Dimension dim = new Dimension(150, 20);
        startDateField.setPreferredSize(dim);
        endDateField.setPreferredSize(dim);
        daysOutField.setPreferredSize(dim);
        totalDaysField.setPreferredSize(dim);
        weekendDaysField.setPreferredSize(dim);
        weekdaysField.setPreferredSize(dim);
        startDateField.setMinimumSize(dim);
        endDateField.setMinimumSize(dim);
        daysOutField.setMinimumSize(dim);
        totalDaysField.setMinimumSize(dim);
        weekendDaysField.setMinimumSize(dim);
        weekdaysField.setMinimumSize(dim);

        JButton calculateButton = new JButton("Calculate");
        JButton exitButton = new JButton("Exit");
        JButton resetButton = new JButton("Reset");

        calculateButton.addActionListener(e -> calculateButtonClicked());
        exitButton.addActionListener(e -> exitButtonClicked());
        resetButton.addActionListener(e -> resetButtonClicked(currentDate));

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(resetButton);

        // main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(new JLabel("Start date:"), getConstraints(0, 0));
        panel.add(startDateField, getConstraints(1, 0));
        panel.add(new JLabel("End date:"), getConstraints(0, 1));
        panel.add(endDateField, getConstraints(1, 1));
        panel.add(new JLabel("Days out:"), getConstraints(0, 2));
        panel.add(daysOutField, getConstraints(1, 2));
        panel.add(new JLabel("Weekdays:"), getConstraints(0, 3));
        panel.add(weekdaysField, getConstraints(1, 3));
        panel.add(new JLabel("Weekend days:"), getConstraints(0, 4));
        panel.add(weekendDaysField, getConstraints(1, 4));
        panel.add(new JLabel("Total days:"), getConstraints(0, 5));
        panel.add(totalDaysField, getConstraints(1, 5));


        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    // helper method for getting a GridBagConstraints object
    private GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 0, 10);
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    private void calculateButtonClicked() {
        LocalDate startDate, endDate;
        long totalDays, weekDays, weekendDays;
        int daysOut;

        // error messages
        String dateErrorMessage = "Date must be in the MM/dd/yyyy format.\n"
                + "Please re-enter.";

        String intErrorMessage = "Days out must be an integer.\n"
                + "Please re-enter.";

        String errorTitle = "Invalid Entry";

        if (daysOutField.getText().isEmpty()) {
            try {
                startDate = LocalDate.parse(startDateField.getText(), DT);
                endDate = LocalDate.parse(endDateField.getText(), DT);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, dateErrorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }
            weekDays = CalendarCalculations.calculateFutureDays(startDate, endDate);
        } else {
            try {
                startDate = LocalDate.parse(startDateField.getText(), DT);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, dateErrorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }
            endDate = startDate;

            try {
                daysOut = Integer.parseInt(daysOutField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, intErrorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }

            weekDays = CalendarCalculations.calculateFutureDays(startDate, daysOut);
            endDate = endDate.plus(daysOut, ChronoUnit.DAYS);
            endDateField.setText(endDate.format(DT));
        }

        if (startDate.isAfter(endDate)) { totalDays = ChronoUnit.DAYS.between(endDate, startDate); }
        else { totalDays = ChronoUnit.DAYS.between(startDate, endDate); }

        weekendDays = totalDays - weekDays;

        weekdaysField.setText(Long.toString(weekDays));
        weekendDaysField.setText(Long.toString(weekendDays));
        totalDaysField.setText(Long.toString(totalDays));
    }

    private void exitButtonClicked() { System.exit(0); }

    private void resetButtonClicked(LocalDate currentDate) {
        startDateField.setText(currentDate.format(DT));
        endDateField.setText("");
        daysOutField.setText("");
        totalDaysField.setText("");
        weekendDaysField.setText("");
        startDateField.requestFocus();
        weekdaysField.setText("");
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new FutureDayFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
