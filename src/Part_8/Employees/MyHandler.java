package Part_8.Employees;

import java.util.ArrayList;
import java.util.List;

import Part_8.Employees.Employee;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {
    private List empList = null;
    private Employee emp = null;
    private StringBuilder data = null;

    boolean bAge = false;
    boolean bName = false;
    boolean bSurname = false;
    boolean bSalary = false;

    public List getEmpList() {
        return empList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("Employee")) {
            // получаемзначение атрибута ID
            int id = Integer.parseInt(attributes.getValue("id"));
            emp = new Employee();// Инициальзируем новый объект Employee
            emp.setId(id); //добавляем ему ID
            if (empList == null) empList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("name")) {
            bName = true; //отмечаем наличие данного тега
        } else if (qName.equalsIgnoreCase("surname")) {
            bSurname = true; //отмечаем наличие данного тега
        }else if (qName.equalsIgnoreCase("age")) {
            bAge = true; //отмечаем наличие данного поля
        } else if (qName.equalsIgnoreCase("salary")) {
            bSalary = true; //отмечаем наличие данного тега
        }
        data = new StringBuilder(); //
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bAge) {
            // добавления возраста объекту
            emp.setAge(Integer.parseInt(data.toString()));
            bAge = false; //сбрасываем флаг
        } else if (bName) {
            // добавления имени объекту
            emp.setName(data.toString());
            bName = false; //сбрасываем флаг
        } else if (bSurname) {
            //добавление фамилии
            emp.setSurname(data.toString());
            bSurname = false; //сбрасываем флаг
        } else if (bSalary) {
            //добавление зп
            emp.setSalary(Float.parseFloat(data.toString()));
            bSalary = false; //сбрасываем флаг
        }
        // если найден закрывабщий тег Employee
        if (qName.equalsIgnoreCase("Employee")) {
            // добавляем в масив объектов Employees
            empList.add(emp);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length)); //добавляем значения тега
    }
}
