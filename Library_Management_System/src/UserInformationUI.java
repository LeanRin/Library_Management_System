import javax.swing.*;
import java.awt.*;

public class UserInformationUI extends JFrame {
    String username;
    String role;
    int length;

    public UserInformationUI(String username, String role) {
        super("用户信息  设计人：钟文俊");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        this.username = username;
        this.role = role;

        initUI();
    }

    private void initUI() {
        // 创建面板
        JPanel panel = new JPanel(null);


        String[] columnNames = {"用户名", "密码", "角色"};

        //判断表格长度
        if (role.equals("管理员")) {
            length = LibrarySystem.userList.size();
        } else {
            length = 1;
        }
        // 创建数据数组
        Object[][] data = new Object[length][columnNames.length];


        //Object[][] data = new Object[LibrarySystem.userList.size()][columnNames.length];

        if (role.equals("管理员")) {
            // 遍历LibrarySystem类中的userList，获取所有用户信息填充到表格中
            for (int i = 0; i < LibrarySystem.userList.size(); i++) {
                data[i][0] = LibrarySystem.userList.get(i).getName();
                data[i][1] = LibrarySystem.userList.get(i).getPassword();
                data[i][2] = LibrarySystem.userList.get(i).getRole();
            }
        } else {
            // 遍历LibrarySystem类中的userList，获取当前用户信息填充到表格中
            int rowIndex = 0;
            for (int i = 0; i < LibrarySystem.userList.size(); i++) {
                if (LibrarySystem.userList.get(i).getName().equals(username)) {
                    data[rowIndex][0] = LibrarySystem.userList.get(i).getName();
                    data[rowIndex][1] = LibrarySystem.userList.get(i).getPassword();
                    data[rowIndex][2] = LibrarySystem.userList.get(i).getRole();
                    break;
                }
            }
        }

        // 创建表格
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 600, 250);

        // 设置表格的行高
        table.setRowHeight(30);

        // 表头字体
        table.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 16));

        // 表格字体
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));

        // 设置表格不可编辑
        table.setDefaultEditor(Object.class, null);

        // 添加组件到面板
        panel.add(scrollPane);

        // 添加面板到窗口
        setContentPane(panel);

        // 设置窗口可见
        setVisible(true);
    }
}