import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowBookUI extends JFrame {
    public BorrowBookUI() {
        super("图书借阅  设计人：钟文俊");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 500);
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

        //添加借阅信息
        JLabel borrowLabel = new JLabel("请输入读者编号");
        borrowLabel.setBounds(10, 400, 100, 30);
        panel.add(borrowLabel);
        //添加借阅信息文本框
        JTextField borrowField = new JTextField();
        borrowField.setBounds(110, 400, 130, 30);
        panel.add(borrowField);
        //添加借阅数量标签
        JLabel borrowNumLabel = new JLabel("借阅数量:");
        borrowNumLabel.setBounds(250, 400, 100, 30);
        panel.add(borrowNumLabel);
        //添加借阅数量文本框
        JTextField borrowNumField = new JTextField("1");
        borrowNumField.setBounds(350, 400, 80, 30);
        panel.add(borrowNumField);
        //不可编辑借阅数量文本框
        borrowNumField.setEditable(false);
        //添加借阅按钮
        JButton borrowButton = new JButton("借阅");
        borrowButton.setBounds(450, 400, 100, 30);
        panel.add(borrowButton);

        //添加表格
        String[] columnNames = {"图书编号", "图书名称", "图书类别", "作者", "出版社", "出版时间", "定价", "库存"};
        Object[][] data = new Object[LibrarySystem.bookList.size()][columnNames.length];
        // 日期格式化对象，显示年月日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //载入数据
        for (int i = 0; i < LibrarySystem.bookList.size(); i++) {
            data[i][0] = LibrarySystem.bookList.get(i).getId();
            data[i][1] = LibrarySystem.bookList.get(i).getName();
            data[i][2] = LibrarySystem.bookList.get(i).getType();
            data[i][3] = LibrarySystem.bookList.get(i).getAuthor();
            data[i][4] = LibrarySystem.bookList.get(i).getPublishing();
            //格式化日期，显示年月日
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
        // 设置表格不可编辑
        table.setDefaultEditor(Object.class, null);
        //设置表格不可拖动
        table.setDragEnabled(false);
        //设置表格不可移动
        table.getTableHeader().setReorderingAllowed(false);
        // 设置表格行高
        table.setRowHeight(30);
        // 设置表格字体
        table.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 12));
        // 添加计数器标签
        JLabel counterLabel = new JLabel("当前记录数：" + LibrarySystem.bookList.size());
        counterLabel.setBounds(10, 120, 200, 20);
        panel.add(counterLabel);

        // 添加表格
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
            // 日期格式化对象，显示年月日
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < filteredBooks.size(); i++) {
                Book book = filteredBooks.get(i);
                data1[i][0] = book.getId();
                data1[i][1] = book.getName();
                data1[i][2] = book.getType();
                data1[i][3] = book.getAuthor();
                data1[i][4] = book.getPublishing();
                data1[i][5] = sdf1.format(book.getDate());
                data1[i][6] = book.getPrice();
                data1[i][7] = book.getNum();
            }
            table.setModel(new DefaultTableModel(data1, columnNames));
            counterLabel.setText("当前记录数：" + data1.length);
        });

        // 添加借阅按钮事件
        borrowButton.addActionListener(e -> {
            String readerId = borrowField.getText().trim();
            String borrowNumStr = borrowNumField.getText().trim();
            int selectedRow = table.getSelectedRow();

            // 检查读者编号是否为空
            if (readerId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入读者编号");
                return;
            }
            // 检查是否选择了图书
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请选择图书");
                return;
            }

            // 获取图书编号和库存数量
            Object bookIdObj = table.getValueAt(selectedRow, 0);
            String bookId = bookIdObj.toString();

            Object bookNumObj = table.getValueAt(selectedRow, 7);
            int bookNum;
            if (bookNumObj instanceof Integer) {
                bookNum = (Integer) bookNumObj;
            } else {
                bookNum = Integer.parseInt(bookNumObj.toString());
            }

            // 检查库存是否足够
            int borrowNum = Integer.parseInt(borrowNumStr);
            if (bookNum < borrowNum) {
                JOptionPane.showMessageDialog(this, "库存不足");
                return;
            }

            // 查找读者信息
            Reader reader = null;
            for (Reader r : LibrarySystem.readerList) {
                if (r.getReaderId().equals(readerId)) {
                    reader = r;
                    break;
                }
            }

            // 检查读者是否存在
            if (reader == null) {
                JOptionPane.showMessageDialog(this, "未找到该读者");
                return;
            }

            // 检查读者是否已经借阅过该图书
            if (reader.getBorrowedBooks().contains(bookId)) {
                JOptionPane.showMessageDialog(this, "您已借阅过该图书，不能重复借阅");
                return;
            }

            // 检查读者借阅数量是否达到上限
            int currentBorrowCount = reader.getBorrowedBooks().size();
            if (currentBorrowCount + borrowNum > reader.getMaxBorrowCount()) {
                int allowed = reader.getMaxBorrowCount() - currentBorrowCount;
                JOptionPane.showMessageDialog(this,
                        "您最多还可借阅" + allowed + "本图书\n当前已借阅: " + currentBorrowCount +
                                "\n最大可借阅: " + reader.getMaxBorrowCount());
                return;
            }

            // 记录借阅日期
            Date borrowDate = new Date(); // 当前日期
            // 借阅日期加上读者借阅天数
            long borrowDays = reader.getMaxBorrowDays() * 24 * 60 * 60 * 1000L; // 转换为毫秒
            Date returnDate = new Date(borrowDate.getTime() + borrowDays);

            // 更新读者信息
            List<String> borrowedBooks = new ArrayList<>(reader.getBorrowedBooks()); // 创建可变列表
            for (int i = 0; i < borrowNum; i++) {
                borrowedBooks.add(bookId);
            }

            reader.setBorrowedBooks(borrowedBooks);
            reader.setBorrowDate(borrowDate);
            reader.setReturnDate(returnDate);

            // 更新图书库存
            for (Book book : LibrarySystem.bookList) {
                if (book.getId().equals(bookId)) {
                    book.setNum(book.getNum() - borrowNum);
                    break;
                }
            }

            // 更新表格数据
            Object[][] newData = new Object[LibrarySystem.bookList.size()][columnNames.length];
            // 日期格式化对象，显示年月日
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < LibrarySystem.bookList.size(); i++) {
                Book book = LibrarySystem.bookList.get(i);
                newData[i][0] = book.getId();
                newData[i][1] = book.getName();
                newData[i][2] = book.getType();
                newData[i][3] = book.getAuthor();
                newData[i][4] = book.getPublishing();
                newData[i][5] = sdf2.format(book.getDate());
                newData[i][6] = book.getPrice();
                newData[i][7] = book.getNum();
            }
            table.setModel(new DefaultTableModel(newData, columnNames));

            // 显示成功消息，包含归还日期
            JOptionPane.showMessageDialog(this, "借阅成功！\n借阅数量: " + borrowNum +
                    "\n借阅日期: " + sdf2.format(borrowDate) +
                    "\n归还日期: " + sdf2.format(returnDate));
        });

        // 退出按钮事件
        exitButton.addActionListener(e -> {
            this.dispose();
        });
        // 设置窗口可见
        this.setVisible(true);
    }
}