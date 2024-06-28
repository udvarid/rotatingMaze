package dt_maze_challenge.maze;

import java.util.Objects;

public record CoordinateWithPrevious(Coordinate current, Coordinate previous) {

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
