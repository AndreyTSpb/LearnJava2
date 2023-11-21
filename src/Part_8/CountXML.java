package Part_8;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class CountXML {
    public static String fileName = "schema_studs"; //Имя файла
    public static HashMap<String, Integer> nodes = new HashMap<>();
    public CountXML(){
        String useDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();

        String file = useDirectory+"/src/Part_8/"+fileName+".xml";

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            Node root = document.getDocumentElement();
            nodes.put(root.getNodeName(), 1); //Добавляем корневой элемент
            getChildren(root);
            viewQntUnicXML();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Вывод количества уникальных элементов на экран
     */
    public static void viewQntUnicXML(){
        System.out.println("******************************************");
        for(String key : nodes.keySet()){
            System.out.println("Елемент "+ key+" содержится: "+nodes.get(key));
        }
    }

    /**
     * Рекурсивный подсчет
     * количества потомков
     * @param root - родительский элемент
     */
    public static void getChildren(Node root){
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child.getNodeType() != Node.TEXT_NODE) {
                //считаем колличество уникальных элементов
                if(nodes.containsKey(child.getNodeName())){
                    int k = nodes.get(child.getNodeName());
                    nodes.put(child.getNodeName(), ++k);
                }else{
                    nodes.put(child.getNodeName(), 1);
                }
                //рекурсивно проходим потомка
                getChildren(child);
            }
        }
    }
}
