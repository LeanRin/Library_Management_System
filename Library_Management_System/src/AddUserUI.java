import javax.swing.*;

public class AddUserUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton adminRadio;
    private JRadioButton userRadio;

    public AddUserUI() {
        super("添加用户  设计人：钟文俊");
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
        userLabel.setBounds(50, 30, 80, 25);
        usernameField = new JTextField();
        usernameField.setBounds(130, 30, 200, 25);

        // 密码
        JLabel passLabel = new JLabel("密码:");
        passLabel.setBounds(50, 70, 80, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(130, 70, 200, 25);

        // 角色选择
        ButtonGroup roleGroup = new ButtonGroup();
        adminRadio = new JRadioButton("管理员");
        userRadio = new JRadioButton("普通用户");
        adminRadio.setBounds(100, 110, 80, 25);
        userRadio.setBounds(200, 110, 100, 25);
        userRadio.setSelected(true);
        adminRadio.setBackground(new java.awt.Color(255, 147, 217));
        userRadio.setBackground(new java.awt.Color(255, 147, 217));
        roleGroup.add(adminRadio);
        roleGroup.add(userRadio);

        // 按钮
        JButton confirmBtn = new JButton("添加");
        JButton cancelBtn = new JButton("取消");
        confirmBtn.setBounds(100, 160, 80, 30);
        cancelBtn.setBounds(220, 160, 80, 30);

        // 添加组件
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(adminRadio);
        panel.add(userRadio);
        panel.add(confirmBtn);
        panel.add(cancelBtn);

        //回车切换焦点
        usernameField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> confirmBtn.requestFocus());

        // 确认按钮事件
        confirmBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = adminRadio.isSelected() ? "管理员" : "普通用户";
            // 检查用户名是否为空
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名不能为空",
                    "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // 检查密码是否为空
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "密码不能为空",
                    "输入错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // 检查用户名是否已存在
            for (User user : LibrarySystem.userList) {
                if (user.getName().equals(username)) {
                    JOptionPane.showMessageDialog(this, "账号已存在",
                        "输入错误", JOptionPane.WARNING_MESSAGE);
                    usernameField.setText("");
                    passwordField.setText("");
                    return;
                }
            }
            // 检查密码是否符合要求
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "密码长度不能小于6位",
                    "输入错误", JOptionPane.WARNING_MESSAGE);
                passwordField.setText("");
                return;
            }
            if (!password.matches(".*[0-9].*")) {
                JOptionPane.showMessageDialog(this, "密码必须包含数字",
                    "输入错误", JOptionPane.WARNING_MESSAGE);
                passwordField.setText("");
                return;
            }
            if (!password.matches(".*[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(this, "密码必须包含字母",
                    "输入错误", JOptionPane.WARNING_MESSAGE);
                passwordField.setText("");
                return;
            }
            // 添加用户
            LibrarySystem.userList.add(new User(username, password, role));
            JOptionPane.showMessageDialog(this, role+" "+username+" "+"添加成功",
                "成功", JOptionPane.INFORMATION_MESSAGE);
            // 清空输入框
            usernameField.setText("");
            passwordField.setText("");

        });
        // 取消按钮事件
        cancelBtn.addActionListener(e -> dispose());

        add(panel);
        setVisible(true);
    }
}
