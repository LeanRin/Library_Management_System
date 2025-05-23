import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookInformationUI extends JFrame {
    public BookInformationUI() {
        super("图书信息查询  设计人：钟文俊");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        RecUI();
    }

    public void RecUI() {
        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));
        // 创建标签和文本框
        JLabel label = new JLabel("图书名称");
        label.setBounds(10, 20, 200, 30);
        panel.add(label);
        JTextField bookNameField = new JTextField();
        bookNameField.setBounds(80, 20, 200, 30);
        panel.add(bookNameField);
        JLabel authorLabel = new JLabel("作       者");
        authorLabel.setBounds(10, 60, 200, 30);
        panel.add(authorLabel);
        JTextField authorField = new JTextField();
        authorField.setBounds(80, 60, 200, 30);
        panel.add(authorField);
        JLabel categoryLabel = new JLabel("图书类别");
        categoryLabel.setBounds(300, 20, 200, 30);
        panel.add(categoryLabel);
        //创建下拉框
        String[] categories = {"","科技","社科","计算机", "文学", "历史", "科学", "艺术","其他"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBounds(360, 20, 200, 30);
        panel.add(categoryComboBox);

        JLabel publisherLabel = new JLabel("出版社");
        publisherLabel.setBounds(300, 60, 200, 30);
        panel.add(publisherLabel);
        JTextField publisherField = new JTextField();
        publisherField.setBounds(360, 60, 200, 30);
        panel.add(publisherField);
        JButton searchButton = new JButton("查询");
        searchButton.setBounds(120, 100, 100, 30);
        panel.add(searchButton);
        JButton exitButton = new JButton("退出");
        exitButton.setBounds(350, 100, 100, 30);
        panel.add(exitButton);

        //添加表格
        String[] columnNames = {"图书编号", "图书名称", "图书类别", "作者", "出版社", "出版时间", "定价", "库存"};
        Object[][] data = new Object[LibrarySystem.bookList.size()][columnNames.length];
        //载入数据
        for (int i = 0; i < LibrarySystem.bookList.size(); i++) {
            data[i][0] = LibrarySystem.bookList.get(i).getId();
            data[i][1] = LibrarySystem.bookList.get(i).getName();
            data[i][2] = LibrarySystem.bookList.get(i).getType();
            data[i][3] = LibrarySystem.bookList.get(i).getAuthor();
            data[i][4] = LibrarySystem.bookList.get(i).getPublishing();
            //格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String dateStr = sdf.format(LibrarySystem.bookList.get(i).getDate());
            data[i][5] = dateStr;
            data[i][6] = LibrarySystem.bookList.get(i).getPrice();
            data[i][7] = LibrarySystem.bookList.get(i).getNum();
        }
        JTable table = new JTable(data, columnNames);
        // 设置表头行高
        table.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 140, 585, 225);
        
        // 添加计数器标签
        JLabel counterLabel = new JLabel("当前记录数：" + data.length);
        counterLabel.setBounds(10, 120, 200, 20);
        panel.add(counterLabel);
        // 设置表格不可编辑
        table.setDefaultEditor(Object.class, null);
        //设置表格不可选中
        table.setCellSelectionEnabled(false);
        //设置表格不可拖动
        table.setDragEnabled(false);
        //设置表格不可移动
        table.getTableHeader().setReorderingAllowed(false);
        // 设置表格行高
        table.setRowHeight(30);
        // 设置表格字体
        table.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 12));
        panel.add(scrollPane);
        // 添加面板到窗口
        this.add(panel);

        // 添加查询按钮事件
        searchButton.addActionListener(e -> {
            String bookName = bookNameField.getText().trim();
            String author = authorField.getText().trim();
            String category = (String) categoryComboBox.getSelectedItem();
            String publisher = publisherField.getText().trim();

            List<Book> filteredBooks = new ArrayList<>();
            for (Book book : LibrarySystem.bookList) {
                boolean match = true;
                if (!bookName.isEmpty() && !book.getName().contains(bookName)) {
                    match = false;
                }
                if (!author.isEmpty() && !book.getAuthor().contains(author)) {
                    match = false;
                }
                if (!category.isEmpty() && !book.getType().equals(category)) {
                    match = false;
                }
                if (!publisher.isEmpty() && !book.getPublishing().contains(publisher)) {
                    match = false;
                }
                if (match) {
                    filteredBooks.add(book);
                }
            }

            Object[][] data1 = new Object[filteredBooks.size()][columnNames.length];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            for (int i = 0; i < filteredBooks.size(); i++) {
                Book book = filteredBooks.get(i);
                data1[i][0] = book.getId();
                data1[i][1] = book.getName();
                data1[i][2] = book.getType();
                data1[i][3] = book.getAuthor();
                data1[i][4] = book.getPublishing();
                data1[i][5] = sdf.format(book.getDate());
                data1[i][6] = book.getPrice();
                data1[i][7] = book.getNum();
            }
            table.setModel(new DefaultTableModel(data1, columnNames));
            counterLabel.setText("当前记录数：" + data1.length);
        });


        // 添加退出按钮事件
        exitButton.addActionListener(e -> {
            // 关闭窗口
            this.dispose();
        });
        // 设置窗口可见
        this.setVisible(true);


    }
}
