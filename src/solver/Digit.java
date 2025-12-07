package solver;
// Digit.java
public final class Digit {
    private final int value;

    Digit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
