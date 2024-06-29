package dt_maze_challenge.solver;

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

interface SolverType {
    void solve(Maze maze);
}

public class Solver {
    public void solve(Maze maze) {
        switch (maze.getLevel()) {
            case 1 -> (new SolverTypeOne()).solve(maze);
            case 2 -> (new SolverTypeTwo()).solve(maze);
            case 3 -> (new SolverTypeTwo()).solve(maze);
            default -> System.out.println("Unhandled level");
        }
    }
}

class SolverTypeOne implements SolverType {
    @Override
    public void solve(Maze maze) {
        Map<Coordinate, Coordinate> visited = new HashMap<>();
        Queue<CoordinateWithPrevious> actual = new LinkedList<>();

        var start = maze.getStart();
        maze.getWalkableCoordinates(start).forEach(c -> actual.offer(new CoordinateWithPrevious(c, start)));
        boolean endFound = false;
        while (!actual.isEmpty() && !endFound) {
            var current = actual.poll();
            visited.put(current.getCurrent(), current.getPrevious());
            var walkableCells = maze.getWalkableCoordinates(current.getCurrent());
            for (Coordinate c : walkableCells) {
                var cp = new CoordinateWithPrevious(c, current.getCurrent());
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
            boolean startFound = false;
            while (!startFound) {
                coordinate = visited.get(coordinate);
                if (maze.getType(coordinate) == MazeType.ENTRY) {
                    startFound = true;
                } else {
                    steps.add(new CoordinateWithTrap(coordinate));
                    maze.getCoordinates()[coordinate.x()][coordinate.y()] = MazeType.STEP;
                }
            }
            Collections.reverse(steps);
            maze.setSteps(steps);
        }
    }
}

class SolverTypeTwo implements SolverType {
    @Override
    public void solve(Maze maze) {
        Map<Coordinate, Coordinate> visited = new HashMap<>();
        Queue<CoordinateWithPrevious> actual = new LinkedList<>();

        var start = maze.getStart();
        maze.getWalkableCoordinates(start).forEach(c -> actual.offer(new CoordinateWithPrevious(c, start)));
        boolean endFound = false;
        while (!actual.isEmpty() && !endFound) {
            var current = actual.poll();
            if (current.readyToGo()) {
                current.increaseLength();
                visited.put(current.getCurrent(), current.getPrevious());
                var walkableCells = maze.getWalkableCoordinates(current.getCurrent());
                for (Coordinate c : walkableCells) {
                    var typeOfCell = maze.getType(c);
                    var cp = new CoordinateWithPrevious(c, current.getCurrent(), current.getLength());
                    if (typeOfCell == MazeType.ESCAPE) {
                        endFound = true;
                        visited.put(cp.getCurrent(), cp.getPrevious());
                    } else if (!visited.containsKey(cp.getCurrent()) && !actual.contains(cp)) {
                        if (typeOfCell == MazeType.TRAP) {
                            cp.putToWait();
                        }
                        actual.offer(cp);
                    }
                }
            } else {
                current.decreaseWait();
                actual.offer(current);
            }
        }

        if (endFound) {
            Coordinate coordinate = maze.getEnd();
            List<CoordinateWithTrap> steps = new ArrayList<>();
            boolean startFound = false;
            while (!startFound) {
                coordinate = visited.get(coordinate);
                if (maze.getType(coordinate) == MazeType.ENTRY) {
                    startFound = true;
                } else {
                    if (maze.getType(coordinate) == MazeType.EMPTY) {
                        steps.add(new CoordinateWithTrap(coordinate));
                        maze.getCoordinates()[coordinate.x()][coordinate.y()] = MazeType.STEP;
                    } else {
                        steps.add(new CoordinateWithTrap(coordinate, true));
                        maze.getCoordinates()[coordinate.x()][coordinate.y()] = MazeType.STEP_ON_TRAP;
                    }
                }
            }
            Collections.reverse(steps);
            maze.setSteps(steps);
        }
    }
}