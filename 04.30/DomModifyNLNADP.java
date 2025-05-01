package dom;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModifyNLNADP {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("xmlnlnadp.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = factory.newDocumentBuilder();
            
            Document doc = dbuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            // Modify 'vendeg' elements
            NodeList vendegList = doc.getElementsByTagName("vendeg");
            for (int i = 0; i < vendegList.getLength(); i++) {
                Node vendeg = vendegList.item(i);
                NodeList children = vendeg.getChildNodes();
                
                for (int temp = 0; temp < children.getLength(); temp++) {
                    Node node = children.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String tagName = element.getTagName();
                        
                        if ("eletkor".equals(tagName) && "26".equals(element.getTextContent())) {
                            element.setTextContent("22");
                        }
                        if ("nev".equals(tagName) && "Kiss Hajnalka".equals(element.getTextContent())) {
                            element.setTextContent("Kiss Piroska");
                        }
                    }
                }
            }
            
            // Modify gyakornok attribute
            Node gyakornok = doc.getElementsByTagName("gyakornok").item(2);
            NamedNodeMap attr = gyakornok.getAttributes();
            Node nodeAttr = attr.getNamedItem("e_gy");
            nodeAttr.setNodeValue("e3");  // Correct method for attributes
            
            // Remove rendeles elements
            Node vendeglatas = doc.getDocumentElement();
            NodeList childNodes = vendeglatas.getChildNodes();
            
            // Create a list of nodes to remove first
            java.util.List<Node> toRemove = new java.util.ArrayList<>();
            for (int count = 0; count < childNodes.getLength(); count++) {
                Node node = childNodes.item(count);
                if ("rendeles".equals(node.getNodeName())) {
                    toRemove.add(node);
                }
            }
            // Actually remove the nodes
            for (Node node : toRemove) {
                vendeglatas.removeChild(node);
            }
            
            // Save changes
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            // Output to console
            System.out.println("-----------Modified File-----------");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
            
            // Write back to file
            StreamResult fileResult = new StreamResult(xmlFile);
            transformer.transform(source, fileResult);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
