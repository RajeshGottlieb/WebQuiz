package com.webquiz;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class XmlParser {

    public XmlParser(String filename) {
        try {
            Document doc = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .parse(new File(filename));

            System.out.println("QUIZ-DB:");

            NodeList nl = doc.getElementsByTagName("user");

            for (int i = 0; i < nl.getLength(); i++)
                parseUser(nl.item(i));

            nl = doc.getElementsByTagName("subject");

            for (int i = 0; i < nl.getLength(); i++)
                parseSubject(nl.item(i));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void parseUser(Node node) {
        String username = getAttribute(node, "username");
        String password = getAttribute(node, "password");

        System.out.println("USER: username=\"" + username + "\" password=\"" + password + "\"");
    }

    void parseSubject(Node node) {
        String name = getAttribute(node, "name");
        System.out.println("SUBJECT: name=\"" + name + "\"");

        NodeList nl = node.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node child = nl.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getNodeName() == "category") {
                    parseCategory(child);
                }
            }
        }
    }

    void parseCategory(Node node) {
        String name = getAttribute(node, "name");
        System.out.println("CATEGORY: name=\"" + name + "\"");

        NodeList nl = node.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node child = nl.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getNodeName() == "module") {
                    parseModule(child);
                }
            }
        }
    }

    void parseModule(Node node) {
        String name = getAttribute(node, "name");
        System.out.println("MODULE: name=\"" + name + "\"");
        NodeList nl = node.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node child = nl.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getNodeName() == "question") {
                    parseQuestion(child);
                }
            }
        }
    }

    void parseQuestion(Node node) {
        String type = getAttribute(node, "type");
        System.out.println("QUESTION: type=\"" + type + "\"");

        NodeList nl = node.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node child = nl.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getNodeName() == "text") {
                    parseText(child);
                }
            }
        }

        nl = node.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node child = nl.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getNodeName() == "answer") {
                    parseAnswer(child);
                }
            }
        }
    }

    void parseText(Node node) {
        System.out.println("TEXT:");
        System.out.println(getText(node));
    }

    void parseAnswer(Node node) {
        String correct = getAttribute(node, "correct", null);
        if (correct != null)
            System.out.println("ANSWER: correct=\"" + correct + "\"");
        else
            System.out.println("ANSWER:");
        System.out.println(getText(node));
    }

    String getAttribute(Node node, String name, String defAttr) {
        String attr = defAttr;
        Node attrNode = node.getAttributes().getNamedItem(name);
        if (attrNode != null)
            attr = attrNode.getNodeValue();
        if (attr != null)
            attr = unescape(attr);
        return attr;
    }

    String getAttribute(Node node, String name) {
        return this.getAttribute(node, name, "");
    }

    String getText(Node node) {
        return unescape(node.getTextContent());
    }

    /**
     * unescape XML special chars
     */
    String unescape(String str) {
        str = str.replace("&quot;", "\"");
        str = str.replace("&apos;", "'");
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");
        str = str.replace("&amp;", "&");
        return str;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1)
            System.err.println("usage: XmlParser <xml-file>");
        else {
            new XmlParser(args[0]);
        }
    }
}
