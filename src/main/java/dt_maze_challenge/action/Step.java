package dt_maze_challenge.action;

public class Step {
    private int direction;
    private int length;

    public Step(int direction, int length) {
        this.direction = direction;
        this.length = length;
    }

    public int getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }
}

