package solver;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class GuessIterator implements Iterator<int[]> {

    private int current = 0;
    private final int max = (int) Math.pow(9, 5);

    @Override
    public boolean hasNext() {
        return current < max;
    }

    @Override
    public int[] next() {
        if (!hasNext()) throw new NoSuchElementException();

        int[] digits = new int[5];
        int temp = current;

        for (int i = 4; i >= 0; i--) {
            digits[i] = (temp % 9) + 1;
            temp /= 9;
        }

        current++;
        return digits;
    }
}
