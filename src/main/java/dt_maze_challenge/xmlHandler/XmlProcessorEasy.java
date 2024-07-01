package dt_maze_challenge.xmlHandler;

import dt_maze_challenge.maze.Coordinate;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.maze.MazeType;

/**
 * Reads input xml and creates maze.
 */
public class XmlProcessorEasy {
    private XmlProcessorEasy() {}
    public static Maze processXml(String xmlMessage) {
        String nakedLevel1 = xmlMessage.split("<Maze>")[1].split("<Level>")[1];
        int level = Integer.valueOf(nakedLevel1.substring(0, 1));
        Maze maze = new Maze(level);
        String startPoint = nakedLevel1.substring(9).split("</StartPoint>")[0];
        maze.setStart(getCoordinate(startPoint));
        maze.getCoordinates()[maze.getStart().getX()][maze.getStart().getY()] = MazeType.ENTRY;
        String endPoint = nakedLevel1.split("</StartPoint>")[1].split("</EscapePoint>")[0];
        maze.setEnd(getCoordinate(endPoint));
        maze.getCoordinates()[maze.getEnd().getX()][maze.getEnd().getY()] = MazeType.ESCAPE;
        String remaining = nakedLevel1.split("</StartPoint>")[1].split("</EscapePoint>")[1];
        remaining = remaining
                .replaceAll("<InsideItems>","")
                .replaceAll("</InsideItems>","")
                .replaceAll("</Maze>","")
                .replaceAll("<Row>","")
                .replaceAll("</Row>",",")
                .replaceAll("<Column>","")
                .replaceAll("</Wall>","")
                .replaceAll("</Trap>","")
                .replaceAll("</Column>","");
        String[] splitted = remaining.split("<");
        for (String sp : splitted) {
            if (sp.length() > 5) {
                String[] numbs = sp.substring(5).split(",");
                int row = Integer.parseInt(numbs[0]) - 1;
                int column = Integer.parseInt(numbs[1]) - 1;
                if (sp.startsWith("Wall")) {
                    maze.getCoordinates()[row][column] = MazeType.WALL;
                } else if (sp.startsWith("Trap")) {
                    maze.getCoordinates()[row][column] = MazeType.TRAP;
                }
            }
        }
        return maze;
    }

    private static Coordinate getCoordinate(String s) {
        String[] splitted = s.split("Row><Column");
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (char c : splitted[0].toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
                found = true;
            } else if (found) {
                break;
            }
        }
        int row = Integer.parseInt(sb.toString());
        sb = new StringBuilder();
        found = false;
        for (char c : splitted[1].toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
                found = true;
            } else if (found) {
                break;
            }
        }
        int column = Integer.parseInt(sb.toString());
        return new Coordinate(row - 1, column - 1);
    }


}
