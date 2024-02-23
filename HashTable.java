import java.util.LinkedList;

public class HashTable {
    private LinkedList<Task>[] table;
    private int size;
    private float loadFactor;
    private int entries = 0;
    private int currSize = 0;

    @SuppressWarnings("unchecked")
    public HashTable(int size, float loadFactor) {
        this.size = size;
        this.loadFactor = loadFactor;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(int priority) {
        return priority % size;
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
                put(task);
            }
        }
    }
}