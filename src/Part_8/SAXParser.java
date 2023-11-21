package Part_8;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SAXParser {
    public static String fileName = "schema_studs"; //Имя файла
    public SAXParser() throws ParserConfigurationException, IOException, SAXException {
        String useDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();

        String file = useDirectory+"/src/Part_8/"+fileName+".xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // подгрузка файла для расбора
        Document document = builder.parse(new File(file));

        List<Student> students = new ArrayList<>();

        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                // id студента через атрибут
                String id = node.getAttributes().getNamedItem("id").getNodeValue();

                // имя студента
                String name = elem.getElementsByTagName("Name")
                        .item(0).getChildNodes().item(0).getNodeValue();
                // группа
                String group = elem.getElementsByTagName("Group").item(0)
                        .getChildNodes().item(0).getNodeValue();
                // phone
                String phone = elem.getElementsByTagName("Phone")
                        .item(0).getChildNodes().item(0).getNodeValue();
                // email
                String email = elem.getElementsByTagName("Email")
                        .item(0).getChildNodes().item(0).getNodeValue();


                //Address
                LinkedHashMap<String, String> address = new LinkedHashMap<>();
                if(elem.getElementsByTagName("Address").getLength()>0){
                    address.put("Zip", elem.getElementsByTagName("Zip")
                            .item(0).getChildNodes().item(0).getNodeValue());
                    address.put("City", elem.getElementsByTagName("City")
                            .item(0).getChildNodes().item(0).getNodeValue());
                    address.put("Street", elem.getElementsByTagName("Street")
                            .item(0).getChildNodes().item(0).getNodeValue());
                    address.put("House", elem.getElementsByTagName("House")
                            .item(0).getChildNodes().item(0).getNodeValue());
                }



                students.add(new Student(id, name, group, phone, email, address));
            }
        }

        // Print all
        System.out.println("----------------------------------------");
        System.out.println("Вывод данныр распарсеного XML");
        for (Student empl : students)
            System.out.println(empl.toString());

    }
}
