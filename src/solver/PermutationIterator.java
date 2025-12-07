package solver;
// PermutationIterator.java
import java.util.Arrays;
import java.util.NoSuchElementException;

public class PermutationIterator {

    private final int[] counters = new int[5]; 
    private boolean hasNext = true;

    public PermutationIterator() {
        Arrays.fill(counters, 1); 
    }

    public boolean hasNext() {
        return hasNext;
    }
    public Digit[] next() {
        if (!hasNext) {
            throw new NoSuchElementException("No more permutations");
        }

        Digit[] result = new Digit[5];
        for (int i = 0; i < 5; i++) {
            result[i] = DigitFactory.getDigit(counters[i]);
        }

        incrementCounters();
        return result;
    }

    private void incrementCounters() {
        for (int i = counters.length - 1; i >= 0; i--) {
            if (counters[i] < 9) {
                counters[i]++;
                return;
            } else {
                counters[i] = 1; 
            }
        }
        hasNext = false; 
    }
}
