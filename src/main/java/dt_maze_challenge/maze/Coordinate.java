package dt_maze_challenge.maze;

import java.util.ArrayList;
import java.util.List;

public record Coordinate(int x, int y) {
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
}
