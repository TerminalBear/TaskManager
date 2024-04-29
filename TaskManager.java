public class TaskManager {
    private Task[] tasks;
    private HashTable hashTable;

    public TaskManager(Task[] tasks) {
        this.tasks = tasks;
        this.hashTable = new HashTable(10, 0.75);
        convertArrayToHashTable();
        
    }

    private void convertArrayToHashTable() {
        for (Task task : tasks) {
            hashTable.put(task);
        }
        tasks = null;
    }

    // Other methods for managing tasks can be added here
}