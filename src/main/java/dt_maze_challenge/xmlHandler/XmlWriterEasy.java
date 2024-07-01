package dt_maze_challenge.xmlHandler;

import dt_maze_challenge.action.ActionSet;
import dt_maze_challenge.action.Rotate;
import dt_maze_challenge.action.Step;

/**
 * Creates output message based on Actionset
 */
public class XmlWriterEasy {
    private XmlWriterEasy() {}
    private static final String ACTION_START = "<Actions>";
    private static final String ACTION_END = "</Actions>";

    private static final String STEP_START = "    <Step>";
    private static final String STEP_END = "    </Step>";

    private static final String DIRECTION = "        <Direction>%d</Direction>";
    private static final String CELL_NUMBER = "        <CellNumber>%d</CellNumber>";
    private static final String DISTRICT = "        <District>%d</District>";

    private static final String ROTATE_START = "    <Rotate>";
    private static final String ROTATE_END = "    </Rotate>";

    private static final String END_ROW = "\n";

    public static String writeXml(ActionSet actionSet) {

        StringBuilder sb = new StringBuilder();
        if (actionSet.isEmpty()) {
            return sb.toString();
        }

        sb.append(ACTION_START).append(END_ROW);
        for (Rotate rotate : actionSet.getRotates()) {
            sb.append(ROTATE_START).append(END_ROW);
            sb.append(String.format(DISTRICT, rotate.getDistrict())).append(END_ROW);
            sb.append(String.format(DIRECTION, rotate.getDirection())).append(END_ROW);
            sb.append(ROTATE_END).append(END_ROW);
        }
        for (Step step : actionSet.getSteps()) {
            sb.append(STEP_START).append(END_ROW);
            sb.append(String.format(DIRECTION, step.getDirection())).append(END_ROW);
            sb.append(String.format(CELL_NUMBER, step.getLength())).append(END_ROW);
            sb.append(STEP_END).append(END_ROW);
        }
        sb.append(ACTION_END).append(END_ROW);

        return sb.toString();
    }
}
