import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddReaderUI extends JFrame {
    private JTextField readerIdTextField;
    private JTextField readerNameTextField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> typeComboBox;
    private JTextField maxBorrowDaysTextField;
    private JTextField maxBorrowCountTextField;

    public AddReaderUI() {
        super("添加读者  设计人：钟文俊");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(350, 450);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initUI();
    }

    private void initUI() {
        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));

        // 标签和输入框
        JLabel readerIdLabel = new JLabel("读者编号");
        readerIdLabel.setBounds(30, 30, 80, 25);
        readerIdTextField = new JTextField();
        readerIdTextField.setBounds(110, 30, 170, 25);

        JLabel readerNameLabel = new JLabel("读者姓名");
        readerNameLabel.setBounds(30, 70, 80, 25);
        readerNameTextField = new JTextField();
        readerNameTextField.setBounds(110, 70, 170, 25);

        JLabel genderLabel = new JLabel("读者性别");
        genderLabel.setBounds(30, 110, 80, 25);
        genderComboBox = new JComboBox<>(new String[]{"", "男", "女"});
        genderComboBox.setBounds(110, 110, 170, 25);

        JLabel typeLabel = new JLabel("读者类型");
        typeLabel.setBounds(30, 150, 80, 25);
        typeComboBox = new JComboBox<>(new String[]{"", "学生", "教师"});
        typeComboBox.setBounds(110, 150, 170, 25);

        JLabel maxBorrowCountLabel = new JLabel("最大借阅量");
        maxBorrowCountLabel.setBounds(30, 200, 100, 25);
        maxBorrowCountTextField = new JTextField();
        maxBorrowCountTextField.setBounds(110, 200, 170, 25);

        JLabel maxBorrowDaysLabel = new JLabel("最大借阅天数");
        maxBorrowDaysLabel.setBounds(30, 250, 100, 25);
        maxBorrowDaysTextField = new JTextField();
        maxBorrowDaysTextField.setBounds(110, 250, 170, 25);

        // 底部按钮
        JButton confirmButton = new JButton("添加");
        confirmButton.setBounds(60, 320, 100, 40);
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 320, 100, 40);

        // 添加所有组件到面板
        panel.add(readerIdLabel);
        panel.add(readerIdTextField);
        panel.add(readerNameLabel);
        panel.add(readerNameTextField);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(maxBorrowCountLabel);
        panel.add(maxBorrowCountTextField);
        panel.add(maxBorrowDaysLabel);
        panel.add(maxBorrowDaysTextField);
        panel.add(confirmButton);
        panel.add(cancelButton);

        // 添加按钮事件
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String readerId = readerIdTextField.getText();
                String readerName = readerNameTextField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String type = (String) typeComboBox.getSelectedItem();
                String maxBorrowCount = maxBorrowCountTextField.getText();
                String maxBorrowDays = maxBorrowDaysTextField.getText();

                // 检查输入框是否为空
                if (readerId.isEmpty() || readerName.isEmpty() || gender.isEmpty() || type.isEmpty() ||
                        maxBorrowCount.isEmpty() || maxBorrowDays.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写所有字段！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 检查下拉框是否选择
                if (gender.equals("") || type.equals("")) {
                    JOptionPane.showMessageDialog(null, "请选择性别和类型！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 检查最大借阅量和最大借阅天数是否为正整数
                try {
                    int maxCount = Integer.parseInt(maxBorrowCount);
                    int maxDays = Integer.parseInt(maxBorrowDays);
                    if (maxCount <= 0 || maxDays <= 0) {
                        JOptionPane.showMessageDialog(null, "最大借阅量和最大借阅天数必须为正整数！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "最大借阅量和最大借阅天数必须为正整数！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 检查读者编号是否已存在
                for (Reader reader : LibrarySystem.readerList) {
                    if (reader.getReaderId().equals(readerId)) {
                        JOptionPane.showMessageDialog(null, "读者ID已存在，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                int maxBorrowCountInt = Integer.parseInt(maxBorrowCount);
                int maxBorrowDaysInt = Integer.parseInt(maxBorrowDays);
                List<String> borrowedBooks = List.of(); // 其他参数设置为空
                String borrowDate = "";
                String returnDate = "";

                Reader newReader = new Reader(readerId, readerName, gender, type, borrowedBooks, borrowDate, returnDate, maxBorrowCountInt, maxBorrowDaysInt);
                LibrarySystem.readerList.add(newReader);
                JOptionPane.showMessageDialog(null, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        // 取消按钮事件
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(panel);
        this.setVisible(true);
    }
}