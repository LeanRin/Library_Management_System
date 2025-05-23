import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenutSystemUI extends JFrame {
    private JMenu basicDataMenu;
    private JMenu borrowingMenu;
    String username;
    String role;
    public MenutSystemUI(String username, String role) {
        // 标题栏显示用户名和角色
        super("图书管理系统    "  + role + ":" + username+ "    设计人:钟文俊");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initializeUI();
        setMenuEnabledByRole(role);
        //存储用户名
        this.username = username;
        this.role = role;
    }

    private void initializeUI() {
        // 创建主面板
        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 加载背景图片
                ImageIcon icon = new ImageIcon("src/images/background.jpg");
                g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(600, 30));

        // 设置默认颜色
        Color defaultMenuColor = Color.blue;
        Color defaultMenuItemColor = Color.gray;
        Color selectedColor = Color.BLACK;

        // 设置菜单栏字体
        Font menuFont = new Font("微软雅黑", Font.PLAIN, 16);
        menuBar.setFont(menuFont);

        // 创建主菜单
        basicDataMenu = new JMenu("基础数据维护");
        borrowingMenu = new JMenu("借阅管理");
        JMenu queryMenu = new JMenu("信息查询");
        JMenu systemMenu = new JMenu("系统管理");

        // 为主菜单添加鼠标监听器
        addMenuMouseListener(basicDataMenu, defaultMenuColor, selectedColor);
        addMenuMouseListener(borrowingMenu, defaultMenuColor, selectedColor);
        addMenuMouseListener(queryMenu, defaultMenuColor, selectedColor);
        addMenuMouseListener(systemMenu, defaultMenuColor, selectedColor);

        // 创建基础数据维护子菜单
        JMenu bookInfoMenu = new JMenu("图书信息维护");
        JMenu userInfoMenu = new JMenu("账号信息维护");
        JMenu readerInfoMenu = new JMenu("读者信息维护");
        //读者信息维护

        // 创建图书信息维护子菜单
        JMenuItem addBookItem = new JMenuItem("<-添加图书->");
        JMenuItem modifyBookItem = new JMenuItem("<-修改图书->");
        JMenuItem deleteBookItem = new JMenuItem("<-删除图书->");
        //载入功能窗口
        addBookItem.addActionListener(e -> new AddBookUI());// 添加图书
        modifyBookItem.addActionListener(e -> new ReviseBookUI());// 修改图书
        deleteBookItem.addActionListener(e -> new DeleteBookUI());// 删除图书

        // 创建用户信息子菜单
        JMenuItem addUserItem = new JMenuItem("<-添加用户->");
        JMenuItem deleteUserItem = new JMenuItem("<-删除用户->");
        
        addUserItem.addActionListener(e -> new AddUserUI());// 添加用户
        deleteUserItem.addActionListener(e -> new DeleteUserUI());// 删除用户

        // 创建读者信息子菜单
        JMenuItem addreaderItem = new JMenuItem("<-添加读者->");
        JMenuItem modifyreaderItem = new JMenuItem("<-修改读者->");
        JMenuItem deletereaderItem = new JMenuItem("<-删除读者->");

        addreaderItem.addActionListener(e -> new AddReaderUI());// 添加读者信息
        modifyreaderItem.addActionListener(e -> new ModifyReaderUI());// 修改读者信息
        deletereaderItem.addActionListener(e -> new ModifyDeleteUI());// 删除读者信息

        // 创建借阅管理子菜单
        JMenuItem borrowBookItem = new JMenuItem("<-读者借书->");
        JMenuItem returnBookItem = new JMenuItem("<-读者还书->");
        
        borrowBookItem.addActionListener(e -> new BorrowBookUI());// 执行借书操作
        returnBookItem.addActionListener(e -> new RetunBookUI());// 执行还书操作

        // 创建信息查询子菜单
        JMenuItem queryBookItem = new JMenuItem("<-图书信息查询->");
        JMenuItem queryReaderItem = new JMenuItem("<-读者信息查询->");
        
        queryBookItem.addActionListener(e -> new BookInformationUI());// 执行图书查询操作
        queryReaderItem.addActionListener(e -> new ReaderInformationUI());// 执行读者查询操作

        // 创建系统管理子菜单
        JMenuItem showUserInfoItem = new JMenuItem("<-显示用户信息->");
        JMenuItem changePasswordItem = new JMenuItem("<-修改密码->");
        JMenuItem aboutItem = new JMenuItem("<-关于···->");
        
        showUserInfoItem.addActionListener(e -> new UserInformationUI(username,role));// 显示用户信息
        changePasswordItem.addActionListener(e -> new ChangePasswordUI(username,role));// 执行修改密码操作
        aboutItem.addActionListener(e -> // 显示关于对话框
                JOptionPane.showMessageDialog(this, "图书管理系统\n设计人:钟文俊\n学号：2024510203040\n版本:5.2.0", "关于", JOptionPane.INFORMATION_MESSAGE));

        // 分界线
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        JMenuItem exitItem = new JMenuItem("<-退出->");

        exitItem.addActionListener(e -> System.exit(0));// 退出程序

        // 为所有菜单项添加鼠标监听器
        jianting(defaultMenuItemColor, selectedColor, addBookItem, modifyBookItem, deleteBookItem, addUserItem, deleteUserItem,
                addreaderItem, modifyreaderItem, deletereaderItem, borrowBookItem, returnBookItem, queryBookItem, queryReaderItem,
                showUserInfoItem, changePasswordItem, aboutItem, exitItem);

        jianting(defaultMenuItemColor, selectedColor, borrowBookItem, returnBookItem, queryBookItem, queryReaderItem, showUserInfoItem, changePasswordItem, aboutItem);
        addMenuItemMouseListener(exitItem, defaultMenuItemColor, selectedColor);

        // 基础数据维护菜单添加子菜单
        basicDataMenu.add(bookInfoMenu);
        basicDataMenu.add(userInfoMenu);
        basicDataMenu.add(readerInfoMenu);

        // 图书信息维护菜单添加子菜单
        bookInfoMenu.add(addBookItem);
        bookInfoMenu.add(modifyBookItem);
        bookInfoMenu.add(deleteBookItem);

        // 账号信息维护菜单添加子菜单
        userInfoMenu.add(addUserItem);
        userInfoMenu.add(deleteUserItem);

        // 读者信息维护菜单添加子菜单
        readerInfoMenu.add(addreaderItem);
        readerInfoMenu.add(modifyreaderItem);
        readerInfoMenu.add(deletereaderItem);

        // 借阅管理菜单添加子菜单
        borrowingMenu.add(borrowBookItem);
        borrowingMenu.add(returnBookItem);

        // 信息查询菜单添加子菜单
        queryMenu.add(queryBookItem);
        queryMenu.add(queryReaderItem);

        // 系统管理菜单添加子菜单
        systemMenu.add(showUserInfoItem);
        systemMenu.add(changePasswordItem);
        systemMenu.add(aboutItem);

        // 系统管理菜单添加分界线
        systemMenu.addSeparator();
        separator.setForeground(Color.black);


        // 系统管理菜单添加退出
        systemMenu.add(exitItem);

        // 添加菜单到菜单栏
        menuBar.add(basicDataMenu);
        menuBar.add(borrowingMenu);
        menuBar.add(queryMenu);
        menuBar.add(systemMenu);


        // 设置菜单栏到窗口
        setJMenuBar(menuBar);

        // 将面板添加到窗口
        this.add(mainPanel);
        this.setVisible(true);
    }

    public void jianting(Color defaultMenuItemColor, Color selectedColor, JMenuItem... menuItems) {
        for (JMenuItem jMenuItem : menuItems) {
            addMenuItemMouseListener(jMenuItem, defaultMenuItemColor, selectedColor);
        }
    }

    // 为主菜单添加鼠标监听器
    private void addMenuMouseListener(JMenu menu, Color defaultColor, Color selectedColor) {
        menu.setForeground(defaultColor);
        menu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menu.setForeground(selectedColor);
            }

            public void mouseExited(MouseEvent e) {
                menu.setForeground(defaultColor);
            }
        });
    }

    // 为菜单项添加鼠标监听器
    private void addMenuItemMouseListener(JMenuItem menuItem, Color defaultColor, Color selectedColor) {
        menuItem.setForeground(defaultColor);
        menuItem.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                menuItem.setForeground(selectedColor);
            }

            public void mouseExited(MouseEvent e) {
                menuItem.setForeground(defaultColor);
            }
        });
    }

    // 根据用户角色启用或禁用菜单
    private void setMenuEnabledByRole(String role) {
        if ("普通用户".equals(role)) {
            basicDataMenu.setEnabled(false);
            borrowingMenu.setEnabled(false);
        }
    }
    
}
