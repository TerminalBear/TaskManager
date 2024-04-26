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
<br>
Task.java<br>
Status: An enum representing the status of a task. It can be INCOMPLETE, IN_PROGRESS, or COMPLETE.<br>
Properties: The Task class has several properties including name, description, predictedPriority, userSetPriority, dueDate, creationTime, and status.<br>
Constructors: There are two constructors. The default constructor initializes the creationTime to the current date. The other constructor initializes the name, description, creationTime, and dueDate.<br>
Getters and Setters: These methods are used to get and set the properties of the Task class.<br>
setDueDate: This method attempts to parse a string into a LocalDate object and set it as the dueDate. If the parsing fails, it prints an error message and returns false.
toString: This method returns a string representation of the Task object.<br>
main: This is the entry point of the program. It creates a Task object, sets its properties, and prints it.<br>
<br>
Node.java<br>
condition: A string that represents the condition to be evaluated at this node.<br>
trueBranch and falseBranch: These are the two branches that stem from this node. If the condition at this node is true, the tree follows the trueBranch; if false, it follows the falseBranch.<br>
priority: This integer represents the priority of the node. It's used when the node is a leaf node (i.e., it has no branches).<br>
Constructors: There are two constructors. One takes a String condition and sets the priority to null. The other takes an Integer priority and sets the condition to null.<br>
<br>
DecisionTree.java<br>
root: The root node of the decision tree.<br>
DecisionTree(): The constructor of the class. It initializes the decision tree with a specific structure and conditions.<br>
predictPriority(Task task): This method traverses the decision tree based on the task's data and returns the predicted priority. It starts from the root and goes down the tree until it reaches a node with a priority (a leaf node).<br>
evaluateCondition(String condition, Task task): This private method evaluates a condition based on the task's data. It checks if the condition contains "daysBetween" or "descriptionLength" and evaluates the condition accordingly. There is an adjust method for altering the weights when feedback is provided.
<br>
HashTable.java<br>
table: An array of LinkedLists. Each LinkedList represents a bucket in the hash table.<br>
size: The size of the hash table.<br>
loadFactor: The load factor of the hash table. When the number of entries in the hash table exceeds the product of the load factor and the current size, the hash table is resized.<br>
entries: The number of entries currently in the hash table.<br>
currSize: The current size of the hash table.<br>
HashTable(int size, float loadFactor): The constructor of the class. It initializes the hash table with a specific size and load factor.<br>
hash(int priority): This private method calculates the hash value of a given priority.<br>
currSize(): This method returns the current size of the hash table.<br>
put(Task task): This method inserts a task into the hash table. If the number of entries exceeds the product of the load factor and the current size, the hash table is resized.<br>
get(int priority): This method returns the LinkedList (bucket) for a given priority.<br>
remove(Task task): This method removes a task from the hash table.<br>
contains(Task task): This method checks if a task is in the hash table.<br>
updatePriority(Task task, int newPriority): This method updates the priority of a task in the hash table.<br>
resize(): This private method resizes the hash table when the number of entries exceeds the product of the load factor and the current size. It creates a new table with double the size, and rehashes all the entries from the old table to the new table.<br>
<br>
Main.java<br>
Example implementation of HashTable with hardcoded tasks<br>
Next steps:<br>
•	Add submit button that takes table information and builds hastable using HashTable.java<br>
•	Develop Kruskal’s Algorithm and Boyer-Moore’s Algorithm<br>
•	Add algorithm functionality to Sort and Search Bar buttons<br>
•	Test<br>
•	Documentation<br>

