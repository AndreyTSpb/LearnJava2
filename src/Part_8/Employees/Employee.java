package Part_8.Employees;


public class Employee {
    private int id;
    private String name;
    private String surname;
    private int age;
    private float salary;

    public Employee(){

    }
    public Employee(int id, String name, String surname, int age, float salary){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee: Id="+this.id+" Name=" + this.name + " Surname=" + this.surname + " Age=" + this.age + " Salary=" + this.salary ;
    }
}
