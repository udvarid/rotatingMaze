package dt_maze_challenge.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Coordinate> getNeighbours(int max) {
        List<Coordinate> result = new ArrayList<>();
        if (x > 0) {
            result.add(new Coordinate(this.x - 1, this.y));
        }
        if (y > 0) {
            result.add(new Coordinate(this.x, this.y - 1));
        }
        if (x < max) {
            result.add(new Coordinate(this.x + 1, this.y));
        }
        if (y < max) {
            result.add(new Coordinate(this.x, this.y + 1));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
