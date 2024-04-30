import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Task[] tasks;
    private HashTable hashTable;
    public List<Task> searchTasks(String query) {
        BoyerMoore boyerMoore = new BoyerMoore(query);
        List<Task> results = new ArrayList<>();
        for (Task task : hashTable.values()) {
            if (task!= null && (boyerMoore.search(task.getName())!= -1 || boyerMoore.search(task.getDescription())!= -1)) {
                results.add(task);
            }
        }
        return results;
    }
    
    public TaskManager(Task[] tasks) {
        this.tasks = tasks;
        this.hashTable = new HashTable(10, 0.75);
        convertArrayToHashTable();
        
    } public static void addTask(TaskManager taskManager, Task task) {
      
        taskManager.hashTable.put(task);
    }

    private void convertArrayToHashTable() {
        for (Task task : tasks) {
            if (task!= null) {
                hashTable.put(task);
            }
        }
        tasks = null;
    }

    // Other methods for managing tasks can be added here
}