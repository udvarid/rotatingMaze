package dt_maze_challenge.action;

import dt_maze_challenge.maze.Maze;

import java.util.Collections;

public class ActionMaker {
    private ActionMaker() {}
    public static ActionSet makeActionSet(Maze maze) {
        return new ActionSet(Collections.emptyList(), Collections.emptyList());
    }
}
