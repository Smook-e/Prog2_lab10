package solver;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class PermutationIterator implements iterator<int[]> { 
    private final int[] counters = new int[5]; 
    private boolean hasNext = true; 
    public PermutationIterator() { 
        Arrays.fill(counters, 1); } 
    @Override 
    public boolean hasNext() { 
        return hasNext; } 
    @Override 
    public int[] next() { 
        if (!hasNext) { 
            throw new NoSuchElementException("No more permutations"); } 
        int[] result = counters.clone(); 
        incrementCounters(); 
        return result; 
    } 
    private void incrementCounters() { 
        for (int i = counters.length - 1; i >= 0; i--) { 
            if (counters[i] < 9) { 
                counters[i]++; return; 
            } 
            else { counters[i] = 1;
            } 
        } 
        hasNext = false; } 
}