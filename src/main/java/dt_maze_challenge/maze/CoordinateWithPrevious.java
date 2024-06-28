package dt_maze_challenge.maze;

import java.util.Objects;

public class CoordinateWithPrevious {
    private final Coordinate current;
    private Coordinate previous;

    public CoordinateWithPrevious(Coordinate current, Coordinate previous) {
        this.current = current;
        this.previous = previous;
    }

    public CoordinateWithPrevious(Coordinate current) {
        this.current = current;
    }

    public Coordinate getCurrent() {
        return current;
    }

    public Coordinate getPrevious() {
        return previous;
    }

    public void setPrevious(Coordinate previous) {
        this.previous = previous;
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
