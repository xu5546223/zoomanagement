import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.awt.event.InputEvent;

public class ZooGui {
    private JFrame frame;
    private JTextArea textArea;
    private Zoo zoo;
    private Enclosure enclosure;

    public ZooGui(Zoo zoo) {
        this.zoo=zoo;
        enclosure=new Enclosure(10);
         // 將動物欄添加到動物園
         zoo.addEnclosure(enclosure);
        
        frame = new JFrame("Zoo Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.BLACK);
        


        textArea = new JTextArea();
        // 設置背景顏色為黑色
        textArea.setBackground(Color.BLACK); 
        // 設置文字顏色為白色
        textArea.setForeground(Color.WHITE); 
        // 禁止使用者輸入
        textArea.setEditable(false); 
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        
        JMenu animalMenu = new JMenu("Animal");
        JMenu staffMenu = new JMenu("Staff");
        JMenu zookeeperMenu = new JMenu("Zookeeper");

        // 在 "Animal" 選單中添加選項
        JMenuItem addAnimalItem = createMenuItem("Add Animal", KeyEvent.VK_A, KeyEvent.VK_F1);
        JMenuItem deleteAnimalItem = createMenuItem("Delete Animal", KeyEvent.VK_D, KeyEvent.VK_F2);
        JMenuItem findAnimalItem = createMenuItem("Find Animal", KeyEvent.VK_F, KeyEvent.VK_F3);
        animalMenu.add(addAnimalItem);
        animalMenu.add(deleteAnimalItem);
        animalMenu.add(findAnimalItem);

        // 在 "Staff" 選單中添加選項
        JMenuItem addStaffItem = createMenuItem("Add Staff", KeyEvent.VK_A, KeyEvent.VK_F4);
        JMenuItem deleteStaffItem = createMenuItem("Delete Staff", KeyEvent.VK_D, KeyEvent.VK_F5);
        JMenuItem findStaffItem = createMenuItem("Find Staff", KeyEvent.VK_F, KeyEvent.VK_F6);
        staffMenu.add(addStaffItem);
        staffMenu.add(deleteStaffItem);
        staffMenu.add(findStaffItem);

        // 在 "Zookeeper" 選單中添加選項
        JMenuItem addZookeeperItem = createMenuItem("Add Zookeeper", KeyEvent.VK_A, KeyEvent.VK_F7);
        JMenuItem deleteZookeeperItem = createMenuItem("Delete Zookeeper", KeyEvent.VK_D, KeyEvent.VK_F8);
        JMenuItem findZookeeperItem = createMenuItem("Find Zookeeper", KeyEvent.VK_F, KeyEvent.VK_F9);
        zookeeperMenu.add(addZookeeperItem);
        zookeeperMenu.add(deleteZookeeperItem);
        zookeeperMenu.add(findZookeeperItem);

        // 在 選單中添加 generateReport 選項
        JMenuItem generateReportItem = new JMenuItem("Generate Report");
        generateReportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));

        // 在 選單中添加 cleanTextArea 選項
        JMenuItem cleanItem = new JMenuItem("Clean TextArea");
        cleanItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));

        // 添加 "Alt + A" 快捷鍵
        JMenuItem aboutMenu = new JMenuItem("About");
        aboutMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
   

        // 將 "Animal", "Staff", 和 "Zookeeper" 選單添加到 "Options" 選單中
        optionsMenu.add(animalMenu);
        optionsMenu.add(staffMenu);
        optionsMenu.add(zookeeperMenu);
        optionsMenu.add(generateReportItem);
        optionsMenu.add(cleanItem);
        
    

        // 將 "Options" 選單添加到選單列
        menuBar.add(optionsMenu);
        menuBar.add(aboutMenu);



        addAnimalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validInput = false;
                String animalName = "";
                int animalAge = 0;
                String animalHealthStatus = "";
        
                while (!validInput) {
                    animalName = JOptionPane.showInputDialog(frame, "Enter the name of the animal:");
                    if (animalName == null) {
                        return; // 結束輸入
                    } else if (!animalName.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid name.");
                        continue;
                    }
        
                    boolean validAge = false;
                    while (!validAge) {
                        try {
                            String ageInput = JOptionPane.showInputDialog(frame, "Enter the age of the animal:");
                            if (ageInput == null) {
                                return; // 結束輸入
                            }
                            animalAge = Integer.parseInt(ageInput);
                            validAge = true;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number for the age.");
                        }
                    }
        
                    String[] healthStatusOptions = {"Healthy", "Sick"};
                    animalHealthStatus = (String) JOptionPane.showInputDialog(frame, "Select the health status of the animal:", "Health Status", JOptionPane.QUESTION_MESSAGE, null, healthStatusOptions, healthStatusOptions[0]);
                    if (animalHealthStatus == null) {
                        return; // 結束輸入
                    }
        
                    validInput = true;
                }
        
                Animal newAnimal = new Animal(animalName, animalAge, animalHealthStatus);
                enclosure.addAnimal(newAnimal);
                textArea.append("Added animal: " + animalName + "\n");
            }
        });

        deleteAnimalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 檢查是否有動物
                if (enclosure.animals.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "沒有動物", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 創建一個包含所有動物名稱的陣列
                String[] animalNames = new String[enclosure.animals.size()];
                for (int i = 0; i < enclosure.animals.size(); i++) {
                    animalNames[i] = enclosure.animals.get(i).name;
                }
        
                // 讓用戶選擇要刪除的動物
                String animalName = (String) JOptionPane.showInputDialog(frame, "Enter the name of the animal to delete:", "Delete Animal", JOptionPane.QUESTION_MESSAGE, null, animalNames, animalNames[0]);
        
                // 找到並刪除選擇的動物
                for (Animal animal : enclosure.animals) {
                    if (animal.name.equals(animalName)) {
                        enclosure.removeAnimal(animal);
                        textArea.append("Deleted animal: " + animalName + "\n");
                        break;
                    }
                }
            }
        });
        
        findAnimalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 檢查是否有動物
                if (enclosure.animals.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "沒有動物", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 創建一個包含所有動物名稱的陣列
                String[] animalNames = new String[enclosure.animals.size()];
                for (int i = 0; i < enclosure.animals.size(); i++) {
                    animalNames[i] = enclosure.animals.get(i).name;
                }
        
                // 讓用戶選擇要查找的動物
                String animalName = (String) JOptionPane.showInputDialog(frame, "Enter the name of the animal to find:", "Find Animal", JOptionPane.QUESTION_MESSAGE, null, animalNames, animalNames[0]);
        
                // 找到並顯示選擇的動物的資訊
                for (Animal animal : enclosure.animals) {
                    if (animal.name.equals(animalName)) {
                        textArea.append("Found animal: " + animalName + "\n");
                        textArea.append("Age: " + animal.age + "\n");
                        textArea.append("Health Status: " + animal.healthStatus + "\n");
                        break;
                    }
                }
            }
        });
        
        addStaffItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validInput = false;
                String staffName = "";
                int staffId = 0;
                float staffSalary = 0.0f;
        
                while (!validInput) {
                    staffName = JOptionPane.showInputDialog(frame, "Enter the name of the staff:");
                    if (staffName == null) {
                        return; // 結束輸入
                    } else if (!staffName.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid name.");
                        continue;
                    }
        
                    boolean validId = false;
                    while (!validId) {
                        try {
                            String idInput = JOptionPane.showInputDialog(frame, "Enter the ID of the staff:");
                            if (idInput == null) {
                                return; // 結束輸入
                            }
                            staffId = Integer.parseInt(idInput);
                            validId = true;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number for the ID.");
                        }
                    }
        
                    boolean validSalary = false;
                    while (!validSalary) {
                        try {
                            String salaryInput = JOptionPane.showInputDialog(frame, "Enter the salary of the staff:");
                            if (salaryInput == null) {
                                return; // 結束輸入
                            }
                            staffSalary = Float.parseFloat(salaryInput);
                            validSalary = true;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number for the salary.");
                        }
                    }
        
                    validInput = true;
                }
        
                Staff newStaff = new Staff(staffName, staffId, staffSalary);
                enclosure.addStaff(newStaff);
                textArea.append("Added staff: " + staffName + "\n");
            }
        });
        
        deleteStaffItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 檢查是否有員工
                if (enclosure.staffs.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "沒有員工", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 創建一個包含所有員工名稱的陣列
                String[] staffNames = new String[enclosure.staffs.size()];
                for (int i = 0; i < enclosure.staffs.size(); i++) {
                    staffNames[i] = enclosure.staffs.get(i).name;
                }
        
                // 讓用戶選擇要刪除的員工
                String staffName = (String) JOptionPane.showInputDialog(frame, "Enter the name of the staff to delete:", "Delete Staff", JOptionPane.QUESTION_MESSAGE, null, staffNames, staffNames[0]);
        
                // 找到並刪除選擇的員工
                for (Staff staff : enclosure.staffs) {
                    if (staff.name.equals(staffName)) {
                        enclosure.removeStaff(staff);
                        textArea.append("Deleted staff: " + staffName + "\n");
                        break;
                    }
                }
            }
        });
        
        findStaffItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 檢查是否有員工
                if (enclosure.staffs.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "沒有員工", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 創建一個包含所有員工名稱的陣列
                String[] staffNames = new String[enclosure.staffs.size()];
                for (int i = 0; i < enclosure.staffs.size(); i++) {
                    staffNames[i] = enclosure.staffs.get(i).name;
                }
        
                // 讓用戶選擇要查找的員工
                String staffName = (String) JOptionPane.showInputDialog(frame, "Enter the name of the staff to find:", "Find Staff", JOptionPane.QUESTION_MESSAGE, null, staffNames, staffNames[0]);
        
                // 找到並顯示選擇的員工的資訊
                for (Staff staff : enclosure.staffs) {
                    if (staff.name.equals(staffName)) {
                        textArea.append("Found staff: " + staffName + "\n");
                        textArea.append("ID: " + staff.id + "\n");
                        textArea.append("Salary: " + staff.salary + "\n");
                        break;
                    }
                }
            }
        });
        
        addZookeeperItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validInput = false;
                String zookeeperName = "";
                int zookeeperId = 0;
                float zookeeperSalary = 0.0f;
        
                while (!validInput) {
                    zookeeperName = JOptionPane.showInputDialog(frame, "Enter the name of the zookeeper:");
                    if (zookeeperName == null) {
                        return; // 結束輸入
                    } else if (!zookeeperName.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid name.");
                        continue;
                    }
        
                    boolean validId = false;
                    while (!validId) {
                        try {
                            String idInput = JOptionPane.showInputDialog(frame, "Enter the ID of the zookeeper:");
                            if (idInput == null) {
                                return; // 結束輸入
                            }
                            zookeeperId = Integer.parseInt(idInput);
                            validId = true;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number for the ID.");
                        }
                    }
        
                    boolean validSalary = false;
                    while (!validSalary) {
                        try {
                            String salaryInput = JOptionPane.showInputDialog(frame, "Enter the salary of the zookeeper:");
                            if (salaryInput == null) {
                                return; // 結束輸入
                            }
                            zookeeperSalary = Float.parseFloat(salaryInput);
                            validSalary = true;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number for the salary.");
                        }
                    }
        
                    validInput = true;
                }
        
                Zookeeper newZookeeper = new Zookeeper(zookeeperName, zookeeperId, zookeeperSalary);
                enclosure.addZookeeper(newZookeeper);
                textArea.append("Added zookeeper: " + zookeeperName + "\n");
            }
        });
        
        deleteZookeeperItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 檢查是否有動物園管理員
                if (enclosure.zookeepers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "沒有動物園管理員", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 創建一個包含所有動物園管理員名稱的陣列
                String[] zookeeperNames = new String[enclosure.zookeepers.size()];
                for (int i = 0; i < enclosure.zookeepers.size(); i++) {
                    zookeeperNames[i] = enclosure.zookeepers.get(i).name;
                }
        
                // 讓用戶選擇要刪除的動物園管理員
                String zookeeperName = (String) JOptionPane.showInputDialog(frame, "Enter the name of the zookeeper to delete:", "Delete Zookeeper", JOptionPane.QUESTION_MESSAGE, null, zookeeperNames, zookeeperNames[0]);
        
                // 找到並刪除選擇的動物園管理員
                for (Zookeeper zookeeper : enclosure.zookeepers) {
                    if (zookeeper.name.equals(zookeeperName)) {
                        enclosure.removeZookeeper(zookeeper);
                        textArea.append("Deleted zookeeper: " + zookeeperName + "\n");
                        break;
                    }
                }
            }
        });
        
        findZookeeperItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 檢查是否有動物園管理員
                if (enclosure.zookeepers.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "沒有動物園管理員", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 創建一個包含所有動物園管理員名稱的陣列
                String[] zookeeperNames = new String[enclosure.zookeepers.size()];
                for (int i = 0; i < enclosure.zookeepers.size(); i++) {
                    zookeeperNames[i] = enclosure.zookeepers.get(i).name;
                }
        
                // 讓用戶選擇要查找的動物園管理員
                String zookeeperName = (String) JOptionPane.showInputDialog(frame, "Enter the name of the zookeeper to find:", "Find Zookeeper", JOptionPane.QUESTION_MESSAGE, null, zookeeperNames, zookeeperNames[0]);
        
                // 找到並顯示選擇的動物園管理員的資訊
                for (Zookeeper zookeeper : enclosure.zookeepers) {
                    if (zookeeper.name.equals(zookeeperName)) {
                        textArea.append("Found zookeeper: " + zookeeperName + "\n");
                        textArea.append("ID: " + zookeeper.id + "\n");
                        textArea.append("Salary: " + zookeeper.salary + "\n");
                        break;
                    }
                }
            }
        });
        
        generateReportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // 獲取動物園中所有動物的資訊
        String report = zoo.generateZooReport();

        // 在 textArea 中顯示報告
        textArea.setText(report);
    }
        });

        cleanItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在事件分發線程中清除 textArea 的內容
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText("");
                    }
                });
            }
        });

        aboutMenu.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JFrame aboutFrame = new JFrame("About");
        aboutFrame.getContentPane().setBackground(Color.BLACK);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        // 使用相對路徑來設置圖片
        ImageIcon imageIcon = new ImageIcon(Zoo.class.getResource("picture.png"));

        // 設置圖片大小以適應標籤
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(812, 449, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        // 創建一個包含圖片的標籤
        JLabel imageLabel = new JLabel(imageIcon);

        // 設置圖片標籤的相對位置
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.PAGE_END; // PAGE_END 將元素設置在垂直方向的底部
        gridBagConstraints.weighty = 1.0; // 設置 weighty 屬性以保持在底部
        gridBagConstraints.insets = new Insets(0, 0, 0, 10); // 調整右邊的空間
        panel.add(imageLabel, gridBagConstraints);

         
        // 創建一個包含文字的標籤
        JLabel textLabel = new JLabel("<html><div style='text-align: center;'>The creator has something to say:<br>本程式提供簡單的增刪改查功能<br>並能在最後產出一份動物園所有內容的報告<br>提供快捷鍵:Shift+F1-F9[添加、刪除、搜尋]<br>Alt+A:About<br>Alt+C:清理頁面<br>Alt+G:產出報告<br>有任何問題歡迎聯繫:jass6012@gmail.com<br>雲科魔方社社長:饒峻睿</div></html>");
        Font font = new Font("Serif", Font.BOLD, 20);
        textLabel.setFont(font);
        textLabel.setForeground(Color.white);
        panel.add(textLabel);

        aboutFrame.add(panel);
        aboutFrame.pack();
        aboutFrame.setSize(1000, 800);
        aboutFrame.setResizable(false);
        // 設置默認關閉操作
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        aboutFrame.setVisible(true);
    }
});

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }
    private JMenuItem createMenuItem(String text, int mnemonic, int acceleratorKey) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setMnemonic(mnemonic);
    
        // 使用 acceleratorKey 和 SHIFT_DOWN_MASK 來設置快捷鍵
        KeyStroke keyStroke = KeyStroke.getKeyStroke(acceleratorKey, InputEvent.SHIFT_DOWN_MASK);
        menuItem.setAccelerator(keyStroke);
    
        return menuItem;
    }
    

    
}
