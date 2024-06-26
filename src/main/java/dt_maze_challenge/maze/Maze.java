package dt_maze_challenge.maze;

public class Maze {
    private final MazeType[][] coordinates;
    private final int level;
    private static final int SIZE = 17;

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
}
