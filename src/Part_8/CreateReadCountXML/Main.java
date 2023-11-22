package Part_8.CreateReadCountXML;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Main {
    public static LinkedHashMap<String, LinkedHashMap> studentList;
    public static LinkedHashMap<String, LinkedHashMap> studentAddressList;

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        studentsArr(); //заполнение массива данными для создания файла
        //создание файла
        new XMLCreate(studentList, studentAddressList);
        //чтение файла
        new SAXParser();
        //подсчет уникального количества
        new CountXML();
    }

    public static void studentsArr(){
        LinkedHashMap<String, String> studInfo1 = new LinkedHashMap<String, String>();
        studInfo1.put("Name", "Anton Esenin");
        studInfo1.put("Group", "Group-1");
        studInfo1.put("Phone", "+7(555)552-55-55");
        studInfo1.put("Email", "email_1@emai.com");
        studInfo1.put("Address", "");

        LinkedHashMap<String, String> studInfo2 = new LinkedHashMap<String, String>();
        studInfo2.put("Name", "Kirill Voloshin");
        studInfo2.put("Group", "Group-1");
        studInfo2.put("Phone", "+7(555)525-55-25");
        studInfo2.put("Email", "email_2@emai.com");
        studInfo2.put("Address", "");

        LinkedHashMap<String, String> studInfo3 = new LinkedHashMap<String, String>();
        studInfo3.put("Name", "Maximilian Voloshin");
        studInfo3.put("Group", "Group-2");
        studInfo3.put("Phone", "+7(555)155-55-55");
        studInfo3.put("Email", "email_3@emai.com");
        studInfo3.put("Address", "");

        LinkedHashMap<String, String> studInfo4 = new LinkedHashMap<String, String>();
        studInfo4.put("Name", "Maximilian Voloshin");
        studInfo4.put("Group", "Group-2");
        studInfo4.put("Phone", "+7(555)155-55-55");
        studInfo4.put("Email", "email_3@emai.com");

        studentList = new LinkedHashMap<String, LinkedHashMap>();
        studentList.put("studInfo1", studInfo1);
        studentList.put("studInfo2", studInfo2);
        studentList.put("studInfo3", studInfo3);
        studentList.put("studInfo4", studInfo4);

        LinkedHashMap<String, String> studAddress1 = new LinkedHashMap<String, String>();
        studAddress1.put("Zip", "190021");
        studAddress1.put("City", "Москва");
        studAddress1.put("Street", "Пр. Ленина");
        studAddress1.put("House", "1");

        LinkedHashMap<String, String> studAddress2 = new LinkedHashMap<String, String>();
        studAddress2.put("Zip", "120021");
        studAddress2.put("City", "Саратов");
        studAddress2.put("Street", "Пр. Сталина");
        studAddress2.put("House", "10");

        LinkedHashMap<String, String> studAddress3 = new LinkedHashMap<String, String>();
        studAddress3.put("Zip", "110011");
        studAddress3.put("City", "Самара");
        studAddress3.put("Street", "Пр. Гагарина");
        studAddress3.put("House", "1");

        studentAddressList = new LinkedHashMap<String, LinkedHashMap>();
        studentAddressList.put("studInfo1", studAddress1);
        studentAddressList.put("studInfo2", studAddress2);
        studentAddressList.put("studInfo3", studAddress3);
    }

}
