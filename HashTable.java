import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class HashTable {
    private LinkedList<Task>[] table;
    private int size;
    private double loadFactor;
    private int entries = 0;
    private int currSize = 0;

    @SuppressWarnings("unchecked")
    public HashTable(int size, double loadFactor) {
        this.size = size;
        this.loadFactor = loadFactor;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }
    
    public Task[] values() {
        Task[] values = new Task[size];
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i]!= null) {
                for (Task task : table[i]) {
                    values[index++] = task;
                }
            }
        }
        return values;
    }
//altered the hash function to distributly more uniformly 
private int hash(int priority) {
    int hash = 0;
    hash += priority;
    hash += (priority >> 10) ^ 0x12345678;
    hash += (priority >> 20) ^ 0x87654321;
    hash += (priority >> 30) ^ 0x55555555;
    return Math.abs(hash % size);
}
    public int currSize() {
        return this.currSize;
    }

    public void put(Task task) {
        if (entries >= size * loadFactor) {
            resize();
        }
        int index = hash(task.getUserSetPriority());
        table[index].add(task);
        entries++;
        this.currSize++;
    }

    public LinkedList<Task> get(int priority) {
        int index = hash(priority);
        return table[index];
    }

    public boolean remove(Task task) {
        int index = hash(task.getUserSetPriority());
        boolean wasRemoved = table[index].remove(task);
        if (wasRemoved) {
            this.currSize--;
        }
        return wasRemoved;
    }

    public boolean contains(Task task) {
        int index = hash(task.getUserSetPriority());
        return table[index].contains(task);
    }

    public boolean updatePriority(Task task, int newPriority) {
        if (remove(task)) {
            task.setUserSetPriority(newPriority);
            put(task);
            return true;
        } else {
            return false;
        }
    }
    

    @SuppressWarnings("unchecked")

    private void resize() {
        size = size * 2;
        LinkedList<Task>[] oldTable = table;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
        entries = 0;
        for (LinkedList<Task> bucket : oldTable) {
            for (Task task : bucket) {
                int index = hash(task.getUserSetPriority());
                table[index].add(task);
                entries++;
            }
        }
}
public int size() {
    return size;
}

public void add(Task task) {
    // Add the task to the hash table
    // Increment the size
    size++;
}


public Set<Task> keySet() {
    Set<Task> keys = new HashSet<>();
    for (int i = 0; i < table.length; i++) {
        if (table[i]!= null) {
            for (Task task : table[i]) {
                keys.add(task);
            }
        }
    }
    return keys;
}
  
}
