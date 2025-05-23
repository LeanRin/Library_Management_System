import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddBookUI extends JFrame {
    private JTextField bookIdTextField;
    private JTextField publisherTextField;
    private JTextField bookNameTextField;
    private JTextField publishDateTextField;
    private JTextField priceTextField;
    private JTextField authorTextField;
    private JTextField stockQuantityTextField;
    private JComboBox<String> categoryComboBox;

    public AddBookUI() {
        super("添加图书  设计人：钟文俊");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 350);
        this.setLocationRelativeTo(null);// 设置窗口居中
        this.setResizable(false);// 禁止窗口大小调整
        initUI();
    }

    public void initUI() {
        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));
        // 标签
        JLabel bookIdLabel = new JLabel("图书编号");
        JLabel publisherLabel = new JLabel("出版社");
        JLabel bookNameLabel = new JLabel("图书名称");
        JLabel publishDateLabel = new JLabel("出版时间");
        JLabel categoryLabel = new JLabel("图书类别");
        JLabel priceLabel = new JLabel("定价");
        JLabel authorLabel = new JLabel("作者");
        JLabel stockQuantityLabel = new JLabel("库存数量");
        // 文本框
        bookIdTextField = new JTextField();
        publisherTextField = new JTextField();
        bookNameTextField = new JTextField();
        publishDateTextField = new JTextField();
        priceTextField = new JTextField();
        authorTextField = new JTextField();
        stockQuantityTextField = new JTextField();
        // 下拉框
        String[] categories = {"","科技","社科","计算机", "文学", "历史", "科学", "艺术","其他"};
        // 按钮
        JButton confirmButton = new JButton("添加");
        JButton cancelButton = new JButton("取消");

        // 设置标签位置
        bookIdLabel.setBounds(50, 30, 100, 25);
        publisherLabel.setBounds(300, 30, 100, 25);
        bookNameLabel.setBounds(50, 80, 100, 25);
        publishDateLabel.setBounds(300, 80, 100, 25);
        categoryLabel.setBounds(50, 130, 100, 25);
        priceLabel.setBounds(300, 130, 100, 25);
        authorLabel.setBounds(50, 180, 100, 25);
        stockQuantityLabel.setBounds(300, 180, 100, 25);
        // 设置文本框位置
        bookIdTextField.setBounds(110, 30, 170, 25);
        publisherTextField.setBounds(360, 30, 170, 25);
        bookNameTextField.setBounds(110, 80, 170, 25);
        publishDateTextField.setBounds(360, 80, 170, 25);
        priceTextField.setBounds(360, 130, 170, 25);
        authorTextField.setBounds(110, 180, 170, 25);
        stockQuantityTextField.setBounds(360, 180, 170, 25);
        // 设置下拉框位置
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBounds(110, 130, 170, 25);
        // 设置按钮位置
        confirmButton.setBounds(140, 230, 100, 40);
        cancelButton.setBounds(320, 230, 100, 40);
        // 添加组件到面板
        panel.add(bookIdLabel);
        panel.add(publisherLabel);
        panel.add(bookNameLabel);
        panel.add(publishDateLabel);
        panel.add(categoryLabel);
        panel.add(priceLabel);
        panel.add(authorLabel);
        panel.add(stockQuantityLabel);
        panel.add(bookIdTextField);
        panel.add(publisherTextField);
        panel.add(bookNameTextField);
        panel.add(publishDateTextField);
        panel.add(priceTextField);
        panel.add(authorTextField);
        panel.add(stockQuantityTextField);
        panel.add(categoryComboBox);
        panel.add(confirmButton);
        panel.add(cancelButton);
        // 将面板添加到窗口
        this.add(panel);
        this.setVisible(true);


        // 确认按钮事件
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取输入的图书信息
                String id = bookIdTextField.getText();
                String name = bookNameTextField.getText();
                String type = (String) categoryComboBox.getSelectedItem();
                String author = authorTextField.getText();
                String publishing = publisherTextField.getText();
                String dateStr = publishDateTextField.getText();
                String priceStr = priceTextField.getText();
                String numStr = stockQuantityTextField.getText();

                //检查输入框是否为空
                if (id.isEmpty() || name.isEmpty() || type.isEmpty() || author.isEmpty() || publishing.isEmpty() ||
                    dateStr.isEmpty() || priceStr.isEmpty() || numStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写所有字段！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //检查下拉框是否选择
                if (type.equals("")) {
                    JOptionPane.showMessageDialog(null, "请选择图书类别！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 检查图书编号是否已存在
                for (Book book : LibrarySystem.bookList) {
                    if (book.getId().equals(id)) {
                        JOptionPane.showMessageDialog(null, "图书编号已存在，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }


                // 检查价格和库存数量是否为数字
                try {
                    Double.parseDouble(priceStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "价格必须为数字！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Integer.parseInt(numStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "库存数量必须为整数！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 检查出版时间格式是否正确
                try {
                    new SimpleDateFormat("yyyy-MM").parse(dateStr);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "出版时间格式错误，请使用yyyy-MM格式！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 检查价格和库存数量是否为正数
                if (Double.parseDouble(priceStr) <= 0 || Integer.parseInt(numStr) <= 0) {
                    JOptionPane.showMessageDialog(null, "价格和库存数量必须为正数！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 清空文本框
                bookIdTextField.setText("");
                publisherTextField.setText("");
                bookNameTextField.setText("");
                publishDateTextField.setText("");
                priceTextField.setText("");
                authorTextField.setText("");
                stockQuantityTextField.setText("");
                categoryComboBox.setSelectedIndex(0);


                // 创建新图书对象
                Book newBook = new Book(id, name, type, author, publishing, dateStr, priceStr, numStr);
                // 添加到图书列表
                LibrarySystem.bookList.add(newBook);
                // 显示添加成功提示
                JOptionPane.showMessageDialog(null, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                //打印booklist
                System.out.println(LibrarySystem.bookList);
            }
        });


        // 取消按钮事件
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 关闭窗口
                dispose();
            }
        });


    }
}