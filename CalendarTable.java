import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CalendarTable {
    public JTable table;
    public DefaultTableModel model;

    public CalendarTable(LocalDate date) {
        model = new DefaultTableModel(new Object[]{"Day", "Date"}, 0);
        table = new JTable(model);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String monthYear = formatter.format(date);

        int daysInMonth = (int) ChronoUnit.DAYS.between(date.withDayOfMonth(1), date.plusMonths(1).withDayOfMonth(1));
        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate day = date.withDayOfMonth(i);
            model.addRow(new Object[]{i, day});
        }

        table.setPreferredScrollableViewportSize(new Dimension(200, 200));
        table.setFillsViewportHeight(true);

        JFrame frame = new JFrame(monthYear);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CalendarTable(LocalDate.now());
    }
}