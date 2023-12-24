import java.util.ArrayList;

public class Enclosure {
    int capacity;
    ArrayList<Animal> animals = new ArrayList<>();
    ArrayList<Staff> staffs = new ArrayList<>();
    ArrayList<Zookeeper> zookeepers = new ArrayList<>();

    public Enclosure(int capacity) {
        this.capacity = capacity;
    }

    public void addAnimal(Animal animal) {
        if (animals.size() < capacity) {
            animals.add(animal);
        } else {
            System.out.println("Enclosure is full!");
        }
    }

    public void removeAnimal(Animal animal) {
        if (animals.contains(animal)) {
            animals.remove(animal);
            System.out.println("Animal removed from the enclosure!");
        } else {
            System.out.println("Animal not found in the enclosure!");
        }
    }

    public void addStaff(Staff staff) {
        if (staffs.size() < capacity) {
            staffs.add(staff);
        } else {
            System.out.println("Enclosure is full!");
        }
    }

    public void removeStaff(Staff staff) {
        if (staffs.contains(staff)) {
            staffs.remove(staff);
            System.out.println("Staff removed from the enclosure!");
        } else {
            System.out.println("Staff not found in the enclosure!");
        }
    }

    public void addZookeeper(Zookeeper zookeeper) {
        if (zookeepers.size() < capacity) {
            zookeepers.add(zookeeper);
        } else {
            System.out.println("Enclosure is full!");
        }
    }

    public void removeZookeeper(Zookeeper zookeeper) {
        if (zookeepers.contains(zookeeper)) {
            zookeepers.remove(zookeeper);
            System.out.println("Zookeeper removed from the enclosure!");
        } else {
            System.out.println("Zookeeper not found in the enclosure!");
        }
    }
}