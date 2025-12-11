package solver;
public final class DigitFactory {

    private static final Digit[] mem= new Digit[10];

    static {
        for (int i = 1; i <= 9; i++) {
            mem[i] = new Digit(i);
        }
    }

    public static Digit getDigit(int value) {
        return mem[value];
    }
}
