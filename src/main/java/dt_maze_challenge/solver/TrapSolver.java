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

class TrapSolver implements SolverType {
    @Override
    public Maze solve(Maze maze) {
        Map<Coordinate, CoordinateWithPrevious> visited = new HashMap<>();
        Queue<CoordinateWithPrevious> actual = new LinkedList<>();

        Coordinate start = maze.getStart();
        maze.getWalkableCoordinates(start).forEach(c -> actual.offer(new CoordinateWithPrevious(c, start, 1,1)));
        boolean endFound = false;
        while (!actual.isEmpty() && !endFound) {
            CoordinateWithPrevious current = actual.poll();
            current.increaseCost();
            if (current.notWaiting()) {
                current.increaseLength();
                visited.put(current.getCurrent(), current);
                List<Coordinate> walkableCells = maze.getWalkableCoordinates(current.getCurrent());
                for (Coordinate c : walkableCells) {
                    MazeType typeOfCell = maze.getType(c);
                    CoordinateWithPrevious cp = new CoordinateWithPrevious(c, current.getCurrent(), current.getLength(), current.getCost());
                    if (typeOfCell == MazeType.ESCAPE) {
                        endFound = true;
                        visited.put(cp.getCurrent(), cp);
                    } else if (!visited.containsKey(cp.getCurrent()) && !actual.contains(cp)) {
                        if (typeOfCell == MazeType.TRAP) {
                            cp.forceToWait();
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
            int mazeCost = visited.get(coordinate).getCost();
            List<CoordinateWithTrap> steps = new ArrayList<>();
            boolean startFound = false;
            while (!startFound) {
                coordinate = visited.get(coordinate).getPrevious();
                MazeType typeOfCell = maze.getType(coordinate);
                if (typeOfCell == MazeType.ENTRY) {
                    startFound = true;
                } else {
                    if (typeOfCell == MazeType.EMPTY) {
                        steps.add(new CoordinateWithTrap(coordinate));
                        maze.getCoordinates()[coordinate.getX()][coordinate.getY()] = MazeType.STEP;
                    } else {
                        steps.add(new CoordinateWithTrap(coordinate, true));
                        maze.getCoordinates()[coordinate.getX()][coordinate.getY()] = MazeType.STEP_ON_TRAP;
                    }
                }
            }
            Collections.reverse(steps);
            maze.setSteps(steps);
            maze.setCost(mazeCost);
        }
        return maze;
    }
}
