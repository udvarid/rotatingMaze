package dt_maze_challenge.util;

import dt_maze_challenge.maze.MazeType;

import java.util.Arrays;

public class Util {
    private Util(){}

    public static final int ROTATION_COST = 5;
    public static final int MAZE_SIZE = 17;
    public static final boolean IS_COMPLEX = false;

    public static MazeType[][] copyMaze(MazeType[][] coordinates) {
        MazeType[][] protoType = new MazeType[MAZE_SIZE][MAZE_SIZE];
        for (int i = 0; i < MAZE_SIZE; i++) {
            protoType[i] = Arrays.copyOf(coordinates[i], MAZE_SIZE);
        }
        return protoType;
    }
}
