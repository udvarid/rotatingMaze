package dt_maze_challenge.solver;

import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.maze.MazeType;

import java.util.Map;
import java.util.Set;

import static dt_maze_challenge.solver.helper.SolverUtil.calculateMinimumSteps;
import static dt_maze_challenge.solver.helper.SolverUtil.fillUpPermutations;
import static dt_maze_challenge.solver.helper.SolverUtil.rotateLeft;
import static dt_maze_challenge.solver.helper.SolverUtil.rotateRight;
import static dt_maze_challenge.util.Util.ROTATION_COST;
import static dt_maze_challenge.util.Util.copyMaze;
import static java.lang.Integer.MAX_VALUE;

class ComplexSolver implements SolverType {
    Map<Integer, Set<String>> permutations;

    public ComplexSolver() {
        permutations = fillUpPermutations();
    }

    SolverType solver = new TrapSolver();

    @Override
    public Maze solve(Maze maze) {
        Maze winnerMaze = maze;
        int minimumSteps = calculateMinimumSteps(maze);
        MazeType[][] protoType = copyMaze(maze.getCoordinates());
        int initResult = solver.solve(winnerMaze).getCost();
        int mazeCost = initResult > 0 ? initResult : MAX_VALUE;
        boolean foundPossibleBest = false;
        for (int i = 1; i <= 18; i++) {
            Set<String> permutationOnThisLevel = permutations.get(i);
            for (String perm : permutationOnThisLevel) {
                if (mazeCost < minimumSteps + i * ROTATION_COST) {
                    foundPossibleBest = true;
                    break;
                }
                Maze rotatedMaze = rotateMaze(perm, protoType, maze);
                if (!rotatedMaze.anyDoorIsBlocked()) {
                    int rotatedResultStepCost = solver.solve(rotatedMaze).getCost();
                    int rotatedResultCost = rotatedResultStepCost + i * ROTATION_COST;
                    if (rotatedResultStepCost > 0 && rotatedResultCost < mazeCost) {
                        mazeCost = rotatedResultCost;
                        winnerMaze = rotatedMaze;
                    }
                }
            }
            if (foundPossibleBest) {
                break;
            }
        }
        return winnerMaze;
    }

    private Maze rotateMaze(String permutation, MazeType[][] protoType, Maze originMaze) {
        MazeType[][] rotatedCoordinates = copyMaze(protoType);
        for (int i = 0; i < permutation.length(); i++) {
            char ch = permutation.charAt(i);
            if (ch != '0') {
                rotateSector(rotatedCoordinates, i, ch);
            }
        }
        Maze rotatedMaze = new Maze(3, rotatedCoordinates, permutation);
        rotatedMaze.setStart(originMaze.getStart());
        rotatedMaze.setEnd(originMaze.getEnd());
        return rotatedMaze;
    }

    private void rotateSector(MazeType[][] cord, int sector, char rotationType) {
        int x = (sector / 3) * 5 + 1;
        int y = (sector % 3) * 5 + 1;
        MazeType[][] tiny = new MazeType[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tiny[i][j] = cord[x + i][y + j];
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
                cord[x + i][y + j] = tiny[i][j];
            }
        }
    }
}