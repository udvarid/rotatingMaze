package dt_maze_challenge.maze;

public class CoordinateWithTrap {
    private final Coordinate coordinate;
    private boolean trap = false;

    public CoordinateWithTrap(Coordinate coordinate, boolean trap) {
        this.coordinate = coordinate;
        this.trap = trap;
    }

    public CoordinateWithTrap(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isTrap() {
        return trap;
    }

    public void setTrap(boolean trap) {
        this.trap = trap;
    }
}

