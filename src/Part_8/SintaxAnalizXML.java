package Part_8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.parsers.SAXParser;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SintaxAnalizXML {
    public static String fileName = "employees"; //Имя файла

    public static void main(String[] args)
            throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        String useDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        String file = useDirectory+"/src/Part_8/"+fileName+".xml";
        domParser(file);
        saxParser(file);
        staxParser(file);
    }

    /**
     * Парсинг - XML-файл с помощью парсера DOM
     * @param file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void domParser(String file)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // подгрузка файла для расбора
        Document document = builder.parse(new File(file));

        List<Employee> employees = new ArrayList<>();

        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
                String name = elem.getElementsByTagName("name")
                        .item(0).getChildNodes().item(0).getNodeValue();
                String surname = elem.getElementsByTagName("surname").item(0)
                        .getChildNodes().item(0).getNodeValue();
                int age = Integer.parseInt(elem.getElementsByTagName("age")
                        .item(0).getChildNodes().item(0).getNodeValue());
                float salary = Float.parseFloat(elem.getElementsByTagName("salary")
                        .item(0).getChildNodes().item(0).getNodeValue());
                employees.add(new Employee(id, name, surname, age, salary));
            }
        }
        System.out.println("----------------------------------------");
        System.out.println("Вывод данныр распарсеного XML через DOM");
        for (Employee employee : employees){
            System.out.println(employee.toString());
        }
    }

    /**
     * Парсинг XML-файл с помощью SAX-парсера
     * @param file
     */
    public static void saxParser(String file){
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(new File(file), handler);
            List<Employee> empList = handler.getEmpList();
            System.out.println("----------------------------------------");
            System.out.println("Вывод данныр распарсеного XML через SAX");
            for(Employee emp : empList)
                System.out.println(emp);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Парсинг XML-файл с помощью StAX-парсера
     */
    public static void staxParser(String file) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));
        List<Employee> employees = new ArrayList<>();
        Employee employee = null;
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement(); //корневой элемент
                switch (startElement.getName().getLocalPart()) {
                    case "Employee":
                        employee = new Employee(); //если работник то создаем объект
                        Attribute id = startElement.getAttributeByName(new QName("id"));
                        employee.setId(Integer.parseInt(id.getValue()));
                        break;
                    case "name":
                        nextEvent = reader.nextEvent();
                        employee.setName(nextEvent.asCharacters().getData());
                        break;
                    case "surname":
                        nextEvent = reader.nextEvent();
                        employee.setSurname(nextEvent.asCharacters().getData());
                        break;
                    case "age":
                        nextEvent = reader.nextEvent();
                        employee.setAge(Integer.parseInt(nextEvent.asCharacters().getData()));
                        break;
                    case "salary":
                        nextEvent = reader.nextEvent();
                        employee.setSalary(Float.parseFloat(nextEvent.asCharacters().getData()));
                        break;
                }
            }
            //если достигли конечного тега, добавляем объект в список
            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("Employee")) {
                    employees.add(employee);
                }
            }
        }
        System.out.println("----------------------------------------");
        System.out.println("Вывод данныр распарсеного XML через StAX");
        for (Employee emp : employees){
            System.out.println(emp.toString());
        }
    }
}
