import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // Create a hash table with a size of 10
        HashTable hashTable = new HashTable(10, 0.75f);

        // Create some tasks with different priorities
        Task task1 = new Task();
        task1.setUserSetPriority(1);
        task1.setName("Task 1");
        task1.setDescription("This is task 1");

        Task task2 = new Task();
        task2.setUserSetPriority(2);
        task2.setName("Task 2");
        task2.setDescription("This is task 2");

        Task task3 = new Task();
        task3.setUserSetPriority(1);
        task3.setName("Task 3");
        task3.setDescription("This is task 3");

        // Add tasks to the hash table
        hashTable.put(task1);
        hashTable.put(task2);
        hashTable.put(task3);

        // Retrieve tasks with priority 1
        LinkedList<Task> tasksWithPriority1 = hashTable.get(1);
        System.out.println("Tasks with priority 1:");
        for (Task task : tasksWithPriority1) {
            System.out.println(task.getName() + ": " + task.getDescription());
        }
        // Check if task1 is in the hash table
        if (hashTable.contains(task1)) {
            System.out.println("Task 1 is in the hash table.");
        } else {
            System.out.println("Task 1 is not in the hash table.");
        }
        // Retrieve tasks with priority 2
        LinkedList<Task> tasksWithPriority2 = hashTable.get(2);
        System.out.println("Tasks with priority 2:");
        for (Task task : tasksWithPriority2) {
            System.out.println(task.getName() + ": " + task.getDescription());
        }
        // Remove task1 from the hash table and retrieve tasks with priority 1 again
        hashTable.remove(task1);
        System.out.println("Tasks with priority 1:");
        for (Task task : tasksWithPriority1) {
            System.out.println(task.getName() + ": " + task.getDescription());
        }
        // Check if task1 is in the hash table again
        if (hashTable.contains(task1)) {
            System.out.println("Task 1 is in the hash table.");
        } else {
            System.out.println("Task 1 is not in the hash table.");
        }
        // Update the priority of task2 and retrieve tasks with priority 2 again
        LinkedList<Task> tasksWithPriority3 = hashTable.get(3);
        hashTable.updatePriority(task2, 3);
        System.out.println("Tasks with priority 3:");
        for (Task task : tasksWithPriority3) {
            System.out.println(task.getName() + ": " + task.getDescription());
        }
        System.out.println("Number of tasks in hash table: " + hashTable.currSize());
    }
}