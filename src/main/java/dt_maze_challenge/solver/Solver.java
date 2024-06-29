package dt_maze_challenge.solver;

import dt_maze_challenge.action.ActionMaker;
import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.maze.Coordinate;
import dt_maze_challenge.maze.CoordinateWithPrevious;
import dt_maze_challenge.maze.CoordinateWithTrap;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.maze.MazeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

interface SolverType {
    ActionSet solve(Maze maze);
}

public class Solver {
    public ActionSet solve(Maze maze) {
        return switch (maze.getLevel()) {
            case 1 -> (new SimpleSolver()).solve(maze);
            case 2 -> (new TrapSolver()).solve(maze);
            case 3 -> (new ComplexSolver()).solve(maze);
            default -> ActionMaker.makeActionSet(maze);
        };
    }
}

class SimpleSolver implements SolverType {
    @Override
    public ActionSet solve(Maze maze) {
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
        return ActionMaker.makeActionSet(maze);
    }
}

class TrapSolver implements SolverType {
    @Override
    public ActionSet solve(Maze maze) {
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
        return ActionMaker.makeActionSet(maze);
    }
}

class ComplexSolver implements SolverType {
    Map<Integer, Set<String>> permutations = new HashMap<>();
    SolverType solver = new TrapSolver();
    @Override
    public ActionSet solve(Maze maze) {
        fillUpPermutations();
        Maze winnerMaze = maze;
        int minimumSteps = calculateMinimumSteps(maze);
        MazeType[][] protoType = getMazeCopy(maze.getCoordinates());
        var initResult = solver.solve(winnerMaze);
        int mazeCost = initResult.getStepCost() > 0 ? initResult.getStepCost() : Integer.MAX_VALUE;
        boolean foundPossibleBest = false;
        for (int i = 1; i <= 18; i++) {
            var permutationOnThisLevel = permutations.get(i);
            for (String perm : permutationOnThisLevel) {
                if (mazeCost < minimumSteps + i * ActionMaker.ROTATION_COST) {
                    foundPossibleBest = true;
                    break;
                }
                Maze rotatedMaze = rotateMaze(perm, protoType);
                rotatedMaze.setStart(maze.getStart());
                rotatedMaze.setEnd(maze.getEnd());
                if (rotatedMaze.anyDoorIsBlocked()) {
                    continue;
                }
                var rotatedResultStepCost = solver.solve(rotatedMaze).getStepCost();
                var rotatedResultCost = rotatedResultStepCost + i * ActionMaker.ROTATION_COST;
                if (rotatedResultStepCost > 0 && rotatedResultCost < mazeCost) {
                    mazeCost = rotatedResultCost;
                    winnerMaze = rotatedMaze;
                }
            }
            if (foundPossibleBest) {
                break;
            }
        }
        return ActionMaker.makeActionSet(winnerMaze);
    }

    private Maze rotateMaze(String permutation, MazeType[][] protoType) {
        MazeType[][] coordinatesToRotate = getMazeCopy(protoType);
        for (int i = 0; i < permutation.length(); i++) {
            char ch = permutation.charAt(i);
            if (ch != '0') {
                rotateSector(coordinatesToRotate, i, ch);
            }
        }
        return new Maze(3, coordinatesToRotate, permutation);
    }

    private void rotateSector(MazeType[][] coord, int sector, char rotationType) {
        int x = sector / 3;
        int y = sector % 3;
        Coordinate lu = new Coordinate(x * 5 + 1,y * 5 + 1);
        MazeType[][] tiny = new MazeType[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tiny[i][j] = coord[lu.x() + i][lu.y()+j];
            }
        }
        if (rotationType == '1') {
            tiny = rotateLeft(tiny);
        } else if (rotationType == '2') {
            tiny = rotateRight(tiny);
        } else if (rotationType == '3') {
            tiny = rotateLeft(tiny);
            tiny = rotateLeft(tiny);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                coord[lu.x() + i][lu.y()+j] = tiny[i][j];
            }
        }
    }

    private MazeType[][] rotateLeft(MazeType[][] tiny) {
        MazeType[][] puffer = new MazeType[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                puffer[4 - j][i] = tiny[i][j];
            }
        }
        return puffer;
    }

    private MazeType[][] rotateRight(MazeType[][] tiny) {
        MazeType[][] puffer = new MazeType[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                puffer[j][4 - i] = tiny[i][j];
            }
        }
        return puffer;
    }

    private int calculateMinimumSteps(Maze maze) {
        var start = maze.getStart();
        var end = maze.getEnd();
        return Math.abs(start.x()- end.x()) + Math.abs(start.y() - end.y());
    }

    private void fillUpPermutations() {
        for (int i1 = 0; i1 <= 3; i1++) {
            for (int i2 = 0; i2 <= 3; i2++) {
                for (int i3 = 0; i3 <= 3; i3++) {
                    for (int i4 = 0; i4 <= 3; i4++) {
                        for (int i5 = 0; i5 <= 3; i5++) {
                            for (int i6 = 0; i6 <= 3; i6++) {
                                for (int i7 = 0; i7 <= 3; i7++) {
                                    for (int i8 = 0; i8 <= 3; i8++) {
                                        for (int i9 = 0; i9 <= 3; i9++) {
                                            var swv = getString(Arrays.asList(i1, i2, i3, i4, i5, i6, i7, i8, i9));
                                            if (permutations.containsKey(swv.value)) {
                                                permutations.get(swv.value).add(swv.combination);
                                            } else {
                                                var set = new HashSet<String>();
                                                set.add(swv.combination);
                                                permutations.put(swv.value, set);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private MazeType[][] getMazeCopy(MazeType[][] coordinates) {
        MazeType[][] protoType = new MazeType[Maze.SIZE][Maze.SIZE];
        for (int i = 0; i < Maze.SIZE; i++) {
            for (int j = 0; j < Maze.SIZE; j++) {
                protoType[i][j] = coordinates[i][j];
            }
        }
        return protoType;
    }

    private StringWithValue getString(List<Integer> numbers) {
        StringBuilder sb = new StringBuilder();
        int value = 0;
        for (Integer number : numbers) {
            sb.append(number);
            value += calculateValue(number);
        }
        return new StringWithValue(sb.toString(), value);
    }

    private int calculateValue(int number) {
        return switch (number) {
            case 1, 2 -> 1;
            case 3 -> 2;
            default -> 0;
        };
    }

    record StringWithValue(String combination, int value) {}
}