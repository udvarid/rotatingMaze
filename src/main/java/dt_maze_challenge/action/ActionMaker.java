package dt_maze_challenge.action;

import dt_maze_challenge.maze.Coordinate;
import dt_maze_challenge.maze.Maze;

import java.util.ArrayList;
import java.util.List;

public class ActionMaker {
    private ActionMaker() {}
    public static ActionSet makeActionSet(Maze maze) {
        List<Step> stepList = new ArrayList<>();
        var stepCoordinates = maze.getSteps();
        stepList.add(createStep(maze.getStart(), stepCoordinates.get(0)));

        int i = 0;
        for (i = 0; i < stepCoordinates.size() - 1; i++) {
            stepList.add(createStep(stepCoordinates.get(i), stepCoordinates.get(i + 1)));
        }
        stepList.add(createStep(stepCoordinates.get(i), maze.getEnd()));

        List<Rotate> rotationList = new ArrayList<>();
        if (maze.getLevel() == 3) {
            // TODO rotationList feltöltés, ehhez kell még extra mező a maze-be
        }
        return new ActionSet(aggregateSteps(stepList),rotationList);
    }

    private static List<Step> aggregateSteps(List<Step> steps) {
        List<Step> aggregatedSteps = new ArrayList<>();
        int direction = steps.get(0).direction();
        int length = 0;
        for (Step step : steps) {
            if (step.direction() != direction) {
                aggregatedSteps.add(new Step(direction, length));
                direction = step.direction();
                length = 1;
            } else {
                length++;
            }
        }
        aggregatedSteps.add(new Step(direction, length));
        return aggregatedSteps;
    }

    private static Step createStep(Coordinate coorFist, Coordinate coorSecond) {
        int direction = 0;
        if (coorFist.y() > coorSecond.y()) {
            direction = 1;
        } else if (coorFist.y() < coorSecond.y()) {
            direction = 2;
        } else if (coorFist.x() > coorSecond.x()) {
            direction = 3;
        } else if (coorFist.x() < coorSecond.x()) {
            direction = 4;
        }
        return new Step(direction,1);
    }
}
