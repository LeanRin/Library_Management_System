import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RetunBookUI extends JFrame {
    public RetunBookUI() {
        super("图书归还  设计人：钟文俊");
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
        JLabel label = new JLabel("读者编号");
        label.setBounds(10, 20, 200, 30);
        panel.add(label);
        JTextField readerIdField = new JTextField();
        readerIdField.setBounds(80, 20, 100, 30);
        panel.add(readerIdField);
        //查找按钮
        JButton searchButton = new JButton("查找");
        searchButton.setBounds(200, 20, 100, 30);
        panel.add(searchButton);

        //归还按钮
        JButton returnButton = new JButton("归还");
        returnButton.setBounds(330, 20, 100, 30);
        panel.add(returnButton);
        //禁用按钮
        returnButton.setEnabled(false);

        JButton exitButton = new JButton("退出");
        exitButton.setBounds(460, 20, 100, 30);
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
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        // 设置表头行高
        table.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        //表格位置
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 80, 585, 282);
        // 添加计数器标签
        JLabel counterLabel = new JLabel("当前待还记录数：0");
        counterLabel.setBounds(10, 55, 200, 20);
        panel.add(counterLabel);
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
        panel.add(scrollPane);

        //查询按钮事件,判断输入框是否为空,查找读者信息中的图书列表,获取图书编号,查询图书列表,获取图书信息,显示在表格中
        searchButton.addActionListener(e -> {
            String readerId = readerIdField.getText();
            if (readerId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入读者编号", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Reader reader = null;
            for (Reader r : LibrarySystem.readerList) {
                if (r.getReaderId().equals(readerId)) {
                    reader = r;
                    break;
                }
            }
            if (reader == null) {
                JOptionPane.showMessageDialog(this, "未找到该读者信息", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<String> borrowedBooks = reader.getBorrowedBooks();
            if (borrowedBooks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "该读者没有借阅图书", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            model.setRowCount(0); // 清空表格数据
            for (String bookId : borrowedBooks) {
                for (Book book : LibrarySystem.bookList) {
                    if (book.getId().equals(bookId)) {
                        Object[] rowData = {book.getId(), book.getName(), book.getType(), book.getAuthor(), book.getPublishing(), new SimpleDateFormat("yyyy-MM").format(book.getDate()), book.getPrice(), book.getNum()};
                        model.addRow(rowData);
                        counterLabel.setText("当前待还记录数：" + model.getRowCount());
                    }
                }
            }
            returnButton.setEnabled(true); // 启用归还按钮
        });

        //归还按钮事件
        returnButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请选择要归还的图书", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String readerId = readerIdField.getText();
            String bookId = (String) table.getValueAt(selectedRow, 0);

            // 查找读者
            Reader reader = null;
            for (Reader r : LibrarySystem.readerList) {
                if (r.getReaderId().equals(readerId)) {
                    reader = r;
                    break;
                }
            }
            if (reader != null) {
                // 确保借阅列表是可变集合
                List<String> borrowedBooks = new ArrayList<>(reader.getBorrowedBooks());
                // 从读者借阅列表中删除图书
                if (borrowedBooks.remove(bookId)) {
                    reader.setBorrowedBooks(borrowedBooks); // 更新读者的借阅列表
                    // 更新图书库存
                    for (Book book : LibrarySystem.bookList) {
                        if (book.getId().equals(bookId)) {
                            book.setNum(book.getNum() + 1); // 库存加1
                            break;
                        }
                    }
                    // 刷新表格
                    model.removeRow(selectedRow);
                    counterLabel.setText("当前待还记录数：" + model.getRowCount());
                    model.setRowCount(0);
                    for (String id : borrowedBooks) {
                        for (Book book : LibrarySystem.bookList) {
                            if (book.getId().equals(id)) {
                                Object[] rowData = {book.getId(), book.getName(), book.getType(), book.getAuthor(), book.getPublishing(), new SimpleDateFormat("yyyy-MM").format(book.getDate()), book.getPrice(), book.getNum()};
                                model.addRow(rowData);
                            }
                        }
                    }
                    if (borrowedBooks.isEmpty()) {
                        returnButton.setEnabled(false); // 禁用归还按钮
                    }
                    JOptionPane.showMessageDialog(this, "图书归还成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //退出按钮事件
        exitButton.addActionListener(e -> {
            this.dispose();
        });

        //添加所有组件到面板
        this.add(panel);
        this.setVisible(true);
    }
}