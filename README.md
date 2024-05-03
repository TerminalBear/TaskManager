# TaskManager
TaskManagerGUI
<br>
The TaskManagerGUI constructor initializes the JFrame and its components. It sets up a JPanel with a BorderLayout, and adds a top panel for the search field and buttons, and a center panel for the JTable.<br>
The JTable is initialized with a DefaultTableModel that allows all cells to be editable. The table model is set up with seven columns: "Name", "Description", "Creation Date", "Due Date", "Status", "Priority", and "Suggested Priority".<br>
The "Status" and "Priority" columns use JComboBoxes for cell editing. The "Creation Date" and "Due Date" columns use a custom renderer and editor for displaying and editing dates. The "Suggested Priority" column uses a custom renderer and editor for a "Predict" button.<br>
The addTask method adds a new row to the table with default values. The removeTask method removes the selected rows from the table.
The DateRenderer class is a custom cell renderer for displaying dates in the "Creation Date" and "Due Date" columns.<br>
The PredictButtonRenderer and PredictButtonEditor classes are custom cell renderer and editor for the "Predict" button in the "Suggested Priority" column. When the button is clicked, it uses a DecisionTree predictor to predict the priority of the task and updates the cell value.<br>
The DateEditor class is a custom cell editor for editing dates in the "Due Date" column.<br>
There is a class for priority visualization using a bar graph when Task and Priority button is clicked.<br>
There is a feedback button for "Inaccurate Prediction" which acknowledges and adjusts decision tree weights.<br>
The main method creates an instance of the TaskManagerGUI class, which launches the application.<br>
GUI also includes buttons for sorting, word search, and a graphical visualization of the tasks and priorities.<br>
<br>
Main.java<br>
Example implementation of HashTable with hardcoded tasks<br>

