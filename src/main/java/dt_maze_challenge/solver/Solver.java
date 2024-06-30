package dt_maze_challenge.solver;

import dt_maze_challenge.action.ActionMaker;
import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.maze.Maze;

import static java.util.Collections.emptyList;

public class Solver {
    public ActionSet solve(Maze maze) {
        ActionSet result = new ActionSet(emptyList(), emptyList());
        Maze mazeResult = null;
        int level = maze.getLevel();
        if (level == 1) {
            mazeResult = (new SimpleSolver()).solve(maze);
        } else if (level == 2) {
            mazeResult = (new TrapSolver()).solve(maze);
        } else if (level == 3) {
            mazeResult = (new ComplexSolver()).solve(maze);
        }
        if (mazeResult != null) {
            result = ActionMaker.makeActionSet(mazeResult);
        }
        return result;
    }
}




