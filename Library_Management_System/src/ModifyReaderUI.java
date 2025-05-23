import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ModifyReaderUI extends JFrame {
    private JTextField readerIdTextField;
    private JTextField readerNameTextField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> typeComboBox;
    private JTextField maxBorrowDaysTextField;
    private JTextField maxBorrowCountTextField;

    public ModifyReaderUI() {
        super("修改读者  设计人：钟文俊");
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
        genderComboBox.setEditable(false); // 只读

        JLabel typeLabel = new JLabel("读者类型");
        typeLabel.setBounds(30, 220, 80, 25);
        typeComboBox = new JComboBox<>(new String[]{"", "学生", "老师"});
        typeComboBox.setBounds(110, 220, 170, 25);

        JLabel maxBorrowCountLabel = new JLabel("最大借阅量");
        maxBorrowCountLabel.setBounds(30, 260, 100, 25);
        maxBorrowCountTextField = new JTextField();
        maxBorrowCountTextField.setBounds(110, 260, 170, 25);

        JLabel maxBorrowDaysLabel = new JLabel("最大借阅天数");
        maxBorrowDaysLabel.setBounds(30, 300, 100, 25);
        maxBorrowDaysTextField = new JTextField();
        maxBorrowDaysTextField.setBounds(110, 300, 170, 25);

        // 底部按钮
        JButton confirmButton = new JButton("修改");
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


        // 修改按钮事件
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
                //检查最大借阅量是否大于0且小于等于10
                if (Integer.parseInt(maxBorrowCount) > 10 || Integer.parseInt(maxBorrowCount) <= 0) {
                    JOptionPane.showMessageDialog(null, "最大借阅量必须为1-10之间的整数！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //检查最大借阅天数是否大于0且小于等于100
                if (Integer.parseInt(maxBorrowDays) > 100 || Integer.parseInt(maxBorrowDays) <= 0) {
                    JOptionPane.showMessageDialog(null, "最大借阅天数必须为1-100之间的整数！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int maxBorrowCountInt = Integer.parseInt(maxBorrowCount);
                int maxBorrowDaysInt = Integer.parseInt(maxBorrowDays);
                List<String> borrowedBooks = List.of(); // 其他参数设置为空
                String borrowDate = "";
                String returnDate = "";

                // 更新读者信息
                for (int i = 0; i < LibrarySystem.readerList.size(); i++) {
                    Reader reader = LibrarySystem.readerList.get(i);
                    if (reader.getReaderId().equals(readerId)) {
                        // 创建新的读者对象，保留原有的借阅信息
                        Reader updatedReader = new Reader(
                            reader.getReaderId(),
                            readerNameTextField.getText(),  // 使用输入的新姓名
                            (String) genderComboBox.getSelectedItem(),  // 使用选择的新性别
                            (String) typeComboBox.getSelectedItem(),   // 使用选择的新类型
                            reader.getBorrowedBooks(),  // 保留原有的借阅图书
                            reader.getBorrowDate(),  // 保留原有的借阅日期
                            reader.getReturnDate(),  // 保留原有的归还日期
                            maxBorrowCountInt,  // 使用输入的新最大借阅量
                            maxBorrowDaysInt   // 使用输入的新最大借阅天数
                        );
                        LibrarySystem.readerList.set(i, updatedReader);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                // 清空文本框
                searchReaderIdTextField.setText("");
                readerIdTextField.setText("");
                readerNameTextField.setText("");
                genderComboBox.setSelectedIndex(0);
                typeComboBox.setSelectedIndex(0);
                maxBorrowCountTextField.setText("");
                maxBorrowDaysTextField.setText("");
                confirmButton.setEnabled(false);
                System.out.println(LibrarySystem.readerList);
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