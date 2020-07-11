package io;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import util.LP;
import util.IntTable;

public class XMLDataFileReader {

    File file;

    public XMLDataFileReader(String fileName) {
        file = new File(fileName);
    }

    public IntTable readDataFile() throws IOException {
        IntTable table = new IntTable();
        if (!file.exists()) {
            return table;
        }
        Document doc = getDocument();
        NodeList sets = doc.getDocumentElement().getChildNodes();

        for (int i = 1; i < sets.getLength(); i += 2) {
            Node node = sets.item(i);
            int setID = getNodeId(node);
            NodeList members = node.getChildNodes();
            for (int n = 1; n < members.getLength(); n += 2) {
                Node prot = members.item(n);
                int protID = getNodeId(prot);
                NodeList configs = prot.getChildNodes();
                for (int c = 1; c < configs.getLength(); c += 2) {
                    Node conf = configs.item(c);
                    String name = conf.getNodeName(),
                            txt = conf.getTextContent();
                    if (name.equals("Reacher")) {
                        table.add(setID, protID, Integer.parseInt(txt), -1);
                    } else if (name.equals("_Solver")) {
                        table.add(setID, protID, -1, Integer.parseInt(txt));
                    }
                }
            }
        }
        return table;
    }

    public void tryFile() throws IOException {
        Document doc = getDocument();
        String str, nl = System.lineSeparator();
        Element element = doc.getDocumentElement();
        str = doc.getXmlVersion() + " encoding:" + doc.getXmlEncoding() + nl;
        str += element.getNodeName() + " id=" + element.getAttribute("id").toString() + nl;
        str += printNodeList(element.getChildNodes());
        LP.println(str);
    }

    private int getNodeId(Node node) {
        return Integer.parseInt(((Element) node).getAttribute("id").toString());
    }

    private String printNodeList(NodeList nlist) {
        String str = "", nl = LP.newln;

        for (int i = 1; i < nlist.getLength(); i += 2) {
            Node tmp = nlist.item(i);
            str += tmp.getNodeName();
            if ((tmp.getNodeName() == "Reacher") || (tmp.getNodeName() == "_Solver")) {
                str += " " + tmp.getTextContent() + nl;
            } else {
                str += " id=" + ((Element) tmp).getAttribute("id").toString() + nl;
            }
            NodeList re = tmp.getChildNodes();
            str += printNodeList(re);
        }
        return str;
    }

    private Document getDocument() throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            if (db == null) {
                return null;
            }
            Document doc = db.parse(file);
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

}
