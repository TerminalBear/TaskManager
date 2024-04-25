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
            // Compare the characters in the pattern with the characters in the text
            while (patternPointer >= 0 && taskName.charAt(patternPointer) == hashTable.get(textPointer + patternPointer).getFirst().getName().charAt(0)) {
                patternPointer--;
            }

            // If the pattern was found, return the task
            if (patternPointer < 0) {
                return hashTable.get(textPointer).getFirst();
            }

            // Update the text pointer using the skip value for the mismatched character
            textPointer += skip[hashTable.get(textPointer + patternPointer).getFirst().getName().charAt(0)];
            patternPointer = taskName.length() - 1;
        }

        // Return null if the pattern was not found
        return null;
    }
}