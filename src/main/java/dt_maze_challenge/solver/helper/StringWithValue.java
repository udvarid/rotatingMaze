package dt_maze_challenge.solver.helper;

public class StringWithValue {
    String combination;
    int value;

    public StringWithValue(String combination, int value) {
        this.combination = combination;
        this.value = value;
    }

    public String getCombination() {
        return combination;
    }

    public int getValue() {
        return value;
    }
}