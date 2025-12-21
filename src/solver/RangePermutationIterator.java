package solver;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class RangePermutationIterator implements iterator<int[]> {

    private final int start;
    private final int end;
    private int current;
    private final int size; 

    public RangePermutationIterator(int start, int end, int size) {
        this.start = start;
        this.end = end;
        this.current = start;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return current <= end;
    }

    @Override
    public int[] next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        int[] result = getguess(current);
        current++;
        return result;
    }
    private int[] getguess(int index) {
        int[] digits = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            digits[i] = (index % 9) + 1;
            index /= 9;
        }
        return digits;
    }
}
