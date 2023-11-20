package Part_8;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class XMLCreate {
    public static String namespace = "http://localhost";
    public static String fileName = "schema_studs";

    public XMLCreate(LinkedHashMap<String, LinkedHashMap> studentList, LinkedHashMap<String, LinkedHashMap> studentAddressList) throws SAXException, ParserConfigurationException, TransformerException {
        String useDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        //загрузить XSD-схему из файла
        File xsdFile = new File(useDirectory+"/src/Part_8/"+fileName+".xsd");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsdFile); //Схема для XML



        Document document = createDocument(); //Создание экземпляра DocumentBuilder

        Element rootElement = createElement(document, "StudentList", "", new HashMap<String, String>());//Создание корневого элемента

        for (String studKey : studentList.keySet()){
            Element studentElement = createChildElement(document, rootElement, "Student", "", new HashMap<>());//Создание вложенных элементов
            LinkedHashMap<String, String> studValue = studentList.get(studKey);
            for (String studInfoKey : studValue.keySet()) {

                if(Objects.equals(studInfoKey, "Address")){
                    Element addressElement = createChildElement(document, studentElement, studInfoKey, studValue.get(studInfoKey), new HashMap<>());//Создание вложенных элементов
                    LinkedHashMap<String, String> address = studentAddressList.get(studKey);
                    for (String addressKey : address.keySet()){
                        createChildElement(document, addressElement, addressKey, String.valueOf(address.get(addressKey)), new HashMap<>());//Создание вложенных элементов
                    }
                }else{
                    createChildElement(document, studentElement, studInfoKey, studValue.get(studInfoKey), new HashMap<>());//Создание вложенных элементов
                }
            }
        }

        //Генерация XML-файла
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(useDirectory+"/src/Part_8/"+fileName+".xml"));
        transformer.transform(source, result);
    }

    /**
     * Создание документа
     * @return
     * @throws ParserConfigurationException
     */
    public static Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    /**
     * Создание корневого элемента
     * @param document
     * @param qualifiedName
     * @param value
     * @param attribute
     * @return
     */
    public static Element createElement(Document document, String qualifiedName, String value, HashMap<String, String> attribute){
        Element rootElement = document.createElementNS(namespace, qualifiedName);
        if(value.length() > 0) {
            rootElement.appendChild(document.createTextNode(value)); //Значение узла
        }
        document.appendChild(rootElement);
        //Определение и добавление атрибутов
        if(attribute.size() > 0){
            for (String attributeKey : attribute.keySet()) {
                String attributeValue = attribute.get(attributeKey);
                rootElement.setAttribute(attributeKey, attributeValue);
            }
        }
        return rootElement;
    }


    /**
     * Создание дочернего элемента
     * @param document
     * @param parent
     * @param qualifiedName
     * @param value
     * @param attribute
     * @return
     */
    public static Element createChildElement(Document document, Element parent, String qualifiedName, String value, HashMap<String, String> attribute){
        Element childElement = document.createElementNS(namespace, qualifiedName);
        if(value.length() > 0) {
            childElement.appendChild(document.createTextNode(value)); //Значение узла
        }
        parent.appendChild(childElement);
        //Определение и добавление атрибутов
        if(attribute.size() > 0){
            for (String attributeKey : attribute.keySet()) {
                String attributeValue = attribute.get(attributeKey);
                childElement.setAttribute(attributeKey, attributeValue);
            }
        }
        return childElement;
    }
}
