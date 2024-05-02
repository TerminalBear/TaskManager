public class BoyerMoore {
    private int[] charTable;
    private int[] offsetTable;
    private String pattern;

    public BoyerMoore(String pattern) {
        this.pattern = pattern;
        charTable = new int[256];
        offsetTable = new int[pattern.length()];

        // Initialize the character table
        for (int i = 0; i < charTable.length; i++) {
            charTable[i] = pattern.length();
        }
        for (int i = 0; i < pattern.length() - 1; i++) {
            charTable[pattern.charAt(i)] = pattern.length() - 1 - i;
        }

        // Initialize the offset table
        int shift = 1;
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (pattern.charAt(i) == pattern.charAt(pattern.length() - shift)) {
                offsetTable[i] = shift;
            } else {
                shift = 1;
            }
        }
    }

    public int search(String text) {
        int textLength = text.length();
        int patternLength = pattern.length();
        int i = patternLength - 1;
        int j;
        while (i < textLength) {
            j = patternLength - 1;
            while (j >= 0 && text.charAt(i) == pattern.charAt(j)) {
                i--;
                j--;
            }
            if (j < 0) {
                return i + 1;
            }
            i += Math.max(offsetTable[j], charTable[text.charAt(i)]);
        }
        return -1;
    }
}