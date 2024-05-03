import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner.DateEditor;
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
    private JButton listTaskButton;
    private JButton feedbackButton;
    private JButton SortByDueDate;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private DecisionTree predictor;
    private TaskSearcher taskSearcher;
    private HashTable hashTable; 
    private JLabel resultLabel;
    private TaskManager taskManger;


    public TaskManagerGUI() {
        // Create the GUI
       
        predictor = new DecisionTree();
        hashTable = new HashTable(10, 0.75);
        Task[] tasks = new Task[] { new Task("Development", "Description for Task 1", LocalDate.now(), LocalDate.now().plusDays(5)), new Task("Research", "Description for Task 2", LocalDate.now().minusDays(7), LocalDate.now().plusDays(3)), new Task("Draft", "Description for Task 3",  LocalDate.now().minusDays(4), LocalDate.now().plusDays(7)), new Task("Proposal", "Description for Task 4", LocalDate.now().minusDays(10), LocalDate.now().plusDays(10)), new Task("Final`", "Description for Task 5",  LocalDate.now().minusDays(1), LocalDate.now().plusDays(2)), new Task("Walk", "Description for Task 6", LocalDate.now().minusDays(12), LocalDate.now().plusDays(8)), new Task("Editing", "Description for Task 7", LocalDate.now().minusDays(20), LocalDate.now().plusDays(12)), new Task("Party", "Description for Task 8", LocalDate.now().minusDays(30), LocalDate.now().plusDays(4)), new Task("Refractoring", "Description for Task 9", LocalDate.now().minusDays(60), LocalDate.now().plusDays(6)), new Task("Fitness planning", "Description for Task 10",LocalDate.now().minusDays(90), LocalDate.now().plusDays(9)) };
        TaskManager taskManager = new TaskManager(tasks);
        taskSearcher = new TaskSearcher(hashTable);
        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.DARK_GRAY);
        searchField = new JTextField(20);
        SortByDueDate = new JButton("Sort by Due Date ");
        sortByNameButton = new JButton("Sort by Name");
        sortByDateButton = new JButton("Sort by Date");
        sortByPriorityButton = new JButton("Sort by Priority");
        addTaskButton = new JButton("Add Task");
        removeTaskButton = new JButton("Remove Task");
        listTaskButton = new JButton("Task and Priority");
        feedbackButton = new JButton("Inaccurate Prediction");
        JButton searchButton = new JButton("Search");
        JButton TasklistButton = new JButton("Populate");
        resultLabel = new JLabel();
        JButton boyerMooreButton = new JButton("Boyer-Moore Search"); // Add a new button for Boyer-Moore search
        JPanel bottomPanel = new JPanel();
        topPanel.add(searchField);
        bottomPanel.add(sortByNameButton);
        topPanel.add(sortByDateButton);
       // topPanel.add(sortByPriorityButton);
        topPanel.add(addTaskButton);
        topPanel.add(removeTaskButton);
        topPanel.add(listTaskButton);
    
        bottomPanel.add(SortByDueDate);
        bottomPanel.add(boyerMooreButton);
        bottomPanel.add(searchField);
        bottomPanel.add(resultLabel);
       // bottomPanel.add(searchButton);
        bottomPanel.add(TasklistButton);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel,BorderLayout.SOUTH);
        TasklistButton.addActionListener(e -> addTasklist());
        feedbackButton.addActionListener(e -> {
            // Get the selected row
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the task and the predicted priority from the selected row
                Task task = (Task) tableModel.getValueAt(selectedRow, 0);
                int predictedPriority = (int) tableModel.getValueAt(selectedRow, 6);

                // Adjust the decision tree
                predictor.adjust(task, predictedPriority);
            }
            JOptionPane.showMessageDialog(null, "Feedback Accepted", "Information", JOptionPane.INFORMATION_MESSAGE);
        });
        topPanel.add(feedbackButton);
       SortByDueDate.addActionListener(e -> sortByDueDate());

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
        sortByNameButton.addActionListener(e -> sortTasksByName());
        listTaskButton.addActionListener(e -> {
            // new JFrame
            JFrame outputFrame = new JFrame("Task Details");
            outputFrame.setSize(600, 600); // Increase the height of the frame
            outputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // JPanel with a BoxLayout
            JPanel innerPanel = new JPanel();
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

            // Iterate over all rows in the table
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                // Get the task name and priority from the row
                String taskName = (String) tableModel.getValueAt(row, 0);
                String taskPriority = (String) tableModel.getValueAt(row, 5);

                // bar graph
                int priority = Integer.parseInt(taskPriority.split(" ")[0]); // Extract the priority number
                JPanel bar = new JPanel();
                bar.setBackground(Color.BLUE);
                bar.setPreferredSize(new Dimension(20, priority * priority * 20)); // Set the height of the bar
                                                                                   // proportional to the square of the
                                                                                   // priority

                JLabel label = new JLabel("Task: " + taskName + ", Priority: " + taskPriority);

                // panel for the bar and label, and add them to it
                JPanel barPanel = new JPanel();
                barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.Y_AXIS));
                barPanel.add(Box.createVerticalGlue()); // Add an empty Box component with a flexible vertical size
                barPanel.add(bar); // Add the bar
                barPanel.add(label); // Add the label

                // Add the barPanel to the innerPanel
                innerPanel.add(barPanel);
            }

            // Add the innerPanel to the JFrame
            outputFrame.add(innerPanel);

            // Make the JFrame visible
            outputFrame.setVisible(true);
        });

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
        sortByDateButton.addActionListener(e -> sortTasksByDate());
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
  
    public void sortTasksByName() {
        // Get the tasks from the table model
        int rowCount = tableModel.getRowCount();
        Task[] tasks = new Task[rowCount];
        for (int i = 0; i < rowCount; i++) {
            tasks[i] = new Task((String) tableModel.getValueAt(i, 0), (String) tableModel.getValueAt(i, 1),(LocalDate) tableModel.getValueAt(i, 2), (LocalDate) tableModel.getValueAt(i, 3));
        }
    
        // Sort the tasks using Quick Sort
        quickSort(tasks, 0, tasks.length - 1);
    
        // Update the table model with the sorted tasks
        tableModel.setRowCount(0);
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
        }
    }
    private void quickSort(Task[] tasks, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(tasks, low, high);
            quickSort(tasks, low, pivotIndex - 1);
            quickSort(tasks, pivotIndex + 1, high);
        }
    }
    public void sortTasksByDate() {
        int rowCount = tableModel.getRowCount();
        Task[] tasks = new Task[rowCount];
        for (int i = 0; i < rowCount; i++) {
            String name = (String) tableModel.getValueAt(i, 0);
            String description = (String) tableModel.getValueAt(i, 1);
            String creationDateString = (String) tableModel.getValueAt(i, 2);
            String dueDateString = (String) tableModel.getValueAt(i, 3);
            LocalDate creationDate = LocalDate.parse(creationDateString);
            LocalDate dueDate = LocalDate.parse(dueDateString);
            tasks[i] = new Task(name, description, creationDate, dueDate);
        }
    
        // Sort the tasks using Merge Sort
        mergeSort(tasks, 0, tasks.length - 1);
    
        // Update the table model with the sorted tasks
        tableModel.setRowCount(0);
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
        }
    }
    
    private void mergeSort(Task[] tasks, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(tasks, left, mid);
            mergeSort(tasks, mid + 1, right);
            merge(tasks, left, mid, right);
        }
    }
    
    private void merge(Task[] tasks, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
    
        Task[] L = new Task[n1];
        Task[] R = new Task[n2];
    
        for (int i = 0; i < n1; i++) {
            L[i] = tasks[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = tasks[mid + 1 + j];
        }
    
        int i = 0;
        int j = 0;
        int k = left;
    
        while (i < n1 && j < n2) {
            if (L[i].getCreationDate().compareTo(R[j].getCreationDate()) <= 0) {
                tasks[k] = L[i];
                i++;
            } else {
                tasks[k] = R[j];
                j++;
            }
            k++;
        }
    
        while (i < n1) {
            tasks[k] = L[i];
            i++;
            k++;
        }
    
        while (j < n2) {
            tasks[k] = R[j];
            j++;
            k++;
        }
    }
    public void sortByDueDate() {
        int rowCount = tableModel.getRowCount();
        Task[] tasks = new Task[rowCount];
        for (int i = 0; i < rowCount; i++) {
            tasks[i] = new Task((String) tableModel.getValueAt(i, 0), (String) tableModel.getValueAt(i, 1), LocalDate.now(), (LocalDate) tableModel.getValueAt(i, 3));
        }
    
        // Sort the tasks using Bubble Sort
        bubbleSort(tasks);
    
        // Update the table model with the sorted tasks
        tableModel.setRowCount(0);
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
        }
    }
    
    private void bubbleSort(Task[] tasks) {
        int n = tasks.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tasks[j].getDueDate().compareTo(tasks[j + 1].getDueDate()) > 0) {
                    // Swap tasks[j] and tasks[j + 1]
                    Task temp = tasks[j];
                    tasks[j] = tasks[j + 1];
                    tasks[j + 1] = temp;
                }
            }
        }
    }
    

    private int partition(Task[] tasks, int low, int high) {
        Task pivot = tasks[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (tasks[j].getName().compareTo(pivot.getName()) < 0) {
                i++;
                swap(tasks, i, j);
            }
        }
        swap(tasks, i + 1, high);
        return i + 1;
    }

    private void swap(Task[] tasks, int i, int j) {
        Task temp = tasks[i];
        tasks[i] = tasks[j];
        tasks[j] = temp;
    }
    // Methods of the TaskManagerGUI class
    public void addTask() {
        // Get the name, description, creation date, and due date from the user
        String name = JOptionPane.showInputDialog("Enter the name of the task:");
        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name is required.");
            return;
        }
        String description = JOptionPane.showInputDialog("Enter the description of the task:");
        LocalDate creationDate = LocalDate.now();
        String dueDateInput = JOptionPane.showInputDialog("Enter the due date (MM/dd/yyyy):");
        if (dueDateInput == null || dueDateInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Due date is required.");
            return;
        }
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
    
        // Add the task to the table model
        tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
    }
    public void removeTask() {
        int[] selectedRows = taskTable.getSelectedRows();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            tableModel.removeRow(selectedRows[i]);
        }
    }
    public  void addTasklist() {
        Task[] tasks = { new Task("Development", "Description for Task 1", LocalDate.now(), LocalDate.now().plusDays(5)),
        new Task("Research", "Description for Task 2", LocalDate.now().minusDays(7), LocalDate.now().plusDays(3)),
        new Task("Draft", "Description for Task 3", LocalDate.now().minusDays(4), LocalDate.now().plusDays(7)),
        new Task("Proposal", "Description for Task 4", LocalDate.now().minusDays(10), LocalDate.now().plusDays(10)),
        new Task("Final", "Description for Task 5", LocalDate.now().minusDays(1), LocalDate.now().plusDays(2)),
        new Task("Walk", "Description for Task 6", LocalDate.now().minusDays(12), LocalDate.now().plusDays(8)),
        new Task("Editing", "Description for Task 7", LocalDate.now().minusDays(20), LocalDate.now().plusDays(12)),
        new Task("Party", "Description for Task 8", LocalDate.now().minusDays(30), LocalDate.now().plusDays(4)),
        new Task("Refractoring", "Description for Task 9", LocalDate.now().minusDays(60), LocalDate.now().plusDays(6)),
        new Task("Fitness planning", "Description for Task 10", LocalDate.now().minusDays(90), LocalDate.now().plusDays(9)) };

// Add each task to the hash table and the sorted tasks list
for (Task task : tasks) {
    hashTable.add(task);
    //sortedTasks.add(task);
}

// Add each task to the table model
for (Task task : tasks) {
    tableModel.addRow(new Object[]{task.getName(), task.getDescription(), task.getCreationDate(), task.getDueDate(), task.getStatus()});
}
// Add tasks 11-20

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
        // n instance of the TaskManagerGUI class
        new TaskManagerGUI();
    }
}