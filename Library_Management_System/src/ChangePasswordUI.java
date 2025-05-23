import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChangePasswordUI extends JFrame {
    private JPasswordField Enterpassword; // 修改密码输入框
    private JPasswordField ConfirmPassword; // 再次输入密码输入框
    private String username;
    private String role;

    public ChangePasswordUI(String username, String role) {
        super("修改密码  设计人：钟文俊");
        this.username = username;
        this.role = role;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        //判断是否为管理员
        if (role.equals("管理员")) {
            AdmininitUI();

        } else {
            initUI();
        }

    }

    private void AdmininitUI() {
        JFrame jframe = new JFrame("管理员密码管理");  // 优化窗口标题
        jframe.setSize(400, 260);  // 适当增加高度
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));

        // 调整按钮位置和间距（增加上下边距）
        JButton button1 = new JButton("修改当前用户密码");
        button1.setBounds(50, 40, 300, 35);  // 调整y坐标和高度
        JButton button2 = new JButton("重置其他用户密码");
        button2.setBounds(50, 95, 300, 35);  // 调整y坐标和高度
        JButton button3 = new JButton("关闭");
        button3.setBounds(50, 150, 300, 35);  // 调整y坐标和高度

        // 优化按钮字体
        java.awt.Font buttonFont = new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 14);
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        button3.setFont(buttonFont);

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        jframe.add(panel);
        jframe.setVisible(true);

        // 按钮事件保持不变
        button1.addActionListener(e -> {
            initUI();
            jframe.dispose();
        });
        button2.addActionListener(e -> {
            recinitUI();
            jframe.dispose();
        });
        button3.addActionListener(e -> jframe.dispose());
    }

    private void initUI() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));

        //当前用户名标签
        JLabel currentUserLabel = new JLabel("当前用户名: " + username);
        currentUserLabel.setBounds(130, 10, 200, 25);
        currentUserLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 15));
        panel.add(currentUserLabel);

        // 输入新密码标签和输入框
        JLabel userLabel = new JLabel("输入新的密码:");
        userLabel.setBounds(50, 50, 120, 25);
        userLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 15));
        Enterpassword = new JPasswordField();
        Enterpassword.setBounds(150, 50, 180, 25);
        // 添加回车事件监听
        Enterpassword.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    ConfirmPassword.requestFocusInWindow();
                }
            }
        });

        // 再次输入密码标签和输入框
        JLabel passLabel = new JLabel("再次输入密码:");
        passLabel.setBounds(50, 100, 120, 25);
        passLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 15));
        ConfirmPassword = new JPasswordField();
        ConfirmPassword.setBounds(150, 100, 180, 25);

        //添加提示标签
        JLabel tipLabel = new JLabel("密码长度不能小于6位，必须包含字母");
        tipLabel.setBounds(50, 130, 300, 25);
        tipLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 12));
        tipLabel.setForeground(new java.awt.Color(255, 0, 0)); // 设置字体颜色为红色
        panel.add(tipLabel);


        // 添加回车事件监听
        ConfirmPassword.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    // 触发确认按钮的点击事件
                    ((JButton) panel.getComponentAt(4, 160)).doClick();
                }
            }
        });

        // 按钮
        JButton confirmBtn = new JButton("修改");
        JButton cancelBtn = new JButton("取消");
        confirmBtn.setBounds(100, 160, 80, 30);
        cancelBtn.setBounds(220, 160, 80, 30);

        // 添加组件
        panel.add(userLabel);
        panel.add(Enterpassword);
        panel.add(passLabel);
        panel.add(ConfirmPassword);
        panel.add(confirmBtn);
        panel.add(cancelBtn);


        // 修改密码按钮事件
        confirmBtn.addActionListener(e -> {
            String newPassword = new String(Enterpassword.getPassword());
            String confirmPassword = new String(ConfirmPassword.getPassword());

            // 检查输入是否为空
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请再次输入密码", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 检查两次输入是否一致
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                // 清空输入框
                Enterpassword.setText("");
                ConfirmPassword.setText("");
                return;
            }

            // 验证密码长度和内容
            if (newPassword.length() < 6) {
                JOptionPane.showMessageDialog(this, "密码长度不能小于6位", "错误", JOptionPane.ERROR_MESSAGE);
                Enterpassword.setText("");
                ConfirmPassword.setText("");
                return;
            }

            // 检查密码是否包含数字
            if (!newPassword.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(this, "密码必须包含数字", "错误", JOptionPane.ERROR_MESSAGE);
                Enterpassword.setText("");
                ConfirmPassword.setText("");
                return;
            }


            if (!newPassword.matches(".*[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(this, "密码必须包含字母", "错误", JOptionPane.ERROR_MESSAGE);
                Enterpassword.setText("");
                ConfirmPassword.setText("");
                return;
            }

            // 查找并更新用户密码
            for (User user : LibrarySystem.userList) {
                if (user.getName().equals(username)) {
                    LibrarySystem.userList.set(LibrarySystem.userList.indexOf(user),
                            new User(user.getName(), newPassword, user.getRole()));
                    JOptionPane.showMessageDialog(this, "密码修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    // 打印测试信息
                    System.out.println(LibrarySystem.userList);
                    dispose();
                }
            }
        });

        // 取消按钮事件
        cancelBtn.addActionListener(e -> dispose());

        add(panel);
        setVisible(true);
    }

    private void recinitUI() {
        //设置窗口大小
        this.setSize(400, 200);
        // 创建面板
        JPanel panel = new JPanel(null);
        panel.setBackground(new java.awt.Color(255, 147, 217));
        // 调整搜索用户标签位置和字体
        JLabel searchUserLabel = new JLabel("输入要重置密码的用户名");
        searchUserLabel.setBounds(120, 20, 180, 25);  // 调整x坐标和y坐标
        searchUserLabel.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 15));
        
        // 调整输入框位置和宽度
        JTextField searchUserTextField = new JTextField();
        searchUserTextField.setBounds(80, 60, 260, 25);  // 增加宽度并调整位置
        
        // 调整按钮位置和尺寸
        JButton resetPasswordButton = new JButton("重置密码");
        resetPasswordButton.setBounds(80, 100, 120, 30);  // 调整位置和宽度
        JButton exitButton = new JButton("退出");
        exitButton.setBounds(220, 100, 120, 30);  // 调整位置和宽度
        
        // 添加组件
        panel.add(searchUserLabel);
        panel.add(searchUserTextField);
        panel.add(resetPasswordButton);
        panel.add(exitButton);

        // 重置密码按钮事件（修复bug）
        resetPasswordButton.addActionListener(e -> {
            String username = searchUserTextField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (User user : LibrarySystem.userList) {
                if (user.getName().equals(username)) {
                    LibrarySystem.userList.set(LibrarySystem.userList.indexOf(user),
                            new User(user.getName(), "123456", user.getRole()));
                    JOptionPane.showMessageDialog(this, "密码重置成功，默认密码为123456", "成功", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(LibrarySystem.userList);
                    break;
                }else {
                    JOptionPane.showMessageDialog(this, "用户不存在", "错误", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        });

        // 退出按钮事件
        exitButton.addActionListener(e -> dispose());
        
        this.add(panel);
        this.setVisible(true);
    }
}