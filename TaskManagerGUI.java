import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JLabel;
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
import javax.swing.JOptionPane;
public class TaskManagerGUI {
    private TreeSet<Task> sortedTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            try {
                Date d1 = formatter.parse(t2.getDueDate());
                Date d2 = formatter.parse(t1.getDueDate());
                return d1.compareTo(d2);
            } catch (ParseException e) {
                return 0;
            }
        }
    });
    // Properties of the TaskManagerGUI class
    private JLabel resultLabel;

    private JFrame frame;
    private JTextField searchField;
    private JTextField addField;
    private JButton sortByNameButton;
    private JButton sortByDateButton;
    private JButton sortByPriorityButton;
    private JButton addTaskButton;
    private JButton removeTaskButton;
    private JButton sortByDueDateButton;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private DecisionTree predictor;
    private HashTable hashTable; 
    private TaskSearcher taskSearcher;// Declare the hashTable variable
    public TaskManagerGUI() {
        // Create the GUI
        hashTable = new HashTable(10, 0.75);
     Task[] tasks = new Task[] { new Task("Task 1", "Description for Task 1", LocalDate.now(), LocalDate.now().plusDays(5)), new Task("Task 2", "Description for Task 2", LocalDate.now(), LocalDate.now().plusDays(3)), new Task("Task 3", "Description for Task 3", LocalDate.now(), LocalDate.now().plusDays(7)), new Task("Task 4", "Description for Task 4", LocalDate.now(), LocalDate.now().plusDays(10)), new Task("Task 5", "Description for Task 5", LocalDate.now(), LocalDate.now().plusDays(2)), new Task("Task 6", "Description for Task 6", LocalDate.now(), LocalDate.now().plusDays(8)), new Task("Task 7", "Description for Task 7", LocalDate.now(), LocalDate.now().plusDays(12)), new Task("Task 8", "Description for Task 8", LocalDate.now(), LocalDate.now().plusDays(4)), new Task("Task 9", "Description for Task 9", LocalDate.now(), LocalDate.now().plusDays(6)), new Task("Task 10", "Description for Task 10", LocalDate.now(), LocalDate.now().plusDays(9)) };
    TaskManager taskManager = new TaskManager(tasks);
        taskSearcher = new TaskSearcher(hashTable);
        // Create a search field and a result label
        searchField = new JTextField(10);
       addField = new JTextField(10);
        resultLabel = new JLabel();
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
        sortByDueDateButton = new JButton("sort by due date");
        JButton searchButton = new JButton("Search");
        JButton TasklistButton = new JButton("Populate");
        
        JButton boyerMooreButton = new JButton("Boyer-Moore Search"); // Add a new button for Boyer-Moore search
        topPanel.add(searchField);
        topPanel.add(sortByNameButton);
        topPanel.add(sortByDateButton);
        topPanel.add(sortByPriorityButton);
        topPanel.add(addTaskButton);
        topPanel.add(removeTaskButton);
        topPanel.add(boyerMooreButton); // Add the new button to the top panel
        // Create a JPanel to hold the search button and result label
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(searchField);
       topPanel.add(addField);
        bottomPanel.add(resultLabel);
        bottomPanel.add(searchButton);
        bottomPanel.add(TasklistButton);
        bottomPanel.add(sortByDueDateButton);
        bottomPanel.setBackground(Color.DARK_GRAY);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel,BorderLayout.SOUTH);

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
       
        boyerMooreButton.addActionListener(e -> {
            String query = searchField.getText();
            List<Task> results = taskManager.searchTasks(query);
        
            if (!results.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Task task : results) {
                    sb.append("Name: ").append(task.getName()!= null? task.getName() : "Empty").append("\n");
                    sb.append("Description: ").append(task.getDescription()!= null? task.getDescription() : "Empty").append("\n");
                    sb.append("Creation Date: ").append(task.getCreationDate()!= null? task.getCreationDate().toString() : "Empty").append("\n");
                    sb.append("Due Date: ").append(task.getDueDate()!= null? task.getDueDate().toString() : "Empty").append("\n");
                    sb.append("Status: ").append(task.getStatus()!= null? task.getStatus() : "Empty").append("\n");
                    sb.append("\n");
                }
                JOptionPane.showMessageDialog(frame, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No results found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        TasklistButton.addActionListener(e -> addTasklist());
        addTaskButton.addActionListener(e -> addTask());
        removeTaskButton.addActionListener(e -> removeTask());
        searchButton.addActionListener(e -> {
            String taskName = searchField.getText();
           
                Task task = searchTask(taskName);
                if (task!= null) {
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (task.equals(tableModel.getValueAt(i, 0))) {
                            tableModel.removeRow(i);
                        }
                    }
                }
                else {
                    resultLabel.setText("Not found");
                }
           
        });
     
        sortByDueDateButton.addActionListener(e -> {
            // Clear the table model
           // tableModel.setRowCount(0);
        
            // Add the sorted tasks from the TreeSet to the table model
            for (Task task : sortedTasks) {
                tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
            }
        });
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
        searchField.setText("No input");
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
  
    // Add the searchTask method to the TaskManagerGUI class
    public  void addTasklist() {
        Task[] tasks = { new Task("Task 1", "Description for Task 1", LocalDate.now(), LocalDate.now().plusDays(5)),
        new Task("Task 2", "Description for Task 2", LocalDate.now(), LocalDate.now().plusDays(3)),
        new Task("Task 3", "Description for Task 3", LocalDate.now(), LocalDate.now().plusDays(7)),
        new Task("Task 4", "Description for Task 4", LocalDate.now(), LocalDate.now().plusDays(10)),
        new Task("Task 5", "Description for Task 5", LocalDate.now(), LocalDate.now().plusDays(2)),
        new Task("Task 6", "Description for Task 6", LocalDate.now(), LocalDate.now().plusDays(8)),
        new Task("Task 7", "Description for Task 7", LocalDate.now(), LocalDate.now().plusDays(12)),
        new Task("Task 8", "Description for Task 8", LocalDate.now(), LocalDate.now().plusDays(4)),
        new Task("Task 9", "Description for Task 9", LocalDate.now(), LocalDate.now().plusDays(6)),
        new Task("Task 10", "Description for Task 10", LocalDate.now(), LocalDate.now().plusDays(9)) };

// Add each task to the hash table and the sorted tasks list
for (Task task : tasks) {
    hashTable.add(task);
    sortedTasks.add(task);
}

// Add each task to the table model
for (Task task : tasks) {
    tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
}
// Add tasks 11-20

    }
      public Task searchTask(String taskName) {
        // Create a new instance of the TaskSearcher class
        TaskSearcher taskSearcher = new TaskSearcher(hashTable);

        // Use the TaskSearcher class to search for the task
        Task task = taskSearcher.searchTask(taskName);

        // Return the task if found, or null if not found
        return task;
    }
    public void addTask() {
        // Get the name, description, creation date, and due date from the user
        String name = JOptionPane.showInputDialog("Enter the name of the task:");
        String description = JOptionPane.showInputDialog("Enter the description of the task:");
        LocalDate creationDate = LocalDate.now();
        String dueDateInput = JOptionPane.showInputDialog("Enter the due date (MM/dd/yyyy):");
        Date dueDate = null;
        try {
            dueDate = formatter.parse(dueDateInput);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid due date format. Please use MM/dd/yyyy.");
            return;
        }
    
        // Create a new Task object with the name, description, creation date, and due date
        Task task = new Task(name, description, creationDate, dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    
        // Add the task to the hash table
        hashTable.add(task);
    
        // Add the task to the TreeSet
        sortedTasks.add(task);
    
        // Add the task to the table model
        TaskManager.addTask(null, task);
        tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
        if (task == null) {
            System.out.println("Task is null");
        } else {
            System.out.println("Task is not null");
        }
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