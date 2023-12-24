/*public class Staff {
 String name;
 int id;
 float salary;
 public Staff(String name, int id, float salary) {
 this.name = name;
 this.id = id;
 this.salary = salary;
 }
}*/

public class Staff {
    String name;
    int id;
    float salary;

    public Staff(String name, int id, float salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public float getSalary() {
        return salary;
    }
}