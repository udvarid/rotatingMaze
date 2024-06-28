package dt_maze_challenge.solver;

import dt_maze_challenge.maze.Coordinate;
import dt_maze_challenge.maze.CoordinateWithPrevious;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.maze.MazeType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Solver {
    private final SolverTypeOne solverTypeOne = new SolverTypeOne();
    public void solve(Maze maze) {
        switch (maze.getLevel()) {
            case 1 -> solverTypeOne.solve(maze);
            case 2 -> solverTypeOne.solve(maze);
            case 3 -> solverTypeOne.solve(maze);
            default -> System.out.println("Unhandled level");
        }

    }
}

class SolverTypeOne {
    public void solve(Maze maze) {
        Map<Coordinate, Coordinate> visited = new HashMap<>();
        Queue<CoordinateWithPrevious> actual = new LinkedList<>();

        var start = maze.getStart();
        maze.getWalkableCoordinates(start).forEach(c -> actual.offer(new CoordinateWithPrevious(c, start)));
        boolean endFound = false;
        while (!actual.isEmpty() && !endFound) {
            var current = actual.poll();
            visited.put(current.current(), current.previous());
            var walkableCells = maze.getWalkableCoordinates(current.current());
            for (Coordinate c : walkableCells) {
                var cp = new CoordinateWithPrevious(c, current.current());
                if (maze.getType(c) == MazeType.ESCAPE) {
                    endFound = true;
                    visited.put(cp.current(), cp.previous());
                } else if (!visited.containsKey(cp.current()) && !actual.contains(cp)) {
                    actual.offer(cp);
                }
            }
        }

        if (endFound) {
            Coordinate coordinate = maze.getEnd();
            boolean startFound = false;
            while (!startFound) {
                coordinate = visited.get(coordinate);
                if (maze.getType(coordinate) == MazeType.ENTRY) {
                    startFound = true;
                } else {
                    maze.getCoordinates()[coordinate.x()][coordinate.y()] = MazeType.STEP;
                }
            }
        }
    }
}
