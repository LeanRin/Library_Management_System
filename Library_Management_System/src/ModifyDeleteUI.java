import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyDeleteUI extends JFrame {
    private JTextField readerIdTextField;
    private JTextField readerNameTextField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> typeComboBox;
    private JTextField maxBorrowDaysTextField;
    private JTextField maxBorrowCountTextField;

    public ModifyDeleteUI() {
        super("删除读者  设计人：钟文俊");
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
        // 创建标签和输入框
        JLabel searchLabel = new JLabel("输入读者编号查找");
        searchLabel.setBounds(30, 30, 120, 25);
        JTextField searchReaderIdTextField = new JTextField();
        searchReaderIdTextField.setBounds(150, 30, 130, 25);
        JButton searchButton = new JButton("查找");
        searchButton.setBounds(120, 60, 120, 30);
        panel.add(searchLabel);
        panel.add(searchReaderIdTextField);
        panel.add(searchButton);


        // 标签和输入框
        JLabel readerIdLabel = new JLabel("读者编号");
        readerIdLabel.setBounds(30, 100, 80, 25);
        readerIdTextField = new JTextField();
        readerIdTextField.setEditable(false); // 只读
        readerIdTextField.setBounds(110, 100, 170, 25);

        JLabel readerNameLabel = new JLabel("读者姓名");
        readerNameLabel.setBounds(30, 140, 80, 25);
        readerNameTextField = new JTextField();
        readerNameTextField.setEditable(false); // 只读
        readerNameTextField.setBounds(110, 140, 170, 25);

        JLabel genderLabel = new JLabel("读者性别");
        genderLabel.setBounds(30, 180, 80, 25);
        genderComboBox = new JComboBox<>(new String[]{"", "男", "女"});
        genderComboBox.setBounds(110, 180, 170, 25);
        //禁用性别下拉框
        genderComboBox.setEnabled(false);

        JLabel typeLabel = new JLabel("读者类型");
        typeLabel.setBounds(30, 220, 80, 25);
        typeComboBox = new JComboBox<>(new String[]{"", "学生", "教师"});
        typeComboBox.setBounds(110, 220, 170, 25);
        //禁用读者类型下拉框
        typeComboBox.setEnabled(false);


        JLabel maxBorrowCountLabel = new JLabel("最大借阅量");
        maxBorrowCountLabel.setBounds(30, 260, 100, 25);
        maxBorrowCountTextField = new JTextField();
        maxBorrowCountTextField.setBounds(110, 260, 170, 25);
        maxBorrowCountTextField.setEditable(false); // 只读

        JLabel maxBorrowDaysLabel = new JLabel("最大借阅天数");
        maxBorrowDaysLabel.setBounds(30, 300, 100, 25);
        maxBorrowDaysTextField = new JTextField();
        maxBorrowDaysTextField.setBounds(110, 300, 170, 25);
        maxBorrowDaysTextField.setEditable(false); // 只读

        // 底部按钮
        JButton confirmButton = new JButton("删除");
        confirmButton.setBounds(60, 340, 100, 40);
        confirmButton.setEnabled(false); // 初始不可用
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 340, 100, 40);

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

        // 查询按钮事件
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String readerId = searchReaderIdTextField.getText();
                Reader reader = null;
                for (Reader r : LibrarySystem.readerList) {
                    if (r.getReaderId().equals(readerId)) {
                        reader = r;
                        break;
                    }
                }
                if (reader != null) {
                    readerIdTextField.setText(reader.getReaderId());
                    readerNameTextField.setText(reader.getReaderName());
                    genderComboBox.setSelectedItem(reader.getReaderGender());
                    typeComboBox.setSelectedItem(reader.getReaderType());
                    maxBorrowCountTextField.setText(String.valueOf(reader.getMaxBorrowCount()));
                    maxBorrowDaysTextField.setText(String.valueOf(reader.getMaxBorrowDays()));
                    confirmButton.setEnabled(true);

                }
                else {
                    JOptionPane.showMessageDialog(null, "未找到该读者！", "错误", JOptionPane.ERROR_MESSAGE);
                    confirmButton.setEnabled(false);
                }
            }
        });


        // 删除按钮事件
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String readerId = readerIdTextField.getText();
                // 检查读者是否有未还书籍
                for (Reader reader : LibrarySystem.readerList) {
                    if (reader.getReaderId().equals(readerId) && !reader.getBorrowedBooks().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "该读者有未还书籍，无法删除！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                int result = JOptionPane.showConfirmDialog(null, "确定要删除读者 " + readerId + " 吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    LibrarySystem.readerList.removeIf(r -> r.getReaderId().equals(readerId));
                    JOptionPane.showMessageDialog(null, "读者 " + readerId + " 删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    // 清空输入框
                    readerIdTextField.setText("");
                    readerNameTextField.setText("");
                    genderComboBox.setSelectedItem("");
                    typeComboBox.setSelectedItem("");
                    maxBorrowCountTextField.setText("");
                    maxBorrowDaysTextField.setText("");
                    confirmButton.setEnabled(false);
                    System.out.println(LibrarySystem.readerList);
                }
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