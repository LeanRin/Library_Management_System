import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReviseBookUI extends JFrame {
    private JTextField bookIdTextField;
    private JTextField publisherTextField;
    private JTextField bookNameTextField;
    private JTextField publishDateTextField;
    private JTextField priceTextField;
    private JTextField authorTextField;
    private JTextField stockQuantityTextField;
    private JComboBox<String> categoryComboBox;
    private JTextField bookIdTextField1;

    public ReviseBookUI() {
        super("修改图书  设计人：钟文俊");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 350);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        RecUI();
    }

    public void RecUI() {
        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));
        // 标签
        JLabel titleLabel = new JLabel("请输入要修改的图书编号");
        titleLabel.setBounds(50, 20, 200, 30);
        titleLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 18));
        JLabel biankuang = new JLabel("================================================================================");
        biankuang.setBounds(0, 65, 600, 2);
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
        // 设置文本框不可编辑
        bookIdTextField.setEditable(false);

        bookIdTextField1 = new JTextField();
        bookIdTextField1.setBounds(280, 20, 150, 30);


        // 下拉框
        String[] categories = {"","科技","社科","计算机", "文学", "历史", "科学", "艺术","其他"};
        // 按钮
        JButton confirmButton = new JButton("修改");
        confirmButton.setEnabled(false);
        JButton cancelButton = new JButton("取消");
        JButton backButton = new JButton("查询");
        backButton.setBounds(450, 20, 100, 30);

        // 设置标签位置
        bookIdLabel.setBounds(50, 80, 100, 25);
        publisherLabel.setBounds(300, 80, 100, 25);
        bookNameLabel.setBounds(50, 125, 100, 25);
        publishDateLabel.setBounds(300, 125, 100, 25);
        categoryLabel.setBounds(50, 170, 100, 25);
        priceLabel.setBounds(300, 170, 100, 25);
        authorLabel.setBounds(50, 220, 100, 25);
        stockQuantityLabel.setBounds(300, 220, 100, 25);
        // 设置文本框位置
        bookIdTextField.setBounds(110, 80, 170, 25);
        publisherTextField.setBounds(360, 80, 170, 25);
        bookNameTextField.setBounds(110, 125, 170, 25);
        publishDateTextField.setBounds(360, 125, 170, 25);
        priceTextField.setBounds(360, 170, 170, 25);
        authorTextField.setBounds(110, 220, 170, 25);
        stockQuantityTextField.setBounds(360, 220, 170, 25);
        // 设置下拉框位置
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBounds(110, 170, 170, 25);
        // 设置按钮位置
        confirmButton.setBounds(140, 260, 100, 40);
        cancelButton.setBounds(320, 260, 100, 40);
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
        panel.add(backButton);
        panel.add(titleLabel);
        panel.add(biankuang);
        panel.add(bookIdTextField1);
        // 将面板添加到窗口
        this.add(panel);
        this.setVisible(true);
        // 查询按钮事件，获取编辑框内容与图书列表进行匹配，将匹配到的内容填入文本框中
        backButton.addActionListener(e -> {
            // 获取输入的图书编号
            String id = bookIdTextField1.getText();
            // 检查输入框是否为空
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入图书编号！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 遍历图书列表，查找匹配的图书
            for (Book book : LibrarySystem.bookList) {
                if (book.getId().equals(id)) {
                    // 将匹配到的图书信息填入文本框中
                    bookIdTextField.setText(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getId());
                    publisherTextField.setText(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getPublishing());
                    bookNameTextField.setText(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getName());
                    publishDateTextField.setText(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getDate().toString());
                    // 将日期格式化为yyyy-MM
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    String formattedDate = sdf.format(book.getDate());
                    publishDateTextField.setText(formattedDate);
                    // 设置价格和库存数量的文本框内容
                    priceTextField.setText(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getPrice() + "");
                    authorTextField.setText(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getAuthor());
                    stockQuantityTextField.setText(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getNum()+ "");
                    categoryComboBox.setSelectedItem(LibrarySystem.bookList.get(LibrarySystem.bookList.indexOf(book)).getType());
                    // 显示查询成功提示
                    JOptionPane.showMessageDialog(null, "查询成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    confirmButton.setEnabled(true);
                    return;

                }
            }
            // 如果没有找到匹配的图书，显示提示
            JOptionPane.showMessageDialog(null, "未找到该图书！", "提示", JOptionPane.INFORMATION_MESSAGE);
        });



        // 确认按钮事件
        confirmButton.addActionListener(e -> {
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
            if (type != null && (id.isEmpty() || name.isEmpty() || type.isEmpty() || author.isEmpty() || publishing.isEmpty() ||
                    dateStr.isEmpty() || priceStr.isEmpty() || numStr.isEmpty())) {
                JOptionPane.showMessageDialog(null, "请填写所有字段！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //检查下拉框是否选择
            if (type.equals("")) {
                JOptionPane.showMessageDialog(null, "请选择图书类别！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
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


            //将数据替换列表
            for (Book book : LibrarySystem.bookList) {
                if (book.getId().equals(id)) {
                    LibrarySystem.bookList.set(LibrarySystem.bookList.indexOf(book), new Book(id, name, type, author, publishing, dateStr, priceStr, numStr));
                    break;
                }
            }

            // 显示添加成功提示
            JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            confirmButton.setEnabled(false);
            //打印booklist
            System.out.println(LibrarySystem.bookList);
        });



        // 取消按钮事件
        cancelButton.addActionListener(e -> {
            // 关闭窗口
            dispose();
        });
    }
}