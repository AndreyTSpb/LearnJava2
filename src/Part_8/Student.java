package Part_8;

import java.util.LinkedHashMap;

public class Student {
    public String id;
    public String name;
    public String group;
    public String phone;
    public String email;
    public LinkedHashMap<String,String> address;

    public Student(String id, String name, String group, String phone, String email, LinkedHashMap<String, String> address){
        this.id = id;
        this.name = name;
        this.group = group;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Переопределение метода
     * вывода значений объекта на экран
     * @return
     */
    @Override
    public String toString() {
        String addressStr = " Address: ";
        for (String addressKey : address.keySet()){
            addressStr += address.get(addressKey) + " ";
        }
        return "[" + id + ", " + name + ", " + group + ", " + phone + ", "
                + email + addressStr +"]";
    }
}
