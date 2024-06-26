package dt_maze_challenge.xmlHandler;

import dt_maze_challenge.action.Rotate;
import dt_maze_challenge.action.Step;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

public class XmlWriter {
    private XmlWriter() {}
    public static String writeXml(List<Step> steps, List<Rotate> rotates) {
        String result = "";
        try {
            // Step 1: Create a new Document
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Step 2: Create the root element
            Element root = document.createElement("Actions");
            document.appendChild(root);

            steps.forEach( step -> {
                Element st = document.createElement("Step");
                root.appendChild(st);
                Element direction = document.createElement("Direction");
                direction.appendChild(document.createTextNode(String.valueOf(step.direction())));
                st.appendChild(direction);
                Element length = document.createElement("CellNumber");
                length.appendChild(document.createTextNode(String.valueOf(step.length())));
                st.appendChild(length);
            }
            );

            rotates.forEach( rotate -> {
                Element rot = document.createElement("Rotate");
                root.appendChild(rot);
                Element direction = document.createElement("District");
                direction.appendChild(document.createTextNode(String.valueOf(rotate.district())));
                rot.appendChild(direction);
                Element length = document.createElement("Direction");
                length.appendChild(document.createTextNode(String.valueOf(rotate.direction())));
                rot.appendChild(length);
            }
            );

            // Step 5: Transform the Document to a String or File
            // Transform to String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource domSource = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult streamResult = new StreamResult(writer);
            transformer.transform(domSource, streamResult);
            result = writer.getBuffer().toString();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        return result;
    }
}
