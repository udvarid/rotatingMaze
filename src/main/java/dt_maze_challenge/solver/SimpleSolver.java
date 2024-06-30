package dt_maze_challenge.solver;

import dt_maze_challenge.action.ActionMaker;
import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.maze.Coordinate;
import dt_maze_challenge.maze.CoordinateWithPrevious;
import dt_maze_challenge.maze.CoordinateWithTrap;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.maze.MazeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class SimpleSolver implements SolverType {
    @Override
    public ActionSet solve(Maze maze) {
        Map<Coordinate, Coordinate> visited = new HashMap<>();
        Queue<CoordinateWithPrevious> actual = new LinkedList<>();

        Coordinate start = maze.getStart();
        maze.getWalkableCoordinates(start).forEach(c -> actual.offer(new CoordinateWithPrevious(c, start)));
        boolean endFound = false;
        while (!actual.isEmpty() && !endFound) {
            CoordinateWithPrevious current = actual.poll();
            visited.put(current.getCurrent(), current.getPrevious());
            List<Coordinate> walkableCells = maze.getWalkableCoordinates(current.getCurrent());
            for (Coordinate c : walkableCells) {
                CoordinateWithPrevious cp = new CoordinateWithPrevious(c, current.getCurrent());
                if (maze.getType(c) == MazeType.ESCAPE) {
                    endFound = true;
                    visited.put(cp.getCurrent(), cp.getPrevious());
                } else if (!visited.containsKey(cp.getCurrent()) && !actual.contains(cp)) {
                    actual.offer(cp);
                }
            }
        }

        if (endFound) {
            Coordinate coordinate = maze.getEnd();
            List<CoordinateWithTrap> steps = new ArrayList<>();
            boolean isStartReached = false;
            while (!isStartReached) {
                coordinate = visited.get(coordinate);
                if (maze.getType(coordinate) == MazeType.ENTRY) {
                    isStartReached = true;
                } else {
                    steps.add(new CoordinateWithTrap(coordinate));
                    maze.getCoordinates()[coordinate.getX()][coordinate.getY()] = MazeType.STEP;
                }
            }
            Collections.reverse(steps);
            maze.setSteps(steps);
        }
        return ActionMaker.makeActionSet(maze);
    }
}