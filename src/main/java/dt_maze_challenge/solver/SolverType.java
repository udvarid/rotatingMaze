package dt_maze_challenge.solver;

import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.maze.Maze;

interface SolverType {
    ActionSet solve(Maze maze, boolean withOnlyCost);
}
