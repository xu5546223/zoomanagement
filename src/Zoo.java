import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Zoo {
    ArrayList<Enclosure> enclosures = new ArrayList<>();
    ArrayList<Staff> staffs = new ArrayList<>();
    ArrayList<Zookeeper> zookeepers = new ArrayList<>();

    public void addEnclosure(Enclosure enclosure) {
        enclosures.add(enclosure);
    }

    public void addStaff(Staff newStaff) {
        staffs.add(newStaff);
    }

    public void addZookeeper(Zookeeper newZookeeper) {
        zookeepers.add(newZookeeper);
    }

    public String generateZooReport() {
    StringBuilder report = new StringBuilder("Zoo Report:\n");

    // 迭代所有動物欄
    for (Enclosure enclosure : enclosures) {
        report.append("Enclosure ").append(enclosures.indexOf(enclosure) + 1).append(":\n");

        // 迭代動物欄中的動物
        for (Animal animal : enclosure.animals) {
            report.append("   Animal: ").append(animal.getName()).append("\n");
            report.append("   Age: ").append(animal.getAge()).append("\n");
            report.append("   Health Status: ").append(animal.getHealthStatus()).append("\n");
            report.append("\n");
        }

        // 迭代動物欄中的員工
        for (Staff staff : enclosure.staffs) {
            report.append("   Staff: ").append(staff.getName()).append("\n");
            report.append("   ID: ").append(staff.getId()).append("\n");
            report.append("   Salary: $").append(staff.getSalary()).append("\n");
            report.append("\n");
        }

        // 迭代動物欄中的動物園管理員
        for (Zookeeper zookeeper : enclosure.zookeepers) {
            report.append("   Zookeeper: ").append(zookeeper.getName()).append("\n");
            report.append("   ID: ").append(zookeeper.getId()).append("\n");
            report.append("   Salary: $").append(zookeeper.getSalary()).append("\n");
            report.append("\n");
        }

        report.append("\n");
    }

    String reportStr = report.toString();

    try {
        // 將報告寫入到檔案中
        Files.write(Paths.get("ZooReport.txt"), reportStr.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
        e.printStackTrace();
    }

    return reportStr;
}


}