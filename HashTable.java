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
    //resize function altered to 
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
}}
// potential change using an array.
/*private Task[] table;

public HashTable(int size, float loadFactor) {
    this.size = size;
    this.loadFactor = loadFactor;
    table = new Task[size];
    for (int i = 0; i < size; i++) {
        table[i] = null;
    }
}

private int hash(int priority) {
    return priority % size;
}

public void put(Task task) {
    if (entries >= size * loadFactor) {
        resize();
    }
    int index = hash(task.getUserSetPriority());
    if (table[index] == null) {
        table[index] = new Task[1];
    }
    table[index][0] = task;
    entries++;
    this.currSize++;
}

public Task get(int priority) {
    int index = hash(priority);
    if (table[index] == null) {
        return null;
    }
    return table[index][0];
}

public boolean remove(Task task) {
    int index = hash(task.getUserSetPriority());
    if (table[index] == null) {
        return false;
    }
    table[index] = null;
    this.currSize--;
    return true;
}

public boolean contains(Task task) {
    int index = hash(task.getUserSetPriority());
    if (table[index] == null) {
        return false;
    }
    return table[index][0].equals(task);
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

private void resize() {
    size = size * 2;
    Task[] oldTable = table;
    table = new Task[size];
    for (int i = 0; i < size; i++) {
        table[i] = null;
    }
    entries = 0;
    for (Task task : oldTable) {
        if (task!= null) {
            put(task);
        }
    }
} */