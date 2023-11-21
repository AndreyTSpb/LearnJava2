package Part_8;

import java.util.ArrayList;
import java.util.List;

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
            // create a new Employee and put it in Map
            String id = attributes.getValue("id");
            // initialize Employee object and set id attribute
            emp = new Employee();
            emp.setId(Integer.parseInt(id));
            // initialize list
            if (empList == null)
                empList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("name")) {
            // set boolean values for fields, will be used in setting Employee variables
            bName = true;
        } else if (qName.equalsIgnoreCase("surname")) {
            // set boolean values for fields, will be used in setting Employee variables
            bSurname = true;
        }else if (qName.equalsIgnoreCase("age")) {
            bAge = true;
        } else if (qName.equalsIgnoreCase("salary")) {
            bSalary = true;
        }
        // create the data container
        data = new StringBuilder();
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bAge) {
            // age element, set Employee age
            emp.setAge(Integer.parseInt(data.toString()));
            bAge = false;
        } else if (bName) {
            emp.setName(data.toString());
            bName = false;
        } else if (bSurname) {
            emp.setSurname(data.toString());
            bSurname = false;
        } else if (bSalary) {
            emp.setSalary(Float.parseFloat(data.toString()));
            bSalary = false;
        }

        if (qName.equalsIgnoreCase("Employee")) {
            // add Employee object to list
            empList.add(emp);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
