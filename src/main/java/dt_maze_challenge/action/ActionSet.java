package dt_maze_challenge.action;

import java.util.List;

public class ActionSet {
    private List<Step> steps;
    private List<Rotate> rotates;
    private int stepCost;

    public ActionSet(List<Step> steps, List<Rotate> rotates) {
        this.steps = steps;
        this.rotates = rotates;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public List<Rotate> getRotates() {
        return rotates;
    }

    public int getStepCost() {
        return stepCost;
    }

    public void setStepCost(int stepCost) {
        this.stepCost = stepCost;
    }

    public boolean isEmpty() {
        return this.steps.isEmpty() && this.rotates.isEmpty();
    }
}
