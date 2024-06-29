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
    private final List<MazeType> walkableTypes = Arrays.asList(MazeType.EMPTY, MazeType.ESCAPE, MazeType.TRAP);
    private List<CoordinateWithTrap> steps = new ArrayList<>();

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

    public List<CoordinateWithTrap> getSteps() {
        return steps;
    }

    public void setSteps(List<CoordinateWithTrap> steps) {
        this.steps = steps;
    }

    public void showMaze() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String ord = String.valueOf(this.coordinates[i][j].ordinal());
                if (this.coordinates[i][j] == MazeType.WALL) {
                    ord = "|";
                }
                if (this.coordinates[i][j] == MazeType.EMPTY) {
                    ord = " ";
                }
                if (this.coordinates[i][j] == MazeType.STEP) {
                    ord = ".";
                }
                if (this.coordinates[i][j] == MazeType.STEP_ON_TRAP) {
                    ord = "*";
                }
                if (this.coordinates[i][j] == MazeType.ENTRY) {
                    ord = "S";
                }
                if (this.coordinates[i][j] == MazeType.ESCAPE) {
                    ord = "E";
                }
                System.out.print(ord + " ");
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
        List<Coordinate> possibleCoordinatesForStart = this.start.getNeighbours(SIZE - 1);
        List<Coordinate> possibleCoordinatesForEnd = this.end.getNeighbours(SIZE - 1);
        boolean startBlocked = possibleCoordinatesForStart.stream().noneMatch( coord ->
            walkableTypes.contains(this.coordinates[coord.x()][coord.y()])
        );
        boolean endBlocked = possibleCoordinatesForEnd.stream().noneMatch( coord ->
                walkableTypes.contains(this.coordinates[coord.x()][coord.y()])
        );
        return startBlocked || endBlocked;
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
