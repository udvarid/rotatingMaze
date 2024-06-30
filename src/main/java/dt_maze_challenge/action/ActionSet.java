package dt_maze_challenge.action;

import java.util.List;

public class ActionSet {
    private final List<Step> steps;
    private final List<Rotate> rotates;

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

    public boolean isEmpty() {
        return this.steps.isEmpty() && this.rotates.isEmpty();
    }
}
