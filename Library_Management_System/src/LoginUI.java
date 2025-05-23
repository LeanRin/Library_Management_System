import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginUI extends JFrame {
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;
    private JRadioButton adminButton;

    public LoginUI() {
        super("图书管理系统  设计人：钟文俊");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 350);  // 增加高度以适应新布局
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        Login();
    }

    public void Login() {

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));

        // 标题
        JLabel titleLabel = new JLabel("图书管理系统登录");
        titleLabel.setBounds(115, 40, 240, 30);
        titleLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 23));
        // 设置字体效果
        JLabel titleLabel2 = new JLabel("图书管理系统登录");
        titleLabel2.setBounds(117, 38,240,30);
        titleLabel2.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 23));
        //设置字体颜色
        titleLabel2.setForeground(new java.awt.Color(131, 131, 131, 140));
        panel.add(titleLabel);
        panel.add(titleLabel2);

        // 用户名标签、输入框
        JLabel usernameLabel = new JLabel("账 号：");
        usernameLabel.setBounds(70, 100, 80, 25);
        usernameLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(140, 100, 180, 25);
        panel.add(usernameField);

        // 密码标签、输入框
        JLabel passwordLabel = new JLabel("密 码：");
        passwordLabel.setBounds(70, 150, 80, 25);
        passwordLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 150, 180, 25);
        panel.add(passwordField);

        // 登录退出按钮
        loginButton = new JButton("登录");
        loginButton.setBounds(80, 250, 100, 35);
        loginButton.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        panel.add(loginButton);

        // 退出按钮
        JButton exitButton;

        exitButton = new JButton("退出");
        exitButton.setBounds(200, 250, 100, 35);
        exitButton.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        panel.add(exitButton);

        // 用户类型单选按钮
        ButtonGroup userTypeGroup = new ButtonGroup();

        adminButton = new JRadioButton("管理员");
        adminButton.setBounds(100, 200, 100, 35);
        adminButton.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        adminButton.setSelected(true);
        adminButton.setBackground(new java.awt.Color(255, 147, 217));
        panel.add(adminButton);


        // 普通用户单选按钮
        JRadioButton userButton;
        userButton = new JRadioButton("普通用户");
        userButton.setBounds(200, 200, 100, 35);
        userButton.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        userButton.setBackground(new java.awt.Color(255, 147, 217));
        panel.add(userButton);

        userTypeGroup.add(adminButton);
        userTypeGroup.add(userButton);

        // 将面板添加到窗口
        this.add(panel);
        this.setVisible(true);

        // 添加回车键切换输入框功能
        usernameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick(); // 触发登录按钮点击事件
                }
            }
        });

        // 登录按钮事件
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String selectedRole = adminButton.isSelected() ? "管理员" : "普通用户";

            // 检查用户名是否为空
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(LoginUI.this, "用户名不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
                usernameField.requestFocus();
                return;
            }

            // 检查密码是否为空
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginUI.this, "密码不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
                passwordField.requestFocus();
                return;
            }

            // 查找用户是否存在
            User foundUser = null;
            for (User user : LibrarySystem.userList) {
                if (user.getName().equals(username)) {
                    foundUser = user;
                    break;
                }
            }

            // 用户不存在
            if (foundUser == null) {
                JOptionPane.showMessageDialog(LoginUI.this, "用户名不存在！", "登录失败", JOptionPane.ERROR_MESSAGE);
                usernameField.requestFocus();//将焦点设置回用户名输入框
                //清除输入框
                usernameField.setText("");
                passwordField.setText("");
                return;
            }

            // 密码错误
            if (!foundUser.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(LoginUI.this, "密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
                passwordField.requestFocus();
                //清除密码框
                passwordField.setText("");
                return;
            }


            // 角色处理逻辑
            String actualRole = foundUser.getRole();
            String loginRole = selectedRole;

            // 处理角色交叉登录情况
            if (!actualRole.equals(selectedRole)) {
                if (actualRole.equals("普通用户")) {
                    // 普通用户尝试登录管理员账号，以普通用户身份登录
                    loginRole = "普通用户";
                    JOptionPane.showMessageDialog(LoginUI.this, "您没有管理员权限，将以普通用户身份登录", "权限提示", JOptionPane.INFORMATION_MESSAGE);
                } else if (actualRole.equals("管理员")) {
                    // 管理员尝试登录普通用户账号，以管理员身份登录
                    loginRole = "管理员";
                    JOptionPane.showMessageDialog(LoginUI.this, "您拥有管理员权限，将以管理员身份登录", "权限提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            // 登录成功
            JOptionPane.showMessageDialog(LoginUI.this, "登录成功！欢迎 " + username + " (" + loginRole + ")", "登录成功", JOptionPane.INFORMATION_MESSAGE);
            new MenutSystemUI(username, loginRole);
            dispose(); // 关闭登录窗口
        });

       // 退出按钮事件
        exitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(LoginUI.this, "确定要退出吗？", "退出确认", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}