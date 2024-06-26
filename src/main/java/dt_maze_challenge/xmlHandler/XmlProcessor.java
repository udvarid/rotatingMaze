package dt_maze_challenge.xmlHandler;

import dt_maze_challenge.maze.Coordinate;
import dt_maze_challenge.maze.Maze;
import dt_maze_challenge.maze.MazeType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlProcessor {
    private XmlProcessor() {}
    public static Maze processXml(String xmlMessage) {
        Maze maze = new Maze(1);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlMessage)));
            document.getDocumentElement().normalize();
            maze = processDocument(document);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return maze;
    }

    private static Maze processDocument(Document document) {
        NodeList nodeList = document.getElementsByTagName("Level");
        Element element = (Element) nodeList.item(0);
        Maze maze = new Maze(Integer.parseInt(element.getTextContent()));

        getCoordinates(document.getElementsByTagName("StartPoint"))
                .forEach( x -> maze.getCoordinates()[x.x() - 1][x.y() - 1] = MazeType.ENTRY);
        getCoordinates(document.getElementsByTagName("EscapePoint"))
                .forEach( x -> maze.getCoordinates()[x.x() - 1][x.y() - 1] = MazeType.ESCAPE);

        getCoordinates(document.getElementsByTagName("Wall"))
                .forEach( x -> maze.getCoordinates()[x.x() - 1][x.y() - 1] = MazeType.WALL);
        getCoordinates(document.getElementsByTagName("Trap"))
                .forEach( x -> maze.getCoordinates()[x.x() - 1][x.y() - 1] = MazeType.TRAP);

        return maze;
    }

    private static List<Coordinate> getCoordinates(NodeList nodeList) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            NodeList nodeListRow = element.getElementsByTagName("Row");
            Element elementRow = (Element) nodeListRow.item(0);
            NodeList nodeListColumn = element.getElementsByTagName("Column");
            Element elementColumn = (Element) nodeListColumn.item(0);
            Coordinate coordinate = new Coordinate(Integer.parseInt(elementRow.getTextContent()), Integer.parseInt(elementColumn.getTextContent()));
            coordinates.add(coordinate);
        }
        return coordinates;

    }
}
