import java.util.LinkedList;

import javax.swing.JOptionPane;
public class TaskSearcher {
    private HashTable hashTable;

    public TaskSearcher(HashTable hashTable) {
        this.hashTable = hashTable;
    }
    public Task searchTask(String taskName) {
        // Implement the Boyer-Moore string search algorithm here
        // to search for the task in the hash table
        // Return the task if found, or null if not found
    
        // Create an array to store the skip values for each character in the pattern
        int[] skip = new int[256];
    
        // Initialize the skip values to the length of the pattern
        for (int i = 0; i < skip.length; i++) {
            skip[i] = taskName.length();
        }
    
        // Calculate the skip values for each character in the pattern
        for (int i = 0; i < taskName.length() - 1; i++) {
            skip[taskName.charAt(i)] = taskName.length() - i - 1;
        }
    
        // Initialize the text and pattern pointers
        int textPointer = 0;
        int patternPointer = taskName.length() - 1;
    
        // Search for the pattern in the text
        while (textPointer <= hashTable.size() - taskName.length()) {
            // Get the list from the hash table
            LinkedList<Task> taskList = hashTable.get(textPointer);
    
            // Check if the list is not empty
            if (taskList!= null &&!taskList.isEmpty()) {
                // Compare the characters in the pattern with the characters in the text
                while (patternPointer >= 0 && taskName.charAt(patternPointer) == taskList.get(0).getName().charAt(0)) {
                    patternPointer--;
                }
    
                // If the pattern was found, return the task
                if (patternPointer < 0) {
                    Task task = taskList.get(0);
                    JOptionPane.showMessageDialog(null, "Task found: " + task.getName() + "\nDescription: " + task.getDescription() + "\nCreation Date: " + task.getCreationDate() + "\nDue Date: " + task.getDueDate() + "\nStatus: " + task.getStatus() + "\nPriority: ", "Task Details", JOptionPane.INFORMATION_MESSAGE);
                    return task;
                }
            }
    
            // Update the text pointer using the skip value for the mismatched character
            try {
                if (taskList!= null &&!taskList.isEmpty()) {
                    textPointer += skip[taskList.get(0).getName().charAt(0)];
                } else {
                    textPointer++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
            patternPointer = taskName.length() - 1;
        }
    
    
        // Return null if the pattern was not found
        return null;
    }}