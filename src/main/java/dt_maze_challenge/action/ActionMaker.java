package dt_maze_challenge.action;

import dt_maze_challenge.maze.Coordinate;
import dt_maze_challenge.maze.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionMaker {
    private ActionMaker() {}
    public static ActionSet makeActionSet(Maze maze) {
        var stepCoordinates = maze.getSteps();
        if (stepCoordinates.isEmpty()) {
            return new ActionSet(Collections.emptyList(), Collections.emptyList());
        }

        boolean isTrapFounded;
        List<Step> stepList = new ArrayList<>();
        do {
            isTrapFounded = false;
            stepList.add(createStep(maze.getStart(), stepCoordinates.get(0).getCoordinate()));
            if (stepCoordinates.get(0).isTrap()) {
                stepCoordinates.get(0).setTrap(false);
                isTrapFounded = true;
                continue;
            }
            int i;
            for (i = 0; i < stepCoordinates.size() - 1; i++) {
                stepList.add(createStep(stepCoordinates.get(i).getCoordinate(), stepCoordinates.get(i + 1).getCoordinate()));
                if (stepCoordinates.get(i + 1).isTrap()) {
                    stepCoordinates.get(i + 1).setTrap(false);
                    isTrapFounded = true;
                    break;
                }
            }
            if (!isTrapFounded) {
                stepList.add(createStep(stepCoordinates.get(i).getCoordinate(), maze.getEnd()));
            }
        } while (isTrapFounded);




        List<Rotate> rotationList = new ArrayList<>();
        if (maze.getLevel() == 3) {
            // TODO rotationList feltöltés, ehhez kell még extra mező a maze-be
        }
        var actionSet = new ActionSet(aggregateSteps(stepList),rotationList);
        actionSet.setStepCost(stepList.size());
        return actionSet;
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
