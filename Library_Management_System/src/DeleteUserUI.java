import javax.swing.*;

public class DeleteUserUI extends JFrame {
    private JTextField usernameField;

    public DeleteUserUI() {
        super("删除用户  设计人：钟文俊");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));

        // 用户名
        JLabel userLabel = new JLabel("用户名:");
        JLabel userLabel2 = new JLabel("当前用户名:");
        userLabel.setBounds(50, 30, 80, 25);
        userLabel2.setBounds(50, 80, 80, 25);
        usernameField = new JTextField();
        JTextField usernameField2 = new JTextField();
        usernameField2.setBounds(130, 80, 180, 25);
        usernameField.setBounds(130, 30, 100, 25);
        usernameField2.setEditable(false);



        // 按钮
        JButton confirmBtn = new JButton("删除");
        //禁用按钮
        confirmBtn.setEnabled(false);
        JButton cancelBtn = new JButton("取消");
        //查询按钮
        JButton queryBtn = new JButton("查询");
        queryBtn.setBounds(250, 30, 60, 25);
        confirmBtn.setBounds(100, 160, 80, 30);
        cancelBtn.setBounds(220, 160, 80, 30);

        // 添加组件
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(queryBtn);
        panel.add(confirmBtn);
        panel.add(cancelBtn);
        panel.add(userLabel2);
        panel.add(usernameField2);



        // 查询按钮事件，获取编辑框内容与用户列表进行匹配，将匹配到的内容填入文本框中
        queryBtn.addActionListener(e -> {
            String username = usernameField.getText();



            // 检查输入框是否为空
            if (usernameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入用户名！");
                return;
            }


            // 遍历用户列表，查找匹配的用户
            for (User user : LibrarySystem.userList) {
                if (user.getName().equals(username)) {
                    // 将匹配到的用户信息填入文本框中
                    JOptionPane.showMessageDialog(this, "找到该用户！");
                    usernameField2.setText(user.getName());
                    confirmBtn.setEnabled(true);
                    return;
                }
                else {
                    JOptionPane.showMessageDialog(this, "未找到该用户！");
                    return;
                }
            }

        });

        // 确认按钮事件
        confirmBtn.addActionListener(e -> {
            String username = usernameField.getText();
            // 遍历用户列表，查找匹配的用户
            for (User user : LibrarySystem.userList) {
                if (user.getName().equals(username)) {
                    // 从列表中删除用户
                    LibrarySystem.userList.remove(user);
                    JOptionPane.showMessageDialog(this, "用户删除成功！");
                    usernameField.setText("");
                    usernameField2.setText("");
                    confirmBtn.setEnabled(false);
                    System.out.println(LibrarySystem.userList);
                    return;
                }
            }
        });


        // 取消按钮事件
        cancelBtn.addActionListener(e1 -> {
            dispose();
        });

        add(panel);
        setVisible(true);
    }
}

