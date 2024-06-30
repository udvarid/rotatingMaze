package dt_maze_challenge.solver;

import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.maze.Maze;

import static java.util.Collections.emptyList;

public class Solver {
    public ActionSet solve(Maze maze) {
        ActionSet result = new ActionSet(emptyList(), emptyList());
        int level = maze.getLevel();
        if (level == 1) {
            result = (new SimpleSolver()).solve(maze, false);
        } else if (level == 2) {
            result = (new TrapSolver()).solve(maze, false);
        } else if (level == 3) {
            result = (new ComplexSolver()).solve(maze, false);
        }
        return result;
    }
}




