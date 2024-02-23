import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TaskManagerGUI {
    // Properties of the TaskManagerGUI class
    private JFrame frame;
    private JTextField searchField;
    private JButton sortByNameButton;
    private JButton sortByDateButton;
    private JButton sortByPriorityButton;
    private JButton addTaskButton;
    private JButton removeTaskButton;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private DecisionTree predictor;

    public TaskManagerGUI() {
        // Create the GUI
        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.DARK_GRAY);
        searchField = new JTextField(20);
        sortByNameButton = new JButton("Sort by Name");
        sortByDateButton = new JButton("Sort by Date");
        sortByPriorityButton = new JButton("Sort by Priority");
        addTaskButton = new JButton("Add Task");
        removeTaskButton = new JButton("Remove Task");
        topPanel.add(searchField);
        topPanel.add(sortByNameButton);
        topPanel.add(sortByDateButton);
        topPanel.add(sortByPriorityButton);
        topPanel.add(addTaskButton);
        topPanel.add(removeTaskButton);
        panel.add(topPanel, BorderLayout.NORTH);

        predictor = new DecisionTree();
        // Create the table
        String[] columnNames = { "Name", "Description", "Creation Date", "Due Date", "Status", "Priority",
                "Suggested Priority" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);

        addTaskButton.addActionListener(e -> addTask());
        removeTaskButton.addActionListener(e -> removeTask());

        // Set the status column to use a JComboBox
        String[] statuses = { "INCOMPLETE", "IN_PROGRESS", "COMPLETE" };
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setFont(new Font("Arial", Font.PLAIN, 10)); // Set font size to 10
        taskTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(statusComboBox));

        // Set the priority column to use a JComboBox
        String[] priorities = { "1 (High)", "2", "3 (Medium)", "4", "5 (Low)" };
        JComboBox<String> priorityComboBox = new JComboBox<>(priorities);
        taskTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(priorityComboBox));

        // Set the creation date and due date columns to use a custom renderer and
        // editor
        taskTable.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer());
        taskTable.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());
        taskTable.getColumnModel().getColumn(3).setCellEditor(new DateEditor());

        taskTable.getColumnModel().getColumn(6).setCellRenderer(new PredictButtonRenderer());
        taskTable.getColumnModel().getColumn(6).setCellEditor(new PredictButtonEditor(new JTextField()));

        // GUI styling
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        topPanel.setBackground(Color.DARK_GRAY);
        searchField.setBackground(Color.WHITE);
        sortByNameButton.setBackground(Color.LIGHT_GRAY);
        sortByDateButton.setBackground(Color.LIGHT_GRAY);
        sortByPriorityButton.setBackground(Color.LIGHT_GRAY);
        addTaskButton.setBackground(Color.GREEN);
        removeTaskButton.setBackground(Color.RED);
    }

    // Methods of the TaskManagerGUI class
    public void addTask() {
        tableModel.addRow(new Object[] { "", "", new Date(), "", "INCOMPLETE", "", "" });
    }

    public void removeTask() {
        int[] selectedRows = taskTable.getSelectedRows();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            tableModel.removeRow(selectedRows[i]);
        }
    }

    // Custom renderer for date columns
    class DateRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof Date) {
                value = formatter.format((Date) value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Predict button column
    class PredictButtonRenderer extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText((value == null) ? "Predict" : value.toString());
            return this;
        }
    }

    class PredictButtonEditor extends AbstractCellEditor implements TableCellEditor {
        JButton button;
        int predictedPriority;
        int editedRow;

        public PredictButtonEditor(JTextField textField) {
            button = new JButton();
            button.addActionListener(e -> {
                // Extract parameters from edited row and pass them to the predict method
                String name = (String) taskTable.getValueAt(editedRow, 0);
                String description = (String) taskTable.getValueAt(editedRow, 1);
                LocalDate creationDate = LocalDate.now();
                Date dueDate = (Date) taskTable.getValueAt(editedRow, 3);
                LocalDate localDueDate = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Task task = new Task(name, description, creationDate, localDueDate);
                predictedPriority = predictor.predictPriority(task);

                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            editedRow = row;
            button.setText((value == null) ? "Predict" : value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return predictedPriority;
        }
    }

    // Custom editor for due date column
    class DateEditor extends AbstractCellEditor implements TableCellEditor {
        private JTextField textField = new JTextField();

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            if (value instanceof Date) {
                textField.setText(formatter.format((Date) value));
            }
            return textField;
        }

        @Override
        public Object getCellEditorValue() {
            try {
                return formatter.parse(textField.getText());
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        // Create an instance of the TaskManagerGUI class
        new TaskManagerGUI();
    }
}