package dt_maze_challenge.solver;

import dt_maze_challenge.action.ActionMaker;
import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.maze.Maze;

public class Solver implements SolverType{
    @Override
    public ActionSet solve(Maze maze) {
        return switch (maze.getLevel()) {
            case 1 -> (new SimpleSolver()).solve(maze);
            case 2 -> (new TrapSolver()).solve(maze);
            case 3 -> (new ComplexSolver()).solve(maze);
            default -> ActionMaker.makeActionSet(maze);
        };
    }
}




