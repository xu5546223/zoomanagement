import javax.swing.SwingUtilities;

public class Main {
 public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Zoo myZoo = new Zoo();
            new ZooGui(myZoo);
        });
    }
}
