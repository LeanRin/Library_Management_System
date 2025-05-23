import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class ReaderInformationUI extends JFrame {
    private JTextField readerIdTextField;       // 读者编号显示框
    private JTextField readerNameTextField;     // 读者姓名显示框
    private JComboBox<String> genderComboBox;   // 性别下拉框
    private JComboBox<String> typeComboBox;     // 读者类型下拉框

    public ReaderInformationUI() {
        super("读者信息查询  设计人：钟文俊");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);  // 将窗口尺寸从600x500调整为800x600
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        RecUI();
    }

    private void RecUI() {
        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));
        // 创建标签和输入框
        JLabel searchLabel = new JLabel("读者编号");
        searchLabel.setBounds(30, 30, 120, 25);
        readerIdTextField = new JTextField();
        readerIdTextField.setBounds(110, 30, 170, 25);
        //读者姓名
        JLabel readerNameLabel = new JLabel("读者姓名");
        readerNameLabel.setBounds(30, 70, 80, 25);
        readerNameTextField = new JTextField();
        readerNameTextField.setBounds(110, 70, 170, 25);
        //性别
        JLabel genderLabel = new JLabel("性  别");
        genderLabel.setBounds(450, 30, 80, 25);
        genderComboBox = new JComboBox<>(new String[]{"", "男", "女"});
        genderComboBox.setBounds(550, 30, 170, 25);
        //读者类型
        JLabel typeLabel = new JLabel("读者类型");
        typeLabel.setBounds(450, 70, 80, 25);
        typeComboBox = new JComboBox<>(new String[]{"", "学生", "教师"});
        typeComboBox.setBounds(550, 70, 170, 25);
        //查询退出按钮
        JButton searchButton = new JButton("查询");
        searchButton.setBounds(120, 100, 120, 30);
        JButton exitButton = new JButton("退出");
        exitButton.setBounds(450, 100, 120, 30);

        // 修改列名，添加借阅日期和归还日期
        String[] columnNames = {"读者编号", "读者姓名", "性别", "读者类型", "借阅数量", "借阅书籍名称", "借阅日期", "归还日期"};
        Object[][] data = new Object[LibrarySystem.readerList.size()][columnNames.length];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 修改初始化数据部分
        for (int i = 0; i < LibrarySystem.readerList.size(); i++) {
            Reader reader = LibrarySystem.readerList.get(i);
            // 将图书编号转换为书名
            String bookNames = reader.getBorrowedBooks().stream()
                    .map(bookId -> LibrarySystem.bookList.stream()
                            .filter(book -> book.getId().equals(bookId))
                            .findFirst()
                            .map(Book::getName)
                            .orElse("未知书籍"))
                    .collect(Collectors.joining(", "));

            String borrowDateStr = reader.getBorrowDate() != null ? sdf.format(reader.getBorrowDate()) : "";
            String returnDateStr = reader.getReturnDate() != null ? sdf.format(reader.getReturnDate()) : "";

            data[i][0] = reader.getReaderId();
            data[i][1] = reader.getReaderName();
            data[i][2] = reader.getReaderGender();
            data[i][3] = reader.getReaderType();
            data[i][4] = reader.getBorrowedBooks().size(); // 修正为实际借阅数量
            data[i][5] = bookNames;  // 使用转换后的书名
            data[i][6] = borrowDateStr;
            data[i][7] = returnDateStr;
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        // 调整表格滚动面板尺寸
        scrollPane.setBounds(0, 140, 795, 425);  // 宽度从600改为780，高度从325改为400

        // 调整按钮位置
        searchButton.setBounds(150, 100, 120, 30);  // X坐标从120改为150
        exitButton.setBounds(450, 100, 120, 30);    // X坐标从360改为450

        // 设置表格的列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(30);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
        table.getColumnModel().getColumn(5).setPreferredWidth(300);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);

        // 设置表格的行高
        table.setRowHeight(30);
        // 设置表格的字体
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        // 设置表格不可编辑
        table.setDefaultEditor(Object.class, null);
        // 设置表格不可选中
        table.setCellSelectionEnabled(false);
        // 设置表格不可拖动
        table.setDragEnabled(false);
        // 设置表格不可移动
        table.getTableHeader().setReorderingAllowed(false);

        // 添加组件到面板
        panel.add(searchLabel);
        panel.add(readerIdTextField);
        panel.add(readerNameLabel);
        panel.add(readerNameTextField);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(searchButton);
        panel.add(exitButton);
        panel.add(scrollPane);
        JLabel counterLabel = new JLabel("总记录数：" + LibrarySystem.readerList.size());
        counterLabel.setBounds(30, 120, 200, 25);
        panel.add(counterLabel);
        //查询按钮根据选择框的内容进行查询，并将查询结果显示在表格中
        searchButton.addActionListener(e -> {
            String readerId = readerIdTextField.getText();
            String readerName = readerNameTextField.getText();
            String gender = (String) genderComboBox.getSelectedItem();
            String type = (String) typeComboBox.getSelectedItem();

            Object[][] filteredData = LibrarySystem.readerList.stream()
                    .filter(reader -> (readerId.isEmpty() || reader.getReaderId().contains(readerId)) &&
                            (readerName.isEmpty() || reader.getReaderName().contains(readerName)) &&
                            (gender.isEmpty() || reader.getReaderGender().equals(gender)) &&
                            (type.isEmpty() || reader.getReaderType().equals(type)))
                    .map(reader -> {
                        // 将图书编号转换为书名
                        String bookNames = reader.getBorrowedBooks().stream()
                                .map(bookId -> LibrarySystem.bookList.stream()
                                        .filter(book -> book.getId().equals(bookId))
                                        .findFirst()
                                        .map(Book::getName)
                                        .orElse("未知书籍"))
                                .collect(Collectors.joining(", "));

                        String borrowDateStr = reader.getBorrowDate() != null ? sdf.format(reader.getBorrowDate()) : "";
                        String returnDateStr = reader.getReturnDate() != null ? sdf.format(reader.getReturnDate()) : "";

                        return new Object[]{
                                reader.getReaderId(),
                                reader.getReaderName(),
                                reader.getReaderGender(),
                                reader.getReaderType(),
                                reader.getBorrowedBooks().size(), // 借阅数量
                                bookNames,  // 显示实际书名
                                borrowDateStr,
                                returnDateStr
                        };
                    })
                    .toArray(Object[][]::new);
            DefaultTableModel filteredModel = new DefaultTableModel(filteredData, columnNames);
            table.setModel(filteredModel);
            // 设置表格的列宽
            table.getColumnModel().getColumn(0).setPreferredWidth(30);
            table.getColumnModel().getColumn(1).setPreferredWidth(30);
            table.getColumnModel().getColumn(2).setPreferredWidth(30);
            table.getColumnModel().getColumn(3).setPreferredWidth(30);
            table.getColumnModel().getColumn(4).setPreferredWidth(30);
            table.getColumnModel().getColumn(5).setPreferredWidth(300);
            table.getColumnModel().getColumn(6).setPreferredWidth(80);
            table.getColumnModel().getColumn(7).setPreferredWidth(80);
            // 设置表格的行高
            table.setRowHeight(30);
            // 设置表格的字体
            table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        });
        // 退出按钮事件
        exitButton.addActionListener(e -> {
            this.dispose();
        });

        // 添加面板到窗口
        this.add(panel);
        this.setVisible(true);
    }
}