package dt_maze_challenge.maze;

import java.util.Objects;

public class CoordinateWithPrevious {

    private final Coordinate current;
    private final Coordinate previous;
    private int length;
    private int wait;
    private int cost;

    public CoordinateWithPrevious(Coordinate current, Coordinate previous) {
        this.current = current;
        this.previous = previous;
    }

    public CoordinateWithPrevious(Coordinate current, Coordinate previous, int length, int cost) {
        this.current = current;
        this.previous = previous;
        this.length = length;
        this.cost = cost;
    }

    public Coordinate getCurrent() {
        return current;
    }

    public Coordinate getPrevious() {
        return previous;
    }

    public int getLength() {
        return length;
    }

    public void increaseLength() {
        length++;
    }

    public void increaseCost() {
        cost++;
    }

    public int getCost() {
        return cost;
    }

    public void forceToWait() {
        this.wait = this.length;
    }

    public void decreaseWait() {
        if (this.wait > 0) {
            wait--;
        }
    }

    public boolean notWaiting() {
        return wait == 0;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateWithPrevious that = (CoordinateWithPrevious) o;
        return Objects.equals(current, that.current);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(current);
    }
}
