package dt_maze_challenge.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze {
    private final MazeType[][] coordinates;
    private final int level;
    private static final int SIZE = 17;
    private Coordinate start;
    private Coordinate end;
    private List<MazeType> walkableTypes = Arrays.asList(MazeType.EMPTY, MazeType.ESCAPE, MazeType.TRAP);

    public Maze(int level) {
        this.coordinates = new MazeType[SIZE][SIZE];
        this.level = level;
        fillUpEmptyMaze();
    }

    private void fillUpEmptyMaze() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                coordinates[i][j] = onTheEdge(i,j) ? MazeType.WALL : MazeType.EMPTY;
            }
        }
    }

    private boolean onTheEdge(int i, int j) {
        return i == 0 || j == 0 || i == SIZE - 1 || j == SIZE - 1;
    }

    public MazeType[][] getCoordinates() {
        return coordinates;
    }

    public int getLevel() {
        return level;
    }

    public void showMaze() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(this.coordinates[i][j].ordinal());
            }
            System.out.println();
        }
    }

    public List<Coordinate> getWalkableCoordinates(Coordinate coordinate) {
        List<Coordinate> result = new ArrayList<>();
        List<Coordinate> possibleCoordinates = coordinate.getNeighbours(SIZE - 1);
        possibleCoordinates.forEach(coord -> {
            if (walkableTypes.contains(this.coordinates[coord.x()][coord.y()])) {
                result.add(coord);
            }
        });
        return result;
    }

    public MazeType getType(Coordinate coordinate) {
        return this.coordinates[coordinate.x()][coordinate.y()];
    }

    public boolean anyDoorIsBlocked() {
        // TODO implementálni, a 3-as típusú forgatáskor hasznos lesz
        return false;
    }

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }
}
